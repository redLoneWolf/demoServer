package com.example.demoserver.data;

import com.example.demoserver.Database;
import com.fasterxml.jackson.databind.JsonNode;


import java.sql.*;
import java.util.*;

public class ItemDao {


   public static Item save(Item item){
       int status = 0;
       Item out = null;
       Connection connection = Database.getConnection();

        try {
            String query = "insert into items(orgId, name, description,  costPrice, sellingPrice) values ("+ item.getOrgId()+",'" +
                    item.getName()+"','"+item.getDescription()+"',"+item.getCostPrice()+","+item.getSellingPrice()+");";
            PreparedStatement pstmt = connection.prepareStatement(query);
            status = pstmt.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
            if(status==1){
                ResultSet keys = pstmt.getGeneratedKeys();
                keys.next();
                out =  getItem(keys.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;

    };

    public static List<Item> getAll(){
        Connection connection = Database.getConnection();
        List<Item>items = new ArrayList<>();
        try {
            Statement st =  connection.createStatement();
            String query = "select id,orgId,name,description,costPrice,sellingPrice,createdAt from items;";
            ResultSet rs =  st.executeQuery(query);
            while (rs.next()){
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setDescription(rs.getString("description"));
                item.setOrgId(rs.getInt("orgId"));
                item.setCostPrice(rs.getInt("costPrice"));
                item.setSellingPrice(rs.getInt("sellingPrice"));
                item.setCreatedAt(rs.getTimestamp("createdAt").toString());
                item.setTotalStock(StockDao.getWithItemId(item.getId()).stream().mapToInt(Stock::getCount).reduce(0, Integer::sum));
                items.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items;
    }

   public static Item getItem(int id){
        Item item =null;
        Connection connection = Database.getConnection();
        try {
            Statement st =  connection.createStatement();
            String query = "select id,orgId,name,description,costPrice,sellingPrice,createdAt from items where id="+id+" limit  1;";
            ResultSet rs =  st.executeQuery(query);
            while (rs.next()){
                item = new Item();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setDescription(rs.getString("description"));
                item.setOrgId(rs.getInt("orgId"));
                item.setCostPrice(rs.getInt("costPrice"));
                item.setSellingPrice(rs.getInt("sellingPrice"));
                item.setCreatedAt(rs.getTimestamp("createdAt").toString());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return item;
    }
    public static int delete(int id){
        int status=0;
        Connection connection = Database.getConnection();
        String query = "delete from items where id="+id+" ;";
        try {
            Statement st = connection.createStatement();
            status = st.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;

    }

    public static int update(int id,Iterator<Map.Entry<String, JsonNode>> entrySet){
        int status =0;
        Connection connection = Database.getConnection();
        StringBuilder query = new StringBuilder("update items set ");

        int i =0;
        for (; entrySet.hasNext(); ) {
            Map.Entry<String, JsonNode> entry = entrySet.next();
            String key = entry.getKey();

            if(i>1 && i<4){
                query.append(",");
            }

            switch (key) {

                case "name":
                    query.append("name='").append(entry.getValue().asText()).append("'");i++;
                    break;
                case "desc":
                    query.append("description='").append(entry.getValue().asText()).append("'");i++;
                    break;
                case "cost":
                    query.append("costPrice=").append(entry.getValue().asInt()).append(",");i++;
                    break;
                case "sale":
                    query.append("sellingPrice=").append(entry.getValue().asInt()).append(" ");i++;
                    break;
            }
            i++;
//            else if (key.equals("id")) {
//                objId = entry.getValue().getAsInt();
//            }
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

    public static Item getWithStocks(int itemId){
        Item item = getItem(itemId);
        List<Stock>stocks = StockDao.getWithItemId(itemId);
        item.setStocks(stocks);
        item.setTotalStock(stocks.stream().mapToInt(Stock::getCount).reduce(0,Integer::sum));
        return item;
    }
}
