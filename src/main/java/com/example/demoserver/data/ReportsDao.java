package com.example.demoserver.data;

import com.example.demoserver.Database;
import com.example.demoserver.JacksonHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportsDao {
    private static  ObjectMapper mapper = JacksonHelper.getObjectMapper();
    private static  Connection connection = Database.getConnection();


    public static String getSummaryForItems(int id) throws SQLException, IOException {
        String json = "";
        String query = "select item_id,i.name,i.sellingPrice,i.createdAt, sum(quantity) as sold, sum(ie.count) as stock " +
                "from ((select itemId as item_id, quantity, 0 as count from orders ) union all " +
                "      (select itemId, 0, count from warehouseStocks ) ) ie left join items i on i.id = item_id group by item_id order by item_id;";

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        List<Map<String, Object>> rows = new ArrayList<>();

        ResultSetMetaData rsmd = rs.getMetaData();
        while (rs.next()){
            int colCount = rsmd.getColumnCount();
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i <= colCount; i++) {
                // Note that the index is 1-based
                String colName = rsmd.getColumnName(i);
                Object colVal = rs.getObject(i);
                row.put(colName.toString(), colVal);
            }
            rows.add(row);
        }
            json =  mapper.writeValueAsString(rows);
        return json;
    }
    public  static String getItemReports(Map<String,String[]> map ) throws SQLException, IOException {
        String out = "";
        String sort = "order by ";
        String query = "select item_id,i.name,i.sellingPrice as itemPrice,i.createdAt, sum(quantity) as soldUnits, sum(quantity)*i.sellingPrice as totalSaleAmount, sum(ie.count) as stockUnits " +
                "from ((select itemId as item_id, quantity, 0 as count from orders ) union all " +
                "      (select itemId, 0, count from warehouseStocks ) ) ie left join items i on i.id = item_id group by item_id ";


        if (map.containsKey("sortBy")) {
            String sortBy = map.get("sortBy")[0];
            if(sortBy.equals("soldUnits")){
                sort = sort+sortBy;
            } else if (sortBy.equals("-soldUnits")) {
                sort = sort +sortBy.substring(1)+" desc";
            }else if(sortBy.equals("stockUnits")){
                sort = sort+sortBy;
            } else if (sortBy.equals("-stockUnits")) {
                sort = sort +sortBy.substring(1)+" desc";
            } else if(sortBy.equals("itemPrice")){
                sort = sort+sortBy;
            } else if (sortBy.equals("-itemPrice")) {
                sort = sort +sortBy.substring(1)+" desc";
            }
            else if(sortBy.equals("totalSaleAmount")){
                sort = sort+sortBy;
            } else if (sortBy.equals("-totalSaleAmount")) {
                sort = sort +sortBy.substring(1)+" desc";
            }else {
                sort = sort + " item_id";
            }
        }else {
            sort = sort + " item_id";
        }
        query = query+sort+" ;";
        System.out.println(query);

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        List<Map<String, Object>> rows = new ArrayList<>();

        ResultSetMetaData rsmd = rs.getMetaData();
        while (rs.next()){
            int colCount = rsmd.getColumnCount();
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i <= colCount; i++) {
                // Note that the index is 1-based
                String colName = rsmd.getColumnName(i);
                Object colVal = rs.getObject(i);
                row.put(colName.toString(), colVal);
            }
            rows.add(row);
        }
        out =  mapper.writeValueAsString(rows);

        return out;
    }

    public  static String getCustomerReports(Map<String,String[]> map ) throws SQLException, IOException {
        String out = "";
        String sort = "order by ";
        String query = "select cname, sum(tot) as totalPrice, sum(ie.ct) as invoices " +
                "from ((select  orders.cName as cname, price*orders.quantity as tot, 0 as ct from orders ) union all " +
                "(select invoices.cName,0, 1 from invoices )) ie group by cname ";

        if (map.containsKey("sortBy")) {
            String sortBy = map.get("sortBy")[0];
            if(sortBy.equals("cname")){
                sort = sort+sortBy;
            } else if (sortBy.equals("-cname")) {
                sort = sort +sortBy.substring(1)+" desc";
            }else if(sortBy.equals("invoiceCount")){
                sort = sort+sortBy;
            } else if (sortBy.equals("-invoiceCount")) {
                sort = sort +sortBy.substring(1)+" desc";
            } else if(sortBy.equals("totalPrice")){
                sort = sort+sortBy;
            } else if (sortBy.equals("-totalPrice")) {
                sort = sort +sortBy.substring(1)+" desc";
            }
            else {
                sort = sort + " cname";
            }
        }else {
            sort = sort + " cname";
        }
        query = query+sort+" ;";
        System.out.println(query);

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        List<Map<String, Object>> rows = new ArrayList<>();

        ResultSetMetaData rsmd = rs.getMetaData();
        while (rs.next()){
            int colCount = rsmd.getColumnCount();
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i <= colCount; i++) {
                // Note that the index is 1-based
                String colName = rsmd.getColumnName(i);
                Object colVal = rs.getObject(i);
                row.put(colName.toString(), colVal);
            }
            rows.add(row);
        }
        out =  mapper.writeValueAsString(rows);

        return out;
    }
}
