package com.example.demoserver.servlets;

import com.example.demoserver.JacksonHelper;
import com.example.demoserver.data.*;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "OrderServlet",value = "/orders")
public class OrderServlet extends CustomServlet{


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
                json = objectMapper.writeValueAsString(OrderDao.getAll());
            }else{
                objId = Integer.parseInt(request.getParameter("id"));
                json = objectMapper.writeValueAsString(OrderDao.get(objId));
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
        Order order = objectMapper.readValue(body,Order.class);
        if(order.getItemId()==null || order.getOrgId()==null || order.getInvoiceId()==null
                || order.getCustomerName()==null){
            response.setStatus(400);
            return;
        }
        Order out = OrderDao.save(order);
        if(out!=null){
            int old = StockDao.getWithWarIdAndItemId(order.getWarId(),order.getItemId()).getCount();
            StockDao.updateWithWarIdAndItemId(order.getWarId(),order.getItemId(),old-order.getQuantity());
            response.getOutputStream().print(objectMapper.writeValueAsString(out));
        }else {
            response.setStatus(400);
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

        if (OrderDao.delete(objId)==1){
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
        Order object =  objectMapper.readValue(body, Order.class);
        int  newPrice;
        System.out.println();

        if(object.getPrice()==null&&object.getQuantity()==null){
            response.setStatus(400);
            return;
        }

        if(OrderDao.update(objId,object.getPrice(),object.getQuantity())==1){
            response.getOutputStream().print(objectMapper.writeValueAsString(OrderDao.get(objId)));
        }else {
            response.setStatus(400);
            response.getOutputStream().print("failed");
        }
    }
}
