package com.example.demoserver;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
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

//        if(req.getSession().getAttribute("user")==null) {
//            String end = req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/")+1,req.getRequestURI().length());
//            if (end.equals("login")|| end.equals("signup")) {
//                chain.doFilter(request,response);
//                return;
//            }
//            response.getOutputStream().print("login please");
//            ((HttpServletResponse)response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        }else {
//            System.out.println(req.getSession().getAttribute("user"));
//            chain.doFilter(request, response);
//        }


//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        httpResponse.getWriter().write("Your IP address "+ipAddress);
        // pass the request along the filter chain
            chain.doFilter(request, response);

    }
}
