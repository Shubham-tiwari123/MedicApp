package com.medical.client.requestAPI;

import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginHospital",urlPatterns = {"/login","/sign-in"})
public class LoginHospital extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("hittttt");

        // check username and password
        String userName = request.getParameter("email");
        String password = request.getParameter("pass");
        System.out.println("email:"+userName+",,"+password);

        JSONObject jsonObject = new JSONObject();
        if(true){
            System.out.println("forwarding");
            Cookie loginCookie = new Cookie("loginStatus",userName);
            response.addCookie(loginCookie);
            jsonObject.put("statusCode",200);
        }else{
            jsonObject.put("statusCode",400);
        }
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/").forward(request,response);
    }
}
