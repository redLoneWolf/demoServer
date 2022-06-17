package com.example.demoserver.servlets;

import com.example.demoserver.data.ReportsDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

@WebServlet(name = "ReportServlet",value = "/reports")
public class ReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,String[]> map = req.getParameterMap();

        resp.setHeader("Content-Type", "application/json");

        System.out.println(map);
        String json ="";

        try {
            json = ReportsDao.getItemReports(req.getParameterMap());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.getOutputStream().print(json);

    }




}
