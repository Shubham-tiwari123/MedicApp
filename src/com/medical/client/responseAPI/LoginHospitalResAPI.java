package com.medical.client.responseAPI;

import com.medical.client.entity.HospitalDetails;
import com.medical.client.service.ExtraFunctions;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;

@WebServlet(name = "LoginHospitalResAPI")
public class LoginHospitalResAPI extends HttpServlet {

    public void readResponse(HttpURLConnection conn,HttpServletResponse response) throws IOException {
        try {
            ExtraFunctions extraFunctions = new ExtraFunctions();
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
            JSONObject jsonObject = new JSONObject();
            if(status==200) {
                String hospitalData = (String) object.get("details");
                HospitalDetails details = extraFunctions.convertJsonToJava(hospitalData, HospitalDetails.class);
                System.out.println("forwarding");
                StringBuilder builder = new StringBuilder();
                builder.append(details.getUserName()).append("&").append("true");
                System.out.println("Builder:"+builder.toString());
                Cookie loginCookie = new Cookie("loginHospitalStatus", builder.toString());
                response.addCookie(loginCookie);
                jsonObject.put("statusCode", 200);
            }else
                jsonObject.put("statusCode", 400);
            PrintWriter printWriter = response.getWriter();
            printWriter.println(jsonObject);
        }catch (Exception e){
            System.out.println("Exception:"+e);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("statusCode", 400);
            PrintWriter printWriter = response.getWriter();
            printWriter.println(jsonObject);
        }
    }
}
