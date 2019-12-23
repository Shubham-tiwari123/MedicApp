package com.medical.server.requestAPI;

import com.medical.server.entity.*;
import com.medical.server.responseAPI.AppendBlockResAPI;
import com.medical.server.service.AppendData;
import com.medical.server.service.CreateAccount;
import com.medical.server.service.ExtraFunctions;
import com.medical.server.service.RegisterHospital;
import com.medical.server.utils.VariableClass;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;

@WebServlet(name = "AppendBlockReqAPI", urlPatterns = {"/appendRecord"})
public class AppendBlockReqAPI extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        AppendBlockResAPI resAPI = new AppendBlockResAPI();
        AppendData appendData = new AppendData();
        SetKeys keys = new SetKeys();
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        RegisterHospital registerHospital = new RegisterHospital();
        ExtraFunctions extraFunctions = new ExtraFunctions();

        int statusCode=0;
        String line;
        while((line = reader.readLine())!= null) {
            buffer.append(line);
        }
        String data = buffer.toString();
        JSONParser parser = new JSONParser();
        try {
            JSONObject jSONObject = (JSONObject) parser.parse(data);
            long status = (Long) jSONObject.get("statusCode");

            if(status==200){
                System.out.println("stattus ok");
                String hospitalUserName = (String) jSONObject.get("hospitalUserName");
                if(!registerHospital.checkUserName(hospitalUserName)){
                    System.out.println("hospital exists");
                    long patientId = (long) jSONObject.get("patientId");
                    System.out.println("patient id:"+patientId);
                    if(appendData.verifyID(patientId)){
                        System.out.println("patient exist");
                        String jsonString = (String) jSONObject.get("encrypted");

                        DeserializeValues deserializeValues = extraFunctions.convertJsonToJava(jsonString,
                                DeserializeValues.class);

                        if(appendData.getServerKeys()) {
                            String decryptString = appendData.decryptData(deserializeValues.getEncryptedData());
                            System.out.println("dec:" + decryptString);
                            //verify data
                            if(appendData.verifyData(decryptString)) {
                                System.out.println("equal");
                                String lastBlockHash = appendData.getLastBlockHashDb(patientId);
                                String updatedBlock = appendData.updateBlock(lastBlockHash,decryptString);
                                System.out.println("updated block:\n"+updatedBlock);

                                if(appendData.appendBlockInChain(patientId,updatedBlock)) {
                                    statusCode = VariableClass.SUCCESSFUL;
                                    System.out.println("data saved");
                                }
                                else {
                                    statusCode=VariableClass.FAILED;
                                    System.out.println("not saved");
                                }
                            }
                            else{
                                statusCode= VariableClass.BAD_REQUEST;
                                System.out.println("not equal");
                            }
                        }
                    }
                    else {
                        statusCode = VariableClass.BAD_REQUEST;
                        System.out.println("no record");
                    }
                }else{
                    statusCode = VariableClass.BAD_REQUEST;
                    System.out.println("no record");
                }
            }else{
                statusCode = VariableClass.BAD_REQUEST;
                System.out.println("no record");
            }
            resAPI.setStatusCode(statusCode,response);
        } catch (Exception e) {
            e.printStackTrace();
            resAPI.setStatusCode(VariableClass.BAD_REQUEST,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        AppendBlockResAPI resAPI = new AppendBlockResAPI();
        resAPI.setStatusCode(VariableClass.BAD_REQUEST,response);
    }
}
