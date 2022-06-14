package com.example.demoserver.servlets;

import com.example.demoserver.GsonHelper;
import com.example.demoserver.data.Organisation;
import com.example.demoserver.data.OrganisationDao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet(name = "OrganisationServlet", value = "/organisations")
public class OrganisationServlet extends CustomServlet {


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
            Type listType = new TypeToken<List<Organisation>>() {}.getType();

            json = gson.toJson(OrganisationDao.getAll(),listType);
        }else{
            objId = Integer.parseInt(request.getParameter("id"));
            json = gson.toJson(OrganisationDao.get(objId));
        }
        response.getOutputStream().print(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String body =  Utils.getBody(request);
        response.setStatus(200);
        response.setHeader("Content-Type", "application/json");
        Organisation organisation = gson.fromJson(body,Organisation.class);
        if(OrganisationDao.save(organisation)==1){
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

        Organisation object =  gson.fromJson(body, Organisation.class);
//        String name ;

        String name ;


        System.out.println();
        if(object.getName()!=null){
            name = object.getName();
        }else {
            response.setStatus(400);
            return;
        }

        if(OrganisationDao.update(objId,name)==1){
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

        if (OrganisationDao.delete(objId)==1){
            resp.setStatus(204);
            resp.getOutputStream().print("Deleted");
            return;
        }else{
            resp.setStatus(400);
            resp.getOutputStream().print("Not deleted");
        }
    }

}
