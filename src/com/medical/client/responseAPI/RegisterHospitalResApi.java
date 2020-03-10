package com.medical.client.responseAPI;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;

@WebServlet(name = "RegisterHospitalResApi")
public class RegisterHospitalResApi extends HttpServlet {

    public void readResponse(HttpURLConnection conn, String userName, HttpServletResponse response,
                             HttpServletRequest request){
        try {
            StringBuffer clientData = new StringBuffer();
            InputStream inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String readData = "";
            while ((readData = bufferedReader.readLine()) != null)
                clientData.append(readData);

            System.out.println("Getting response from server:\n" + clientData.toString());
            JSONParser jsonParser = new JSONParser();
            JSONObject object = (JSONObject) jsonParser.parse(clientData.toString());
            long status = (long) object.get("statusCode");
            System.out.println("status code:"+status);
            if(status==200){
                //forward to login page
                //set cookie with hospital username
                /*Cookie loginCookie = new Cookie("loginStatus",userName);
                response.addCookie(loginCookie);
                request.getRequestDispatcher("").forward(request,response);*/
            }else if(status==401){
                //return to same page with error msg "username already exists"
                request.getRequestDispatcher("").forward(request,response);
            }else{
                //return to same page with error msg "server error"
                request.getRequestDispatcher("").forward(request,response);
            }

        }catch (Exception e){
            System.out.println("Exception:"+e);
        }
    }
}