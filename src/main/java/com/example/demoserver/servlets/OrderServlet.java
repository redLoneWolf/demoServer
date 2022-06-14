package com.example.demoserver.servlets;

import com.example.demoserver.GsonHelper;
import com.example.demoserver.data.Order;
import com.example.demoserver.data.OrderDao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet(name = "OrderServlet",value = "/orders")
public class OrderServlet extends CustomServlet{


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
            Type listType = new TypeToken<List<Order>>() {}.getType();

            json = gson.toJson(OrderDao.getAll(),listType);
        }else{
            objId = Integer.parseInt(request.getParameter("id"));
            json = gson.toJson(OrderDao.get(objId));
        }
        response.getOutputStream().print(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String body =  Utils.getBody(request);
        response.setStatus(200);
        response.setHeader("Content-Type", "application/json");
        Order order = gson.fromJson(body,Order.class);
        if(order.getItemId()==null || order.getOrgId()==null || order.getInvoiceId()==null
                || order.getCustomerName()==null|| order.getPrice()==null){

            response.setStatus(400);

            return;
        }
        if(OrderDao.save(order)==1){
            response.getOutputStream().print("Created Success Fully");
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
        Order object =  gson.fromJson(body, Order.class);
        int  newPrice;
        System.out.println();

        if(object.getPrice()==null&&object.getQuantity()==null){
            response.setStatus(400);
            return;
        }

        if(OrderDao.update(objId,object.getPrice(),object.getQuantity())==1){
            response.getOutputStream().print("success");
        }else {
            response.getOutputStream().print("failed");
        }
    }
}
