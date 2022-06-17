package com.example.demoserver.data;

import com.example.demoserver.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockDao {
    static public Stock save(Stock stock) throws SQLException {
        Connection connection= Database.getConnection();
        int status = 0;
        Stock out = null;

        String query = "insert into warehouseStocks(warId, itemId, count) values ("+ stock.getWarId()+","+ stock.getItemId()+","+ stock.getCount()+");";

//        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            status = pstmt.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
            if(status==1){
                ResultSet keys = pstmt.getGeneratedKeys();
                keys.next();
                out =  get(keys.getInt(1));
            }
//        } catch (SQLException e ) {
//            throw new RuntimeException(e);
//        }
        return out;
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
                stock = new Stock(set.getInt("id"),set.getInt("warId"),set.getInt("itemId"),set.getInt("count"),0);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stock;
    }
    static public Stock getWithWarIdAndItemId(int warId,int itemId){
        Connection connection= Database.getConnection();
        Stock stock = null;
        String query = "select id, warId, itemId, count from warehouseStocks where itemId ="+itemId+" and warId="+warId+" limit  1;";
        try {
            Statement st = connection.createStatement();
            ResultSet set = st.executeQuery(query);
            while (set.next()) {
                stock = new Stock(set.getInt("id"),set.getInt("warId"),set.getInt("itemId"),set.getInt("count"),0);
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

    public static int updateWithWarIdAndItemId(int warId,int itemId, int count){
        int status = 0;
        Connection connection = Database.getConnection();
        String query = "update warehouseStocks set count="+count+" where itemId="+itemId+" and warId="+warId+"  limit 1;";
        System.out.println(query);
        try {
            Statement st = connection.createStatement();
            status = st.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    public static List<Stock> getWithItemId(int itemId){
        Connection connection = Database.getConnection();

        List<Stock> stocks = new ArrayList<>();
        String query = "select id, warId, itemId, count from warehouseStocks where itemId ="+itemId+" ;";
        try {
            Statement st = connection.createStatement();
            ResultSet set = st.executeQuery(query);
            while (set.next()) {

                stocks.add(new Stock(set.getInt("id"),set.getInt("warId"),set.getInt("itemId"),set.getInt("count"),0));
            }
        }
        catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return stocks;
    }


}
