package com.medical.server.requestAPI;

import com.medical.server.entity.HospitalDetails;
import com.medical.server.responseAPI.LoginHospitalResAPI;
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

@WebServlet(name = "LoginHospitalReqAPI")
public class LoginHospitalReqAPI extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        try {
            LoginHospitalResAPI resAPI = new LoginHospitalResAPI();
            Hospital loginHospital = new Hospital();
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String data = buffer.toString();
            System.out.println("Data from client:\n" + data);

            JSONParser jsonParser = new JSONParser();
            JSONObject jSONObject = (JSONObject) jsonParser.parse(data);
            String email = (String) jSONObject.get("email");
            String pass = (String) jSONObject.get("pass");
            if(loginHospital.loginHospital(email,pass)){
                HospitalDetails hospitalDetails = loginHospital.getHospitalDetails(email);
                if (hospitalDetails==null)
                    resAPI.sendResponse(VariableClass.FAILED,response,null);
                else
                    resAPI.sendResponse(VariableClass.SUCCESSFUL,response,hospitalDetails);
            }else
                resAPI.sendResponse(VariableClass.FAILED,response,null);

        }catch (Exception e){
            System.out.println("Exception:"+e);
            LoginHospitalResAPI resAPI = new LoginHospitalResAPI();
            try {
                resAPI.sendResponse(VariableClass.BAD_REQUEST,response,null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}
