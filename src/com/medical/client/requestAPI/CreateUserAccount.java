package com.medical.client.requestAPI;

import com.medical.client.service.Services;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("encryptedValues",encryptedValues);
        PrintWriter writer = response.getWriter();
        writer.println("patient id:"+jsonObject);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
