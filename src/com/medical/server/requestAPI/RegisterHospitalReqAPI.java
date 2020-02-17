package com.medical.server.requestAPI;

import com.medical.server.entity.HospitalDetails;
import com.medical.server.responseAPI.RegisterHospitalResAPI;
import com.medical.server.service.ExtraFunctions;
import com.medical.server.service.Hospital;
import com.medical.server.utils.VariableClass;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "RegisterHospitalReqAPI",urlPatterns = {"/registerHospital"})
public class RegisterHospitalReqAPI extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        RegisterHospitalResAPI resAPI = new RegisterHospitalResAPI();
        ExtraFunctions extraFunctions = new ExtraFunctions();
        Hospital registerHospital = new Hospital();
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();

        String line;
        while((line = reader.readLine())!= null) {
            buffer.append(line);
        }
        String data = buffer.toString();
        System.out.println("Data from client:\n"+data);

        String subString = null;
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject object = (JSONObject) jsonParser.parse(data);
            subString = (String) object.get("details");

            HospitalDetails details = extraFunctions.convertJsonToJava(subString, HospitalDetails.class);

            if (registerHospital.checkUserName(details.getUserName())) {
                System.out.println("hospital does not exists....registering");
                if (registerHospital.saveHospitalDetails(details)) {
                    System.out.println("hospital saved");
                    resAPI.sendResponse(VariableClass.SUCCESSFUL,response);
                } else {
                    resAPI.sendResponse(VariableClass.FAILED, response);
                }
            } else
                resAPI.sendResponse(VariableClass.BAD_REQUEST, response);
        }catch (Exception e){
            resAPI.sendResponse(VariableClass.BAD_REQUEST, response);
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RegisterHospitalResAPI resAPI = new RegisterHospitalResAPI();
        resAPI.sendResponse(VariableClass.BAD_REQUEST,response);
    }
}
