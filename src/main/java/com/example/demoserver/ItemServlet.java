package com.example.demoserver;

import com.example.demoserver.data.Item;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@WebServlet(name = "ItemServlet", value = "/items")
public class ItemServlet extends CustomServlet {
    Connection connection;
    Gson gson;
    @Override
    public void init() throws ServletException {
        super.init();
        connection = Database.getConnection();
        gson = GsonHelper.getGson();
    }





    String getAll(){
        JsonArray jsonArray = new JsonArray();
        try {
            Statement st =  connection.createStatement();
            String query = "select id,orgId,name,description,costPrice,sellingPrice,createdAt from items;";
            ResultSet rs =  st.executeQuery(query);

            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String desc =  rs.getString("description");
                int orgId =  rs.getInt("orgId");
                int cost = rs.getInt("costPrice");
                int sell =  rs.getInt("sellingPrice");
                String created =  rs.getTimestamp("createdAt").toString();
                Item item = new Item(id,name,desc,cost,sell,orgId,created);
                jsonArray.add(gson.toJsonTree(item));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return jsonArray.toString();
    }

    String getItem(int id){
        String json="";
        try {
            Statement st =  connection.createStatement();
            String query = "select id,orgId,name,description,costPrice,sellingPrice,createdAt from items where id="+id+" limit  1;";
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
                json= gson.toJson(item);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int objId;
        response.setStatus(200);
        response.setHeader("Content-Type", "application/json");
        String json = "";
        if(request.getParameter("id")==null){
            json = getAll();
        }else{
            objId = Integer.parseInt(request.getParameter("id"));
            json = getItem(objId);
        }
        response.getOutputStream().print(json);
    }

    int save(Item item){
        try {
            Statement st = connection.createStatement();
            String query = "insert into items(orgId, name, description,  costPrice, sellingPrice) values ("+ item.getOrgId()+",'" +
                    item.getName()+"','"+item.getDescription()+"',"+item.getCostPrice()+","+item.getSellingPrice()+");";
            return st.executeUpdate(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    };

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String body =  Utils.getBody(request);
            response.setStatus(200);
            response.setHeader("Content-Type", "application/json");
//        System.out.println(body);
            Item item = gson.fromJson(body,Item.class);
            if(save(item)==1){
                response.getOutputStream().print("Created Success Fully");
            }else {
                response.getOutputStream().print("Creation Failed");
            }
    }

//    boolean allowEdit(String key){
//        return(key=="name"|| key=="desc"||key=="cost"||key=="sale");
//    }

    @Override
    public void doPatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String body = Utils.getBody(request);
        int objId = 0;
        response.setStatus(200);
        response.setHeader("Content-Type", "application/json");
        if(request.getParameter("id")!=null){
            objId = Integer.parseInt(request.getParameter("id"));
        }else {
            response.setStatus(400);
            return;
        }

        JsonObject object =  gson.fromJson(body,JsonObject.class);

//        response.getOutputStream().print(object.toString());
//        "name","desc","cost","sale"

//        if(!object.entrySet().contains("id")){
//            response.setStatus(400);
//
//            return;
//        }

        StringBuilder query = new StringBuilder("update items set ");

        for (Map.Entry<String, JsonElement> entry:object.entrySet()){
            String key = entry.getKey();
            if(key.equals("name")){

                query.append("name='").append(entry.getValue().getAsString()).append("',");

            }else if (key.equals("desc")){

                query.append("description='").append(entry.getValue().getAsString()).append("',");
            }
            else if (key.equals("cost")){

                query.append("costPrice=").append(entry.getValue().getAsString()).append(",");
            }
            else if (key.equals("sale")){

                query.append("sellingPrice=").append(entry.getValue().getAsString()).append(" ");
            }
//            else if (key.equals("id")) {
//                objId = entry.getValue().getAsInt();
//            }
        }
        query.append(" where id=").append(objId).append(" ;");
//        System.out.println(query);

        try {
            Statement st = connection.createStatement();
            st.executeUpdate(query.toString());
            response.getOutputStream().print("Success");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
