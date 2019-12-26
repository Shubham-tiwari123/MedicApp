package com.medical.server.requestAPI;

import com.medical.server.dao.Database;
import com.medical.server.dao.DatabaseHospital;
import com.medical.server.entity.HospitalDetails;
import com.medical.server.entity.SetKeys;
import com.medical.server.entity.StoreServerKeys;
import com.medical.server.responseAPI.RegisterHospitalResAPI;
import com.medical.server.service.ExtraFunctions;
import com.medical.server.service.RegisterHospital;
import com.medical.server.utils.VariableClass;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
        RegisterHospital registerHospital = new RegisterHospital();
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
                    System.out.println("getting server keys");
                    SetKeys keys = registerHospital.getServerKeys();

                    if (keys.getStatus() == 400) {
                        System.out.println("server key not present");
                        keys = registerHospital.generateKey();
                        if (registerHospital.saveServerKey(keys)) {
                            resAPI.setStatusCode(VariableClass.SUCCESSFUL, response, keys);
                        }else
                            resAPI.setStatusCode(VariableClass.FAILED, response, null);
                    }
                    else if(keys.getStatus()==200){
                        System.out.println("server key already present");
                        resAPI.setStatusCode(VariableClass.SUCCESSFUL, response, keys);
                    }else{
                        resAPI.setStatusCode(VariableClass.FAILED, response, null);
                    }
                } else {
                    resAPI.setStatusCode(VariableClass.FAILED, response, null);
                }
            } else
                resAPI.setStatusCode(VariableClass.BAD_REQUEST, response, null);
        }catch (Exception e){
            resAPI.setStatusCode(VariableClass.BAD_REQUEST, response, null);
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RegisterHospitalResAPI resAPI = new RegisterHospitalResAPI();
        resAPI.setStatusCode(VariableClass.BAD_REQUEST,response,null);
    }
}
