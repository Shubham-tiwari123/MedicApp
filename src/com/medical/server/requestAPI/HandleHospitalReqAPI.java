package com.medical.server.requestAPI;

import com.medical.server.service.Hospital;
import com.medical.server.utils.VariableClass;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "HandleHospitalReqAPI")
public class HandleHospitalReqAPI extends HttpServlet {

    protected void deactivateHospital(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            JSONObject jsonObject = new JSONObject();
            String hospitalEmail = request.getParameter("hospitalEmail");
            System.out.println("d email:" + hospitalEmail);
            Hospital hospital = new Hospital();
            if (hospital.deactivateHospital(hospitalEmail)) {
                jsonObject.put("statusCode", VariableClass.SUCCESSFUL);
            } else
                jsonObject.put("statusCode", VariableClass.FAILED);
            PrintWriter printWriter = response.getWriter();
            printWriter.println(jsonObject.toString());
        }catch (Exception e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("statusCode", VariableClass.FAILED);
            PrintWriter printWriter = response.getWriter();
            printWriter.println(jsonObject.toString());
        }

    }

    protected void activateHospital(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String hospitalEmail = request.getParameter("hospitalEmail");
            System.out.println("email:" + hospitalEmail);
            JSONObject jsonObject = new JSONObject();
            Hospital hospital = new Hospital();
            if (hospital.activateHospital(hospitalEmail))
                jsonObject.put("statusCode", VariableClass.SUCCESSFUL);
            else
                jsonObject.put("statusCode", VariableClass.FAILED);
            PrintWriter printWriter = response.getWriter();
            printWriter.println(jsonObject.toString());
        }catch (Exception e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("statusCode", VariableClass.FAILED);
            PrintWriter printWriter = response.getWriter();
            printWriter.println(jsonObject.toString());
        }
    }
}
