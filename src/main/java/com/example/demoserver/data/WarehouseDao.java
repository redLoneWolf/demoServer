package com.example.demoserver.data;

import com.example.demoserver.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WarehouseDao {

    static public int save(Warehouse warehouse) {
        Connection connection= Database.getConnection();
        int status = 0;

        String query = "insert into warehouses(name, orgId) values ('"+warehouse.getName()+"',"+ warehouse.getOrgId()+");";

        try {
            Statement st = connection.createStatement();
            status = st.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    static public List<Warehouse> getAll(){
        Connection connection= Database.getConnection();

        List<Warehouse> warehouses = new ArrayList<>();
        String query = "select id,name,orgId from warehouses;";
        try {
            Statement st = connection.createStatement();
            ResultSet set = st.executeQuery(query);
            while (set.next()) {
                Warehouse war = new Warehouse();
                war.setName(set.getString("name"));
                war.setId(set.getInt("id"));
                war.setOrgId(set.getInt("orgId"));
                warehouses.add(war);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return warehouses;
    }

    static public Warehouse get(int id){
        Connection connection= Database.getConnection();
        Warehouse warehouse = null;
        String query = "select id, name, orgId from warehouses where id ="+id+" limit  1;";
        try {
            Statement st = connection.createStatement();
            ResultSet set = st.executeQuery(query);
            while (set.next()) {
                warehouse = new Warehouse(set.getInt("id"),set.getString("name"),set.getInt("orgId"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return warehouse;
    }

    static public int delete(int id){
        Connection connection= Database.getConnection();

        int status =0;
        String query = "delete from warehouses where id="+id+" ;";
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
        String query = "update warehouses set name='"+name+"' where id="+id+" ;";

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
