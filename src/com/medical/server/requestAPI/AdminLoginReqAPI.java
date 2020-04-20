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

@WebServlet(name = "AdminLoginRegAPI")
public class AdminLoginReqAPI extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String userName = request.getParameter("email");
        String password = request.getParameter("pass");
        System.out.println("email:"+userName+",,"+password);

        JSONObject jsonObject = new JSONObject();
        if(true){
            System.out.println("forwarding");
            Cookie loginCookie = new Cookie("adminLoginStatus",userName);
            response.addCookie(loginCookie);
            jsonObject.put("statusCode",200);
        }else{
            jsonObject.put("statusCode",400);
        }
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonObject.toString());
    }
}
