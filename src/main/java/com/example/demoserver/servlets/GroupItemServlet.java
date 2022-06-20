package com.example.demoserver.servlets;

import com.example.demoserver.JacksonHelper;
import com.example.demoserver.data.GroupItem;
import com.example.demoserver.data.GroupItemDao;
import com.example.demoserver.data.Item;
import com.example.demoserver.data.ItemDao;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GroupItemServlet",value = "/groups")
public class GroupItemServlet extends CustomServlet{

    ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        super.init();
        objectMapper = JacksonHelper.getObjectMapper();

    }

    @Override
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("id")==null){
            resp.setStatus(400);
            return;
        }
        int objId = Integer.parseInt(req.getParameter("id"));

            if(GroupItemDao.deleteWithItems(objId)==1){
                resp.setStatus(204);
                resp.getOutputStream().print("Deleted");
                return;
            }else{
                resp.setStatus(400);
                resp.getOutputStream().print("Not deleted");
            }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int objId;
        response.setStatus(200);
        response.setHeader("Content-Type", "application/json");
        String json = "";
        try {
            if(request.getParameter("id")==null){
                json = objectMapper.writeValueAsString(GroupItemDao.getAll());
            }else{
                objId = Integer.parseInt(request.getParameter("id"));
                GroupItem out = GroupItemDao.get(objId);

                if(out==null){
                    throw new NullPointerException();
                }else {
                    json = objectMapper.writeValueAsString(out);

                }
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
        GroupItem item = objectMapper.readValue(body,GroupItem.class);
        GroupItem out = GroupItemDao.saveWithItems(item);

        if(out!= null){
            response.getOutputStream().print(objectMapper.writeValueAsString(out));
        }else {
            response.setStatus(400);
            response.getOutputStream().print("Creation Failed");
        }
    }
}
