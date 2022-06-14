package com.example.demoserver.data;

import com.example.demoserver.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StockDao {
    static public int save(Stock stock) {
        Connection connection= Database.getConnection();
        int status = 0;

        String query = "insert into warehouseStocks(warId, itemId, count) values ("+ stock.getWarId()+","+ stock.getItemId()+","+ stock.getCount()+");";

        try {
            Statement st = connection.createStatement();
            status = st.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    static public List<Stock> getAll(){
        Connection connection= Database.getConnection();
        List<Stock> stocks = new ArrayList<>();
        String query = "select id, warId, itemId, count from warehouseStocks;";
        try {
            Statement st = connection.createStatement();
            ResultSet set = st.executeQuery(query);
            while (set.next()) {
                Stock stock = new Stock();
                stock.setId(set.getInt("id"));
                stock.setItemId(set.getInt("itemId"));
                stock.setWarId(set.getInt("warId"));
                stock.setCount(set.getInt("count"));
                stocks.add(stock);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stocks;
    }

    static public Stock get(int id){
        Connection connection= Database.getConnection();
        Stock stock = null;
        String query = "select id, warId, itemId, count from warehouseStocks where id ="+id+" limit  1;";
        try {
            Statement st = connection.createStatement();
            ResultSet set = st.executeQuery(query);
            while (set.next()) {
                stock = new Stock(set.getInt("id"),set.getInt("warId"),set.getInt("itemId"),set.getInt("count"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stock;
    }

    static public int delete(int id){
        Connection connection= Database.getConnection();
        int status =0;
        String query = "delete from warehouseStocks where id="+id+" ;";
        try {
            Statement st = connection.createStatement();
            status = st.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    public static int update(int id, int count){
        int status = 0;
        Connection connection = Database.getConnection();
        String query = "update warehouseStocks set count="+count+" where id="+id+" ;";
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
