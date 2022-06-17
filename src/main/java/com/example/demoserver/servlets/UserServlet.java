package com.example.demoserver.servlets;

import com.example.demoserver.JacksonHelper;
import com.example.demoserver.data.User;
import com.example.demoserver.data.UserDao;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

@WebServlet(name = "UserServlet",urlPatterns = {"/users/login","/users/signup"})
public class UserServlet extends CustomServlet {
    ObjectMapper objectMapper;
    @Override
    public void init() throws ServletException {
        objectMapper = JacksonHelper.getObjectMapper();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = Utils.getBody(req);
        resp.setContentType("application/json");
        if(!body.contains("email") || !body.contains("password")){
            resp.setStatus(400);
            return;
        }
        JsonNode jsonNode = objectMapper.readTree(body);
        String uri =req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/")+1 ,req.getRequestURI().length());
        User out;

//        System.out.println(uri);
        if(uri.equals("login")){
            out = UserDao.get(jsonNode.findValue("email").asText(),jsonNode.findValue("password").asText());

        }else {
             out = UserDao.save(jsonNode.findValue("email").asText(),jsonNode.findValue("password").asText());

        }
        System.out.println(out);
        if(out==null){
            resp.setStatus(404);
            return;
        }
        resp.setStatus(200);
        req.getSession(true).setAttribute("user",out);
//        resp.sendRedirect(req.); // Go to some start page.

        resp.getOutputStream().print(objectMapper.writeValueAsString(out));
//        System.out.println(body);
    }
    @Override
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }
}
