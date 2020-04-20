package com.medical.client.requestAPI;

import com.medical.client.responseAPI.LoginHospitalResAPI;
import com.medical.client.utils.ConstantClass;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "LoginHospitalReqAPI")
public class LoginHospitalReqAPI extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            System.out.println("Login Hospital");
            LoginHospitalResAPI resAPI = new LoginHospitalResAPI();

            String userName = request.getParameter("email");
            String password = request.getParameter("pass");
            System.out.println("email:" + userName + "," + password);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", userName);
            jsonObject.put("pass", password);
            URL url = new URL(ConstantClass.LOGIN_HOSPITAL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStream output = conn.getOutputStream();
            OutputStreamWriter outputWriter = new OutputStreamWriter(output, StandardCharsets.UTF_8);
            outputWriter.write(jsonObject.toString());
            outputWriter.flush();
            outputWriter.close();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("Server successfully hit .....");
                resAPI.readResponse(conn, response);
            } else {
                jsonObject = new JSONObject();
                jsonObject.put("statusCode", 400);
                PrintWriter printWriter = response.getWriter();
                printWriter.println(jsonObject);
            }
        }catch (Exception e){
            System.out.println("Exception:"+e);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("statusCode", 400);
            PrintWriter printWriter = response.getWriter();
            printWriter.println(jsonObject);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = new JSONObject();
        String hospitalUserName = "false";
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
                        System.out.println("Val0:" + val[0] + " val1:" + val[1] + " val2:" + val[2]);
                        if (val[2].equals("true")) {
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
