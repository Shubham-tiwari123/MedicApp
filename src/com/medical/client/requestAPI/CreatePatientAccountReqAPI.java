package com.medical.client.requestAPI;

import com.medical.client.responseAPI.CreatePatientAccountResAPI;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "CreatePatientAccountReqAPI", urlPatterns = {"/createPatientAccount"})
public class CreatePatientAccountReqAPI extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CreatePatientAccountResAPI resAPI = new CreatePatientAccountResAPI();
        String userName = request.getParameter("userName");
        System.out.println("user:"+userName);
        JSONObject object = new JSONObject();
        object.put("username",userName);

        URL url = new URL("http://localhost:8082/createAccount");
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        OutputStream output =conn.getOutputStream();
        OutputStreamWriter outputWriter = new OutputStreamWriter(output, StandardCharsets.UTF_8);
        outputWriter.write(object.toString());
        outputWriter.flush();
        outputWriter.close();
        if(conn.getResponseCode()== HttpURLConnection.HTTP_OK) {
            resAPI.readResponse(conn);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
