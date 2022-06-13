package com.example.demoserver;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.hibernate.Session;
import org.hibernate.mapping.Set;
import org.hibernate.query.Query;

import java.io.*;
import java.sql.*;
import java.util.Arrays;

import javax.servlet.http.*;
import javax.servlet.annotation.*;


@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;



    Gson gson;
    Connection conn;
    public void init() {
        message = "Hello World!";
        gson = new Gson();
        conn = Database.getConnection();

    }

    public void doGet(HttpServletRequest request, HttpServletResponse resp) throws IOException {
//        response.setContentType("text/html");

        int objId;
        if(request.getParameter("id")!=null){
            objId = Integer.parseInt(request.getParameter("id"));


        }

        // Hello
        PrintWriter out = resp.getWriter();
//        out.println("<html><body>");
//        out.println("<h1>" + message + "</h1>");


        Statement st = null;
        JsonArray jsonArray = new JsonArray();
        try {
            st = conn.createStatement();
            ResultSet rs =  st.executeQuery("select id,name,email from users;");

            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String   email  = rs.getString ("email");
//                out.print("<br>");
//                JsonObject jsonObject = new JsonObject();
                User user = new User(id,name,email);


                jsonArray.add(gson.toJsonTree(user));

//                out.println("<tr><td>"+name+"</td><td>"+email+"</td></tr>");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        resp.setStatus(200);
        resp.setHeader("Content-Type", "application/json");
        out.print(jsonArray.toString());
//        out.println("</body></html>");
       
        
        




    }



    public void destroy() {
    }
}