package com.example.demoserver;

import com.example.demoserver.data.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet(name = "WarehouseServlet",value = "/warehouses")
public class WarehouseServlet extends CustomServlet{

    Gson gson;
    @Override
    public void init() throws ServletException {
        super.init();

        gson = GsonHelper.getGson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int objId;
        response.setStatus(200);
        response.setHeader("Content-Type", "application/json");
        String json = "";
        if(request.getParameter("id")==null){
            Type listType = new TypeToken<List<Warehouse>>() {}.getType();

            json = gson.toJson(WarehouseDao.getAll(),listType);
        }else{
            objId = Integer.parseInt(request.getParameter("id"));
            json = gson.toJson(WarehouseDao.get(objId));
        }
        response.getOutputStream().print(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String body =  Utils.getBody(request);
        response.setStatus(200);
        response.setHeader("Content-Type", "application/json");
        Warehouse war = gson.fromJson(body,Warehouse.class);
        if(war.getOrgId()<1){

            response.getOutputStream().print("Creation Failed");
            return;
        }
        if(WarehouseDao.save(war)==1){
            response.getOutputStream().print("Created Success Fully");
        }else {
            response.getOutputStream().print("Creation Failed");
        }
    }

    @Override
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        Warehouse object =  gson.fromJson(body, Warehouse.class);
        String name ;
        if(object.getName()!=null){
            name = object.getName();
        }else {
            response.setStatus(400);
            return;
        }
        if(WarehouseDao.update(objId,name)==1){
            response.getOutputStream().print("success");
        }else {
            response.getOutputStream().print("failed");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if(req.getParameter("id")==null){
            resp.setStatus(400);
            return;
        }
        int objId = Integer.parseInt(req.getParameter("id"));

        if (WarehouseDao.delete(objId)==1){
            resp.setStatus(204);
            resp.getOutputStream().print("Deleted");
        }else{
            resp.setStatus(400);
            resp.getOutputStream().print("Not deleted");
        }
    }

}
