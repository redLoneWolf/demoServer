package com.example.demoserver.data;

import com.example.demoserver.Database;
import com.google.gson.JsonElement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OrganisationDao {

    static public int save(Organisation org) {
        Connection connection= Database.getConnection();
        int status = 0;

        String query = "insert into organisations(name) values ('"+org.getName()+"');";

        try {
            Statement st = connection.createStatement();
            status = st.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    static public List<Organisation> getAll(){
        Connection connection= Database.getConnection();

        List<Organisation> organisations = new ArrayList<>();
        String query = "select id,name,created from organisations;";
        try {
            Statement st = connection.createStatement();
            ResultSet set = st.executeQuery(query);
            while (set.next()) {
                Organisation org = new Organisation();
                org.setName(set.getString("name"));
                org.setCreatedAt(set.getTimestamp("created").toString());
                org.setId(set.getInt("id"));
                organisations.add(org);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return organisations;
    }

    static public Organisation get(int id){
        Connection connection= Database.getConnection();
        Organisation org = null;
        String query = "select id, name, created from organisations where id ="+id+" limit  1;";
        try {
            Statement st = connection.createStatement();
            ResultSet set = st.executeQuery(query);
            while (set.next()) {
               org = new Organisation(set.getInt("id"),set.getString("name"),set.getString("created"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return org;
    }

    static public int delete(int id){
        Connection connection= Database.getConnection();

        int status =0;
        String query = "delete from organisations where id="+id+" ;";
        try {
            Statement st = connection.createStatement();
            status = st.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }
    public static int update(int id, String name){
        int status =0;
        Connection connection = Database.getConnection();
        String query = "update organisations set name='"+name+"' where id="+id+" ;";

        System.out.println(query);

        try {
            Statement st = connection.createStatement();
            status = st.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }
}
