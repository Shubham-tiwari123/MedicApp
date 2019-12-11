package com.medical.server.requestAPI;

import com.medical.server.entity.SetKeys;
import com.medical.server.responseAPI.SendChainResAPI;
import com.medical.server.service.SendData;
import org.json.simple.parser.JSONParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name = "SendChainReqAPI", urlPatterns = {"/getChain"})
public class SendChainReqAPI extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // read patientID from request
        SendChainResAPI resAPI = new SendChainResAPI();
        SendData sendData = new SendData();
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();

        String line;
        while((line = reader.readLine())!= null) {
            buffer.append(line);
        }
        String data = buffer.toString();
        JSONParser parser = new JSONParser();
        List<byte[]> encryptedData = new LinkedList<byte[]>();
        try {
            /* Parse the data to get patientID and encrypted data
            JSONObject jSONObject = (JSONObject) parser.parse(data);
            ArrayList<String> encryptedValues = (ArrayList<String>) jSONObject.get("encryptedValues");
            */
            int patientId = 12345;
            int hospitalId = 34567;
            if(sendData.verifyID(patientId)){
                List<String> list = sendData.getDataDB(patientId);
                SetKeys keys = sendData.getKeysOfClient(hospitalId);
                encryptedData= sendData.encryptDataAgain(keys,list);
                resAPI.sendResponse(encryptedData,response);
            }
            else
                resAPI.sendResponse(encryptedData,response);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
