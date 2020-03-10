package com.medical.server.requestAPI;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "URLMappingResAPI",urlPatterns = {"/admin_dashboard","/hospitals_connected",
        "/patient_connected"})

public class URLMappingResAPI extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if((request.getRequestURL().toString()).equals("http://localhost:8082/admin_dashboard"))
            request.getRequestDispatcher("/dashboard.jsp").forward(request,response);
        else if((request.getRequestURL().toString()).equals("http://localhost:8082/hospitals_connected"))
            request.getRequestDispatcher("/hospitals.jsp").forward(request,response);
        else if((request.getRequestURL().toString()).equals("http://localhost:8082/patient_connected"))
            request.getRequestDispatcher("/patients.jsp").forward(request,response);
    }
}
