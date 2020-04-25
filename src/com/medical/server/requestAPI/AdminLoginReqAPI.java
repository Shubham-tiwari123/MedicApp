package com.medical.server.requestAPI;

import com.medical.server.utils.VariableClass;
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
        System.out.println("email:"+userName+","+password);

        JSONObject jsonObject = new JSONObject();
        if(VariableClass.ADMIN_EMAIL.equals(userName) && VariableClass.ADMIN_PASS.equals(password)){
            System.out.println("forwarding");
            Cookie loginCookie = new Cookie("adminLoginStatus","true");
            response.addCookie(loginCookie);
            jsonObject.put("statusCode",200);
        }else{
            jsonObject.put("statusCode",400);
        }
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonObject.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        JSONObject jsonObject = new JSONObject();
        System.out.println("Login hit");
        Cookie[] cookies = request.getCookies();
        Cookie cookie;
        if(cookies!=null){
            System.out.println("Login hit2");
            for (Cookie value : cookies) {
                cookie = value;
                if (cookie.getName().equals("loginHospitalStatus")) {
                    System.out.println("Login hit3");
                    if (cookie.getValue().contains("&")) {
                        String[] val = cookie.getValue().split("&");
                        System.out.println("Val0:" + val[0] + " val1:" + val[1]);
                        if (val[1].equals("true")) {
                            jsonObject.put("statusCode", 200);
                        } else {
                            jsonObject.put("statusCode", 400);
                        }
                    }else {
                        jsonObject.put("statusCode", 400);
                    }
                    break;
                }
            }
        }else
            jsonObject.put("statusCode",400);
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonObject);
    }
}
