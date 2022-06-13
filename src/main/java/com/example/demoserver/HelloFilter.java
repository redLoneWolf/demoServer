package com.example.demoserver;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;




@WebFilter(filterName = "HelloFilter")
public class HelloFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;


        String ipAddress = req.getRemoteAddr();
        System.out.println("IP Address "+ipAddress + ", Time is"
                + new Date().toString());
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        httpResponse.getWriter().write("Your IP address "+ipAddress);
        // pass the request along the filter chain
        chain.doFilter(request, response);
    }
}
