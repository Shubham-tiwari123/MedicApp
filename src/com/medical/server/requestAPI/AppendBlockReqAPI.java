package com.medical.server.requestAPI;

import com.medical.server.entity.SetKeys;
import com.medical.server.responseAPI.AppendBlockResAPI;
import com.medical.server.service.AppendData;
import com.medical.server.service.ExtraFunctions;
import org.json.simple.parser.JSONParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "AppendBlockReqAPI", urlPatterns = {"/appendRecord"})
public class AppendBlockReqAPI extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Read the data
        AppendBlockResAPI resAPI = new AppendBlockResAPI();
        AppendData appendData = new AppendData();
        ExtraFunctions extraFunctions = new ExtraFunctions();
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();

        String line;
        while((line = reader.readLine())!= null) {
            buffer.append(line);
        }
        String data = buffer.toString();
        JSONParser parser = new JSONParser();
        try {
            /* Parse the data to get patientID and encrypted data
            JSONObject jSONObject = (JSONObject) parser.parse(data);
            ArrayList<String> encryptedValues = (ArrayList<String>) jSONObject.get("encryptedValues");
            */
            int patientId=12345;
            ArrayList<byte[]> list = new ArrayList<byte[]>();
            SetKeys keys = extraFunctions.getServerKeyFromFile();
            if(appendData.verifyID(patientId)){
                String decryptData = appendData.decryptData(list,keys);
                if(appendData.verifyData(decryptData)){
                    String lastValueHash = appendData.getLastBlockHashDb(patientId);
                    String newBlockValue = appendData.updateBlock(lastValueHash,decryptData);
                    if(appendData.appendBlockInChain(patientId,newBlockValue,keys))
                        resAPI.setStatusCode(200,response);
                    else
                        resAPI.setStatusCode(404,response);
                }
                else
                    resAPI.setStatusCode(404,response);
            }
            else
                resAPI.setStatusCode(404,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
