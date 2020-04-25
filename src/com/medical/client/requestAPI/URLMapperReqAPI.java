package com.medical.client.requestAPI;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "URLMapperReqAPI", urlPatterns = {
        "/dashboard",
        "/register_patient",
        "/medical_form",
        "/view_record",
        "/detail_view",
        "/page_not_found",
        "/error_page",
        "/sign_in",
        "/sign_up",
        "/logout",
        /*URLs for Server APIs*/
        "/get_keys",
        "/get_user_info",
        "/check_login",
        "/login_hospital",
        "/register_hospital",
        "/register_new_patient",
        "/send_record",
        "/connect_server"
})

public class URLMapperReqAPI extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //System.out.println("link:"+request.getRequestURL().toString());
        Cookie cookie;
        Cookie[] cookies;
        cookies = request.getCookies();
        String loginStatus = "false";
        if(cookies!=null){
            for (Cookie value : cookies) {
                cookie = value;
                if (cookie.getName().equals("loginHospitalStatus")) {
                    loginStatus = cookie.getValue();
                    break;
                }
            }
        }

        if ((request.getRequestURL().toString()).equals("http://localhost:8080/dashboard")){
            if(!loginStatus.equals("false"))
                request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
            else
                response.sendRedirect("/");
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8080/register_patient")) {
            if(!loginStatus.equals("false"))
                request.getRequestDispatcher("/register_patient.jsp").forward(request, response);
            else
                response.sendRedirect("/");
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8080/sign_in")) {
            if(loginStatus.equals("false"))
                request.getRequestDispatcher("/").forward(request, response);
            else
             response.sendRedirect("/dashboard");
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8080/sign_up")) {
            if(loginStatus.equals("false"))
                request.getRequestDispatcher("register.jsp").forward(request, response);
            else
                response.sendRedirect("/dashboard");
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8080/medical_form")) {
            if(!loginStatus.equals("false"))
                request.getRequestDispatcher("/medical_form.jsp").forward(request, response);
            else
                response.sendRedirect("/");
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8080/view_record")) {
            if(!loginStatus.equals("false"))
                request.getRequestDispatcher("/view_record.jsp").forward(request, response);
            else
                response.sendRedirect("/");
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8080/get_keys")) {
            GetClientKeysReqAPI reqAPI = new GetClientKeysReqAPI();
            reqAPI.doGet(request,response);
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8080/check_login")) {
            LoginHospitalReqAPI reqAPI = new LoginHospitalReqAPI();
            reqAPI.doGet(request,response);
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8080/detail_view")) {
            if (!loginStatus.equals("false"))
                request.getRequestDispatcher("/detail_view.jsp").forward(request, response);
            else
                response.sendRedirect("/");
        }
        else if((request.getRequestURL().toString()).equals("http://localhost:8080/logout")) {
            cookie = new Cookie("loginHospitalStatus","false");
            response.addCookie(cookie);
            response.sendRedirect("/");
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8080/error_page")) {
            response.sendRedirect("/page_not_found");
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8080/page_not_found")) {
            request.getRequestDispatcher("/error_page.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        if ((request.getRequestURL().toString()).equals("http://localhost:8080/get_user_info")){
            System.out.println("Calling get record method");
            GetRecordReqAPI reqAPI = new GetRecordReqAPI();
            reqAPI.doPost(request,response);
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8080/login_hospital")){
            LoginHospitalReqAPI reqAPI = new LoginHospitalReqAPI();
            reqAPI.doPost(request,response);
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8080/register_hospital")){
            RegisterHospitalReqAPI reqAPI = new RegisterHospitalReqAPI();
            reqAPI.doPost(request,response);
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8080/register_new_patient")){
            RegisterPatientReqAPI reqAPI = new RegisterPatientReqAPI();
            reqAPI.doPost(request,response);
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8080/send_record")){
            SendRecordReqAPI reqAPI = new SendRecordReqAPI();
            reqAPI.doPost(request,response);
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8080/connect_server")){
            ServerReqAPI reqAPI = new ServerReqAPI();
            reqAPI.doPost(request,response);
        }
    }
}
