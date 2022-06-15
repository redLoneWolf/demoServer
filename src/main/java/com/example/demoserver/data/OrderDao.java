package com.example.demoserver.data;

import com.example.demoserver.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    static public Order save(Order order) {
        Connection connection= Database.getConnection();
        Order out = null;
        int status = 0;

        int orderPrice = ItemDao.getItem(order.getItemId()).getCostPrice();
        order.setPrice(orderPrice);

        String query = "insert into orders(invoiceId, cName, itemId, orgId, price,warId,quantity) values " +
                "("+ order.getInvoiceId()+",'"+order.getCustomerName()+"',"+order.getItemId()+","
                +order.getOrgId()+","+order.getPrice()+","+order.getWarId()+","+order.getQuantity()+");";

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

    static public List<Order> getAll(){
        Connection connection= Database.getConnection();
        List<Order> orders = new ArrayList<>();
        String query = "select id, invoiceId, cName, itemId, orgId, quantity,price,warId, createdAt from orders;";
        try {

            Statement st = connection.createStatement();
            ResultSet set = st.executeQuery(query);
            while (set.next()) {
                Order order = new Order();
                order.setId(set.getInt("id"));
                order.setInvoiceId(set.getInt("invoiceId"));
                order.setItemId(set.getInt("itemId"));
                order.setCustomerName(set.getString("cName"));
                order.setOrgId(set.getInt("orgId"));
                order.setQuantity(set.getInt("quantity"));
                order.setPrice(set.getInt("price"));
                order.setWarId(set.getInt("warId"));
                order.setCreatedAt(set.getTimestamp("createdAt").toString());
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    static public Order get(int id){
        Connection connection= Database.getConnection();
        Order order = null;
        String query = "select id, invoiceId, cName, itemId, orgId,quantity, price,warId, createdAt from orders where id ="+id+" limit  1;";
        try {
            Statement st = connection.createStatement();
            ResultSet set = st.executeQuery(query);
            while (set.next()) {
                order = new Order();
                order.setId(set.getInt("id"));
                order.setInvoiceId(set.getInt("invoiceId"));
                order.setItemId(set.getInt("itemId"));
                order.setCustomerName(set.getString("cName"));
                order.setOrgId(set.getInt("orgId"));
                order.setPrice(set.getInt("price"));
                order.setQuantity(set.getInt("quantity"));
                order.setWarId(set.getInt("warId"));
                order.setCreatedAt(set.getTimestamp("createdAt").toString());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return order;
    }

    static public int delete(int id){
        Connection connection= Database.getConnection();
        int status =0;
        String query = "delete from orders where id="+id+" ;";
        try {
            Statement st = connection.createStatement();
            status = st.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    public static int update(int id, Integer price,Integer quantity){
        int status = 0;
        Connection connection = Database.getConnection();
        StringBuilder query = new StringBuilder("update orders set ");
        if(price!=null){
            query.append(" price=").append(price);
            if(quantity!=null){
                query.append(",");
            }
        }
        if(quantity!=null){
            query.append(" quantity=").append(quantity);
        }
        query.append(" where id=").append(id).append(" ;");
        try {
            Statement st = connection.createStatement();
            status = st.executeUpdate(query.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }


    
    static public List<Order> getAllWithInvoiceId(int invoiceId){
        Connection connection= Database.getConnection();
        List<Order> orders = new ArrayList<>();
        String query = "select id, invoiceId, cName, itemId, orgId,quantity, warId,price, createdAt from orders where invoiceId ="+invoiceId+" order by createdAt;";
        try {
            Statement st = connection.createStatement();
            ResultSet set = st.executeQuery(query);
            while (set.next()) {
                Order order = new Order();
                order.setId(set.getInt("id"));
                order.setInvoiceId(set.getInt("invoiceId"));
                order.setItemId(set.getInt("itemId"));
                order.setCustomerName(set.getString("cName"));
                order.setOrgId(set.getInt("orgId"));
                order.setPrice(set.getInt("price"));
                order.setQuantity(set.getInt("quantity"));
                order.setCreatedAt(set.getTimestamp("createdAt").toString());
                order.setWarId(set.getInt("warId"));

                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }
}
