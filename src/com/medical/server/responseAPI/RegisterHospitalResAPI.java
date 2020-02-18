package com.medical.server.responseAPI;

import org.json.simple.JSONObject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RegisterHospitalResAPI")
public class RegisterHospitalResAPI extends HttpServlet {

    private static int statusCode;

    public void sendResponse(int statusCode, HttpServletResponse response)
            throws IOException {
        RegisterHospitalResAPI.statusCode = statusCode;
        doPost(response);
    }

    private void doPost(HttpServletResponse response) throws IOException {
        JSONObject object = new JSONObject();
        PrintWriter writer = response.getWriter();
        object.put("statusCode", statusCode);
        System.out.println("sending response to client");
        writer.print(object);
    }
}
