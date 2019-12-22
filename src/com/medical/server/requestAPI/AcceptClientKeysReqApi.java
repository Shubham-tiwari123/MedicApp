package com.medical.server.requestAPI;

import com.medical.server.dao.DatabaseHospital;
import com.medical.server.entity.DeserializeValues;
import com.medical.server.entity.SetKeys;
import com.medical.server.service.ExtraFunctions;
import com.medical.server.service.RegisterHospital;
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
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "AcceptClientKeysReqApi", urlPatterns = {"/shareKeys"})
public class AcceptClientKeysReqApi extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ExtraFunctions extraFunctions = new ExtraFunctions();
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        RegisterHospital registerHospital = new RegisterHospital();

        String line;
        while((line = reader.readLine())!= null) {
            buffer.append(line);
        }
        String data = buffer.toString();
        System.out.println("hit second time");
        System.out.println(data);
        JSONParser parser = new JSONParser();

        try {
            JSONObject jSONObject = (JSONObject) parser.parse(data);
            long status = (long) jSONObject.get("status");
            String userName = (String) jSONObject.get("userName");
            if(status == 200){
                System.out.println("status:"+status);
                String publicExpoString = (String) jSONObject.get("publicExpoString");
                String publicModString = (String) jSONObject.get("publicModString");
                DeserializeValues deserializeValues = extraFunctions.convertJsonToJava(publicExpoString, DeserializeValues.class);
                DeserializeValues deserializeValues1 = extraFunctions.convertJsonToJava(publicModString, DeserializeValues.class);

                ArrayList<byte[]> encryptedExpoKey = deserializeValues.getEncryptedData();
                ArrayList<byte[]> encryptedModKey = deserializeValues1.getEncryptedData();

                SetKeys keys = registerHospital.getServerKeys();
                publicExpoString = registerHospital.decryptKey(encryptedExpoKey,keys);
                publicModString = registerHospital.decryptKey(encryptedModKey,keys);

                System.out.println("publicexpo:"+publicExpoString);
                System.out.println("publicmod:"+publicModString);

                PrintWriter writer = response.getWriter();
                JSONObject respObj = new JSONObject();

                if(registerHospital.saveClientKey(publicModString,publicExpoString,userName)){
                    respObj.put("status",200);
                    writer.print(respObj.toString());
                }
                else{
                    DatabaseHospital database = new DatabaseHospital();
                    database.deleteHospital(userName,VariableClass.REGISTER_HEALTH_CARE);
                    respObj.put("status",600);
                    writer.print(respObj.toString());
                }

            }else{
                DatabaseHospital database = new DatabaseHospital();
                database.deleteHospital(userName,VariableClass.REGISTER_HEALTH_CARE);
                PrintWriter writer = response.getWriter();
                JSONObject respObj = new JSONObject();
                respObj.put("status",600);
                writer.print(respObj.toString());
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

}
