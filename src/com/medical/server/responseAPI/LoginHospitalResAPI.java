package com.medical.server.responseAPI;

import com.medical.server.entity.HospitalDetails;
import com.medical.server.service.ExtraFunctions;
import com.medical.server.utils.VariableClass;
import org.json.simple.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginHospitalResAPI")
public class LoginHospitalResAPI extends HttpServlet {

    private static int statusCode;
    private HospitalDetails details;

    public void sendResponse(int statusCode, HttpServletResponse response, HospitalDetails details)
            throws Exception {
        LoginHospitalResAPI.statusCode = statusCode;
        this.details = details;
        doPost(response);
    }

    private void doPost(HttpServletResponse response) throws Exception {
        JSONObject object = new JSONObject();
        ExtraFunctions extraFunctions = new ExtraFunctions();
        PrintWriter writer = response.getWriter();
        object.put("statusCode", statusCode);
        if (statusCode == VariableClass.SUCCESSFUL)
            object.put("details",extraFunctions.convertJavaToJson(details));
        System.out.println("sending response to client");
        writer.print(object);
    }
}
