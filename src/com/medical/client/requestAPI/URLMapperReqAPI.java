package com.medical.client.requestAPI;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//DashboardReqAPI
@WebServlet(name = "URLMapperReqAPI", urlPatterns = {"/dashboard","/register_patient","/medical_form",
        "/view_record","/detail_view"})
public class URLMapperReqAPI extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if((request.getRequestURL().toString()).equals("http://localhost:8080/dashboard"))
            request.getRequestDispatcher("/dashboard.jsp").forward(request,response);
        else if((request.getRequestURL().toString()).equals("http://localhost:8080/register_patient"))
            request.getRequestDispatcher("/register_patient.jsp").forward(request,response);
        else if((request.getRequestURL().toString()).equals("http://localhost:8080/medical_form"))
            request.getRequestDispatcher("/medical_form.jsp").forward(request,response);
        else if((request.getRequestURL().toString()).equals("http://localhost:8080/view_record"))
            request.getRequestDispatcher("/view_record.jsp").forward(request,response);
        else if((request.getRequestURL().toString()).equals("http://localhost:8080/detail_view"))
            request.getRequestDispatcher("/detail_view.jsp").forward(request,response);
    }
}
