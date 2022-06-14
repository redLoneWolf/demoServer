package com.example.demoserver.data;

import com.example.demoserver.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDao {
    static public int save(Invoice invoice) {
        Connection connection= Database.getConnection();
        int status = 0;

        String query = "insert into invoices(cName, orgId, discount, tax ) values " +
                "('"+ invoice.getCustomerName()+"',"+invoice.getOrgId()+","+invoice.getDiscount()+"," +invoice.getTax()+");";
        try {
            Statement st = connection.createStatement();
            status = st.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
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

}
