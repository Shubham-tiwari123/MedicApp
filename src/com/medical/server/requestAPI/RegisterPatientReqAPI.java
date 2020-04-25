package com.medical.server.requestAPI;

import com.medical.server.entity.GenesisBlock;
import com.medical.server.entity.PatientRecord;
import com.medical.server.responseAPI.RegisterPatientResAPI;
import com.medical.server.service.ExtraFunctions;
import com.medical.server.service.Hospital;
import com.medical.server.service.RegisterPatient;
import com.medical.server.utils.VariableClass;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "RegisterPatientReqAPI")

public class RegisterPatientReqAPI extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            RegisterPatientResAPI resAPI = new RegisterPatientResAPI();
            RegisterPatient registerPatient = new RegisterPatient();
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            JSONParser jsonParser = new JSONParser();
            ExtraFunctions extraFunctions = new ExtraFunctions();

            long generatePatientID = 0;
            int statusCode=0;
            String clientData;
            while ((clientData = reader.readLine()) != null) {
                buffer.append(clientData);
            }
            System.out.println("data for client:\n" + buffer.toString());
            JSONObject object = (JSONObject) jsonParser.parse(buffer.toString());
            String hospitalUserName = (String) object.get("hospitalUserName");

            if(!registerPatient.verifyHospital(hospitalUserName)){
                generatePatientID = registerPatient.generateNewID();
                String patientData = (String) object.get("patientData");
                PatientRecord patientRecord = extraFunctions.convertJsonToJava(patientData,PatientRecord.class);
                patientRecord.setPatientID(generatePatientID);

                if(registerPatient.storePatient(patientRecord,hospitalUserName)){

                    GenesisBlock genesisBlock = registerPatient.createGenesisBlock(generatePatientID);
                    if(registerPatient.storeBlock(genesisBlock,generatePatientID)){
                        statusCode = VariableClass.SUCCESSFUL;
                    } else{
                        statusCode = VariableClass.FAILED;
                    }
                }else{
                    statusCode = VariableClass.INTERNAL_SERVER_ERROR;
                }
            }else {
                statusCode = VariableClass.BAD_REQUEST;
            }
            resAPI.sendResponse(generatePatientID,statusCode,response);
        }catch (Exception e){
            System.out.println("exception:"+e);
            RegisterPatientResAPI resAPI = new RegisterPatientResAPI();
            resAPI.sendResponse(0,VariableClass.INTERNAL_SERVER_ERROR,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        RegisterPatient registerPatient = new RegisterPatient();
        System.out.println("fghjkl;");
        try {
            List<String> result = registerPatient.getAllPatients();
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
