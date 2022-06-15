package com.example.demoserver.data;

import com.example.demoserver.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDao {
    static public Invoice save(Invoice invoice) {
        Connection connection= Database.getConnection();
        int status = 0;
        Invoice out = null;

        String query = "insert into invoices(cName, orgId, discount, tax ) values " +
                "('"+ invoice.getCustomerName()+"',"+invoice.getOrgId()+","+invoice.getDiscount()+"," +invoice.getTax()+");";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            status = pstmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            if(status==1){
                ResultSet keys = pstmt.getGeneratedKeys();
                keys.next();
                out =  get(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }

    static public Invoice saveWithOrders(Invoice invoice){
        Invoice out = save(invoice);
        Float totalPrice = 0.0f;
        List<Order> outOrders = new ArrayList<>();
        for (Order order: invoice.getOrders()){

            order.setInvoiceId(out.getId());
            order.setCustomerName(out.getCustomerName());
            order.setOrgId(out.getOrgId());


            Order toSave = OrderDao.save(order);
            totalPrice = totalPrice +toSave.getAmount();
            outOrders.add(toSave);

            int old = StockDao.getWithWarIdAndItemId(toSave.getWarId(),toSave.getItemId()).getCount();

            StockDao.updateWithWarIdAndItemId(toSave.getWarId(),toSave.getItemId(),old-toSave.getQuantity());
        }
        out.setTotalPrice(totalPrice);
        out.setOrders(outOrders);
        return out;
    }

    static public List<Invoice> getAll(){
        Connection connection= Database.getConnection();
        List<Invoice> invoices = new ArrayList<>();
        String query = "select id, cName, orgId, discount, tax, createdAt from invoices;";
        try {

            Statement st = connection.createStatement();
            ResultSet set = st.executeQuery(query);
            while (set.next()) {
                Invoice invoice = new Invoice();
                invoice.setId(set.getInt("id"));
                invoice.setOrgId(set.getInt("orgId"));
                invoice.setCustomerName(set.getString("cName"));
                invoice.setDiscount(set.getFloat("discount"));
                invoice.setTax(set.getFloat("tax"));
                invoice.setCreatedAt(set.getTimestamp("createdAt").toString());
                invoice.setTotalPrice((float) OrderDao.getAllWithInvoiceId(invoice.getId()).stream().mapToInt(Order::getAmount).reduce(0, Integer::sum));

                invoices.add(invoice);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return invoices;
    }

    static public Invoice get(int id){
        Connection connection= Database.getConnection();
        Invoice invoice = null;
        String query = "select id, cName, orgId, discount, tax, createdAt from invoices where id ="+id+" limit  1;";
        try {
            Statement st = connection.createStatement();
            ResultSet set = st.executeQuery(query);
            while (set.next()) {
                invoice = new Invoice();
                invoice.setId(set.getInt("id"));
                invoice.setOrgId(set.getInt("orgId"));
                invoice.setCustomerName(set.getString("cName"));
                invoice.setDiscount(set.getFloat("discount"));
                invoice.setTax(set.getFloat("tax"));
                invoice.setCreatedAt(set.getTimestamp("createdAt").toString());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return invoice;
    }

    static public int delete(int id){
        Connection connection= Database.getConnection();
        int status =0;
        String query = "delete from invoices where id="+id+" ;";
        try {
            Statement st = connection.createStatement();
            status = st.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    public static int update(int id, Float tax,Float discount){
        int status = 0;
        Connection connection = Database.getConnection();
        StringBuilder query = new StringBuilder("update invoices set ");
        if(tax!=null){
            query.append(" tax=").append(tax);
            if(discount!=null){
                query.append(",");
            }
        }
        if(discount!=null){
            query.append(" discount=").append(discount);
        }
        query.append(" where id=").append(id).append(" ;");
        System.out.println(query);
       try {
            Statement st = connection.createStatement();
            status = st.executeUpdate(query.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    public static Invoice getWithOrders(int id){
        Invoice invoice = get(id);
        List<Order> orders = OrderDao.getAllWithInvoiceId(id);
         invoice.setOrders(orders);

        invoice.setTotalPrice((float) orders.stream().mapToInt(Order::getAmount).reduce(0, Integer::sum));

        return invoice;
    }

}
