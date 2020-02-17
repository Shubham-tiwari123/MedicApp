package com.medical.server.requestAPI;

import com.medical.server.entity.SetKeys;
import com.medical.server.responseAPI.SendChainResAPI;
import com.medical.server.service.Hospital;
import com.medical.server.service.SendData;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SendChainReqAPI", urlPatterns = {"/getChain"})
public class SendChainReqAPI extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // read patientID from request

        Hospital registerHospital = new Hospital();
        SendChainResAPI resAPI = new SendChainResAPI();
        SendData sendData = new SendData();
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();

        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();
        JSONParser parser = new JSONParser();

        ArrayList<ArrayList<byte[]>> encryptedData = null;

        try {
            JSONObject jSONObject = (JSONObject) parser.parse(data);
            long status = (Long) jSONObject.get("statusCode");
            if (status == 200) {
                System.out.println("stattus ok");
                String hospitalUserName = (String) jSONObject.get("hospitalUserName");
                if (!registerHospital.checkUserName(hospitalUserName)) {
                    System.out.println("hospital exists");
                    long patientId = (long) jSONObject.get("patientId");
                    if (sendData.verifyID(patientId)) {
                        System.out.println("user verified");
                        List<String> list = sendData.getDataDB(patientId);
                        for(String val:list)
                            System.out.println("val:\n"+val);

                        System.out.println("list size api:" + list.size());
                        SetKeys keys = sendData.getClientKeys(hospitalUserName);
                        encryptedData = sendData.encryptDataAgain(keys, list);
                        for (ArrayList<byte[]> val : encryptedData) {
                            System.out.println(val);
                        }
                        resAPI.sendResponse(encryptedData, response, VariableClass.SUCCESSFUL);
                    } else {
                        resAPI.sendResponse(null, response, VariableClass.BAD_REQUEST);
                    }
                }else {
                    resAPI.sendResponse(null, response, VariableClass.BAD_REQUEST);
                }
            }else {
                resAPI.sendResponse(null, response, VariableClass.BAD_REQUEST);
            }
        } catch (Exception e) {
            System.out.println(e);
            resAPI.sendResponse(null, response, VariableClass.FAILED);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SendChainResAPI resAPI = new SendChainResAPI();
        resAPI.sendResponse(null, response, VariableClass.BAD_REQUEST);
    }
}
