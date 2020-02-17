package com.medical.server.requestAPI;
import com.medical.server.entity.GenesisBlock;
import com.medical.server.entity.PatientRecord;
import com.medical.server.responseAPI.CreateAccountResAPI;
import com.medical.server.service.ExtraFunctions;
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

@WebServlet(name = "RegisterPatientReqAPI", urlPatterns = {"/register-patient"})

public class RegisterPatientReqAPI extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            CreateAccountResAPI resAPI = new CreateAccountResAPI();
            RegisterPatient registerPatient = new RegisterPatient();
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            JSONParser jsonParser = new JSONParser();
            ExtraFunctions extraFunctions = new ExtraFunctions();

            String clientData;
            while ((clientData = reader.readLine()) != null) {
                buffer.append(clientData);
            }
            System.out.println("data for client:\n" + buffer.toString());
            JSONObject object = (JSONObject) jsonParser.parse(buffer.toString());
            String hospitalUserName = (String) object.get("hospitalUserName");
            if(registerPatient.verifyHospital(hospitalUserName)){
                long generatePatientID = registerPatient.generateNewID();
                String patientData = (String) object.get("patientData");
                PatientRecord patientRecord = extraFunctions.convertJsonToJava(patientData,PatientRecord.class);
                patientRecord.setPatientID(generatePatientID);
                if(registerPatient.storePatient(patientRecord)){
                    GenesisBlock genesisBlock = registerPatient.createGenesisBlock(generatePatientID);
                    if(registerPatient.storeBlock(genesisBlock,generatePatientID)){
                        // return successful
                    } else{
                        //return failed
                    }
                }else{
                    //return failed
                }

            }else {
                //send bad request
            }

        }catch (Exception e){
            System.out.println("exception:"+e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        CreateAccountResAPI resAPI = new CreateAccountResAPI();
        resAPI.sendResponse(0, VariableClass.BAD_REQUEST, response);
    }
}
