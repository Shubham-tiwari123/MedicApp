package com.medical.server.requestAPI;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        "/get_user_info"       //getChain

})

public class URLMappingResAPI extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if ((request.getRequestURL().toString()).equals("http://localhost:8082/admin_dashboard")) {
            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8082/hospitals_connected")) {
            request.getRequestDispatcher("/hospitals.jsp").forward(request, response);
        }
        else if ((request.getRequestURL().toString()).equals("http://localhost:8082/patient_connected")) {
            request.getRequestDispatcher("/patients.jsp").forward(request, response);
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
        }else{
            //send error as response to the client
        }
    }
}
