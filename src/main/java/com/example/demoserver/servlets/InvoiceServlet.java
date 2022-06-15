package com.example.demoserver.servlets;

import com.example.demoserver.JacksonHelper;
import com.example.demoserver.data.*;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "InvoiceServlet",value = "/invoices")
public class InvoiceServlet extends CustomServlet{

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
        try{
            if(request.getParameter("id")==null){

                json = objectMapper.writeValueAsString(InvoiceDao.getAll());
            }else{
                objId = Integer.parseInt(request.getParameter("id"));
                json = objectMapper.writeValueAsString(InvoiceDao.getWithOrders(objId));
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
        Invoice invoice = objectMapper.readValue(body, Invoice.class);
        if(invoice.getCustomerName()==null || invoice.getOrgId()==null){
            response.setStatus(400);
            return;
        }
        Invoice out = invoice.getOrders()==null?InvoiceDao.save(invoice):InvoiceDao.saveWithOrders(invoice);
        if(out!=null){
            response.getOutputStream().print(objectMapper.writeValueAsString(out));
        }else {
            response.getOutputStream().print("Creation Failed");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("id")==null){
            resp.setStatus(400);
            return;
        }
        int objId = Integer.parseInt(req.getParameter("id"));

        if (InvoiceDao.delete(objId)==1){
            resp.setStatus(204);
            resp.getOutputStream().print("Deleted");

        }else{
            resp.setStatus(400);
            resp.getOutputStream().print("Not deleted");
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
        Invoice object =  objectMapper.readValue(body, Invoice.class);
        System.out.println();

        if(object.getTax()==null&&object.getDiscount()==null){
            response.setStatus(400);
            return;
        }


        if(InvoiceDao.update(objId,object.getTax(),object.getDiscount())==1){
            response.getOutputStream().print(objectMapper.writeValueAsString(InvoiceDao.get(objId)));
        }else {
            response.getOutputStream().print("failed");
        }
    }
}
