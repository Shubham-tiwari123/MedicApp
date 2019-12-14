package com.medical.server.responseAPI;

import org.json.simple.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CreateAccountResAPI")
public class CreateAccountResAPI extends HttpServlet {
    private static long patientId;
    private static int responseCode;

    public void sendResponse(long patientID,int responseCode,HttpServletResponse response)
            throws  IOException {
        CreateAccountResAPI.patientId=patientID;
        CreateAccountResAPI.responseCode=responseCode;
        doPost(response);
    }

    private void doPost(HttpServletResponse response)
            throws  IOException {
        PrintWriter writer = response.getWriter();
        JSONObject json = new JSONObject();
        json.put("statusCode",responseCode);
        json.put("patientId",patientId);
        writer.println(json.toString());
    }
}
