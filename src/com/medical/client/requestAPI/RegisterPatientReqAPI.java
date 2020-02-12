package com.medical.client.requestAPI;

import com.medical.client.responseAPI.RegisterPatientResAPI;
import org.json.simple.JSONObject;
import javax.servlet.annotation.WebServlet;
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

@WebServlet(name = "RegisterPatientReqAPI", urlPatterns = {"/createPatientAccount"})
public class RegisterPatientReqAPI extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        RegisterPatientResAPI resAPI = new RegisterPatientResAPI();
        try {
            String userName = request.getParameter("userName");
            JSONObject object = new JSONObject();
            object.put("username", userName);

            URL url = new URL("http://localhost:8082/createAccount");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStream output = conn.getOutputStream();
            OutputStreamWriter outputWriter = new OutputStreamWriter(output, StandardCharsets.UTF_8);
            outputWriter.write(object.toString());
            outputWriter.flush();
            outputWriter.close();
            if(conn.getResponseCode()== HttpURLConnection.HTTP_OK) {
                resAPI.readResponse(conn);
            }else{
                System.out.println("Cannot connect to server...try after sometime....");
            }

        }catch (Exception e){
            System.out.println("Something went wrong try again......");
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        PrintWriter writer = response.getWriter();
        writer.println("BAD_REQUEST.....try again");
    }
}
