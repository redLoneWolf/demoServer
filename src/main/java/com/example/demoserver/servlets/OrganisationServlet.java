package com.example.demoserver.servlets;

import com.example.demoserver.JacksonHelper;
import com.example.demoserver.data.Organisation;
import com.example.demoserver.data.OrganisationDao;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "OrganisationServlet", value = "/organisations")
public class OrganisationServlet extends CustomServlet {


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
        try {
            if(request.getParameter("id")==null){


                json = objectMapper.writeValueAsString(OrganisationDao.getAll());
            }else{
                objId = Integer.parseInt(request.getParameter("id"));
                Organisation organisation = OrganisationDao.get(objId);
                if(organisation==null){throw  new NullPointerException();}
                json = objectMapper.writeValueAsString(organisation);
            }
            response.getOutputStream().print(json);
        }catch (NullPointerException e){
            response.setStatus(404);
            response.getOutputStream().print("{Item not found}");

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String body =  Utils.getBody(request);
        response.setStatus(200);
        response.setHeader("Content-Type", "application/json");
        Organisation organisation = objectMapper.readValue(body,Organisation.class);
         Organisation organisation1 = OrganisationDao.save(organisation);
        if(organisation1!=null){

            response.getOutputStream().print(objectMapper.writeValueAsString(organisation1));
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

        Organisation object = objectMapper.readValue(body, Organisation.class);
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
            response.getOutputStream().print(objectMapper.writeValueAsString(OrganisationDao.get(objId)));
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
