package com.medical.server.requestAPI;

import com.medical.server.entity.HospitalDetails;
import com.medical.server.responseAPI.RegisterHospitalResAPI;
import com.medical.server.service.ExtraFunctions;
import com.medical.server.service.Hospital;
import com.medical.server.utils.VariableClass;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "RegisterHospitalReqAPI")
public class RegisterHospitalReqAPI extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        RegisterHospitalResAPI resAPI = new RegisterHospitalResAPI();
        ExtraFunctions extraFunctions = new ExtraFunctions();
        Hospital registerHospital = new Hospital();
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();

        int statusCode;
        String line;
        while((line = reader.readLine())!= null) {
            buffer.append(line);
        }
        String data = buffer.toString();
        System.out.println("Data from client:\n"+data);
        String subString = null;
        try {
            HospitalDetails details = extraFunctions.convertJsonToJava(data, HospitalDetails.class);

            if (registerHospital.checkUserName(details.getUserName())) {
                System.out.println("hospital does not exists....registering");
                if (registerHospital.saveHospitalDetails(details)) {
                    System.out.println("hospital saved");
                    statusCode = VariableClass.SUCCESSFUL;
                } else {
                    statusCode = VariableClass.FAILED;
                }
            } else
                statusCode = VariableClass.BAD_REQUEST;
            resAPI.sendResponse(statusCode,response);

        }catch (Exception e){
            resAPI.sendResponse(VariableClass.BAD_REQUEST, response);
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Hospital hospital = new Hospital();
        System.out.println("fghjkl;");
        try {
            List<String> result = hospital.getAllHospitals();
            System.out.println("result:"+result);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("statusCode",200);
            jsonObject.put("result",result);
            PrintWriter printWriter = response.getWriter();
            printWriter.println(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
