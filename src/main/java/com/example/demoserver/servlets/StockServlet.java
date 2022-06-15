package com.example.demoserver.servlets;

import com.example.demoserver.JacksonHelper;
import com.example.demoserver.data.Stock;
import com.example.demoserver.data.StockDao;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "StockServlet",value ="/stocks")
public class StockServlet extends CustomServlet{

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
            if(request.getParameter("id")!=null ){
                objId = Integer.parseInt(request.getParameter("id"));
                json = objectMapper.writeValueAsString(StockDao.get(objId));
            }else if(request.getParameter("itemId")!=null){
                objId = Integer.parseInt(request.getParameter("itemId"));

                json = objectMapper.writeValueAsString(StockDao.getWithItemId(objId));
            }else {
                json = objectMapper.writeValueAsString(StockDao.getAll());
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
        Stock stock = objectMapper.readValue(body,Stock.class);
        if(stock.getCount()==null){
            response.setStatus(400);

            return;
        }
        Stock out =StockDao.save(stock);
        if(out!=null){
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

        if (StockDao.delete(objId)==1){
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
        Stock object =  objectMapper.readValue(body, Stock.class);
        int  newCount ;
        System.out.println();

        if(object.getCount()==null){
            response.setStatus(400);
            return;
        }

        newCount = object.getCount();
        if(StockDao.update(objId,newCount)==1){
            response.getOutputStream().print(objectMapper.writeValueAsString(StockDao.get(objId)));
        }else {
            response.getOutputStream().print("failed");
        }
    }
}
