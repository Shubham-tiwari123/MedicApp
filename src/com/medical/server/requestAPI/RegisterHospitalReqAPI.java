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
        System.out.println("data:\n"+data);
        String subString = null;
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject object= (JSONObject) jsonParser.parse(data);
            subString = (String) object.get("details");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        HospitalDetails details = extraFunctions.convertJsonToJava(subString,HospitalDetails.class);

        boolean flag=false;
        int statusCode;
        if(registerHospital.checkUserName(details)){
            if(registerHospital.saveHospitalDetails(details)) {
                flag = true;
                statusCode = VariableClass.SUCCESSFUL;
            }
            else{
                statusCode = VariableClass.FAILED;
            }
        }
        else{
            statusCode = VariableClass.BAD_REQUEST;
        }

        if(flag){
            try {
                SetKeys keys = registerHospital.getServerKeys();
                if(keys==null) {
                    System.out.println("server key not present");
                    keys = registerHospital.generateKey();
                    if (registerHospital.saveServerKey(keys))
                        resAPI.setStatusCode(VariableClass.SUCCESSFUL, response, keys);
                }else{
                    System.out.println("server key already present");
                    resAPI.setStatusCode(VariableClass.FAILED, response, null);
                }
            }catch (Exception e){
                DatabaseHospital database = new DatabaseHospital();
                database.deleteHospital(details.getUserName(),VariableClass.REGISTER_HEALTH_CARE);
                resAPI.setStatusCode(VariableClass.FAILED, response, null);
                e.printStackTrace();
            }
        }else
            resAPI.setStatusCode(statusCode,response,null);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RegisterHospitalResAPI resAPI = new RegisterHospitalResAPI();
        resAPI.setStatusCode(VariableClass.BAD_REQUEST,response,null);
    }
}
