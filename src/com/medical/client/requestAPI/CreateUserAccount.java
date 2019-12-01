package com.medical.client.requestAPI;

import com.medical.client.service.Services;
import com.sun.xml.internal.messaging.saaj.util.Base64;
import org.json.simple.JSONObject;
import sun.nio.cs.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

@WebServlet(name = "CreateUserAccount",urlPatterns = {"/CreateUserAccount"})
public class CreateUserAccount extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Random random = new Random();
        Services services = new Services();
        int patientId = 10000+random.nextInt(50000);
        ArrayList<byte[]> encryptedValues = services.createAccount(patientId);
        URL url = new URL("http://localhost:8082/AcceptFirstBlock");
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("encryptedValues",encryptedValues);
        OutputStream output =conn.getOutputStream();
        OutputStreamWriter outputWriter = new OutputStreamWriter(output,"UTF-8");
        outputWriter.write(jsonObject.toString());
        outputWriter.flush();
        outputWriter.close();
        if(conn.getResponseCode()== HttpURLConnection.HTTP_OK)
            System.out.println("Server successfully hit .....");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
