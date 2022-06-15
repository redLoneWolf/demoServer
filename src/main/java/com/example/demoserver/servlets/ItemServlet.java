package com.example.demoserver.servlets;

import com.example.demoserver.JacksonHelper;
import com.example.demoserver.data.Item;
import com.example.demoserver.data.ItemDao;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ItemServlet", value = "/items")
public class ItemServlet extends CustomServlet {

    ObjectMapper objectMapper;
    @Override
    public void init() throws ServletException {
        super.init();

        objectMapper = JacksonHelper.getObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int objId;
        response.setStatus(200);
        response.setHeader("Content-Type", "application/json");
        String json = "";
        if(request.getParameter("id")==null){


            json = objectMapper.writeValueAsString(ItemDao.getAll());
        }else{
            objId = Integer.parseInt(request.getParameter("id"));
            json = objectMapper.writeValueAsString(ItemDao.getItem(objId));
        }
        response.getOutputStream().print(json);
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String body =  Utils.getBody(request);
            response.setStatus(200);
            response.setHeader("Content-Type", "application/json");
            Item item = objectMapper.readValue(body,Item.class);
            if(ItemDao.save(item)==1){
                response.getOutputStream().print("Created Success Fully");
            }else {
                response.getOutputStream().print("Creation Failed");
            }
    }

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

        JsonNode object =  objectMapper.readTree(body);

//        response.getOutputStream().print(object.toString());
//        "name","desc","cost","sale"

//        if(!object.entrySet().contains("id")){
//            response.setStatus(400);
//
//            return;
//        }
        if(ItemDao.update(objId,object.fields())==1){
            response.getOutputStream().print("success");
        }else {
            response.getOutputStream().print("failed");
        }


    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
//        super.doDelete(req, resp);
        if(req.getParameter("id")==null){
            resp.setStatus(400);
            return;
        }
        int objId = Integer.parseInt(req.getParameter("id"));

        if (ItemDao.delete(objId)==1){
            resp.setStatus(204);
            resp.getOutputStream().print("Deleted");
            return;
        }else{
            resp.setStatus(400);
            resp.getOutputStream().print("Not deleted");
        }
    }
}
