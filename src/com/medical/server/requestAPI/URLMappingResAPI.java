package com.medical.server.requestAPI;

import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "URLMappingResAPI", urlPatterns = {
        "/admin_dashboard",
        "/hospitals_connected",
        "/patient_connected",
        "/admin_login",
        "/get_hospitals",
        "/get_patient",
        "/append_record",
        "/connect_server",
        "/login_hospital",
        "/register_hospital",
        "/register_patient",
        "/get_user_info",
        "/page_not_found",
        "/error_page",
        "/check_login",
        "/deactivate_hospital",
        "/activate_hospital",
        "/deactivate_patient",
        "/activate_patient",
})

public class URLMappingResAPI extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Cookie cookie;
        Cookie[] cookies;
        cookies = request.getCookies();
        String loginStatus = "false";
        if(cookies!=null){
            for (Cookie value : cookies) {
                cookie = value;
                if (cookie.getName().equals("adminLoginStatus")) {
                    loginStatus = cookie.getValue();
                    break;
                }
            }
        }

        if ((request.getRequestURL().toString()).equals("http://localhost:8082/admin_dashboard")) {
            if (loginStatus.equals("true")) {
                request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
            }
            else
                response.sendRedirect("/");
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8082/hospitals_connected")) {
            if (loginStatus.equals("true"))
                request.getRequestDispatcher("/hospitals.jsp").forward(request, response);
            else
                response.sendRedirect("/");
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8082/patient_connected")) {
            if (loginStatus.equals("true"))
                request.getRequestDispatcher("/patients.jsp").forward(request, response);
            else
                response.sendRedirect("/");
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8082/get_hospitals")) {
            System.out.println("Getting hospital details");
            RegisterHospitalReqAPI reqAPI = new RegisterHospitalReqAPI();
            reqAPI.doGet(request, response);
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8082/get_patient")) {
            System.out.println("Getting patient details");
            RegisterPatientReqAPI reqAPI = new RegisterPatientReqAPI();
            reqAPI.doGet(request, response);
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8082/check_login")) {
            System.out.println("Checking login:"+loginStatus);
            JSONObject jsonObject = new JSONObject();
            if (loginStatus.equals("true"))
                jsonObject.put("statusCode",200);
            else
                jsonObject.put("statusCode",400);
            PrintWriter printWriter = response.getWriter();
            printWriter.println(jsonObject);
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8082/error_page")) {
            response.sendRedirect("/page_not_found");
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8082/page_not_found")) {
            request.getRequestDispatcher("/error_page.jsp").forward(request, response);
        }else{
            response.sendRedirect("/page_not_found");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if ((request.getRequestURL().toString()).equals("http://localhost:8082/admin_login")) {
            System.out.println("Calling admin login");
            AdminLoginReqAPI reqAPI = new AdminLoginReqAPI();
            reqAPI.doPost(request, response);
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8082/append_record")) {
            System.out.println("Calling append block");
            AppendBlockReqAPI reqAPI = new AppendBlockReqAPI();
            reqAPI.doPost(request, response);
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8082/connect_server")) {
            System.out.println("Calling connect server");
            ConnectToHospitalReqAPI reqAPI = new ConnectToHospitalReqAPI();
            reqAPI.doPost(request, response);
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8082/login_hospital")) {
            System.out.println("Calling login hospital");
            LoginHospitalReqAPI reqAPI = new LoginHospitalReqAPI();
            reqAPI.doPost(request, response);
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8082/register_hospital")) {
            System.out.println("Calling register hospital");
            RegisterHospitalReqAPI reqAPI = new RegisterHospitalReqAPI();
            reqAPI.doPost(request, response);
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8082/register_patient")) {
            System.out.println("Calling register patient");
            RegisterPatientReqAPI reqAPI = new RegisterPatientReqAPI();
            reqAPI.doPost(request, response);
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8082/get_user_info")) {
            System.out.println("Calling send chain");
            SendChainReqAPI reqAPI = new SendChainReqAPI();
            reqAPI.doPost(request, response);
        }

        else if ((request.getRequestURL().toString()).equals("http://localhost:8082/deactivate_hospital")) {
            System.out.println("Calling deactivate hospital");
            HandleHospitalReqAPI reqAPI = new HandleHospitalReqAPI();
            reqAPI.deactivateHospital(request, response);
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8082/activate_hospital")) {
            System.out.println("Calling activate hospital");
            HandleHospitalReqAPI reqAPI = new HandleHospitalReqAPI();
            reqAPI.activateHospital(request, response);
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8082/deactivate_patient")) {
            System.out.println("Calling deactivate patient");
            HandlePatientReqAPI reqAPI = new HandlePatientReqAPI();
            reqAPI.deactivatePatient(request, response);
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8082/activate_patient")) {
            System.out.println("Calling activate patient");
            HandlePatientReqAPI reqAPI = new HandlePatientReqAPI();
            reqAPI.activatePatient(request, response);
        }

    }
}
