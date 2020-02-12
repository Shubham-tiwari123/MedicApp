package com.medical.client.responseAPI;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;

@WebServlet(name = "RegisterHospitalResApi")
public class RegisterHospitalResApi extends HttpServlet {

    private JSONObject jsonObject = new JSONObject();
    private String userName;
    private long status;

    public void readResponse(HttpURLConnection conn, String userName, HttpServletResponse response,
                             HttpServletRequest request){
        try {
            this.userName = userName;
            StringBuffer clientData = new StringBuffer();
            InputStream inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String readData = "";

            while ((readData = bufferedReader.readLine()) != null)
                clientData.append(readData);

            System.out.println("Getting response from server:\n" + clientData.toString());
            JSONParser jsonParser = new JSONParser();
            JSONObject object = (JSONObject) jsonParser.parse(clientData.toString());
            this.status = (long) object.get("statusCode");
            if(status==200){
                //forward to login page
                request.getRequestDispatcher("").forward(request,response);
            }else{
                //return to same page with error msg
                request.getRequestDispatcher("").forward(request,response);
            }

        }catch (Exception e){
            System.out.println("status: " + status);
        }
    }

    private void doPost(){
        jsonObject.put("status",status);
        jsonObject.put("userName",userName);
    }
}