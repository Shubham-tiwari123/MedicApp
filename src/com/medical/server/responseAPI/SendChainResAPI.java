package com.medical.server.responseAPI;

import com.medical.server.service.ExtraFunctions;
import com.medical.server.utils.SerializeChain;
import org.json.simple.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "SendChainResAPI")
public class SendChainResAPI extends HttpServlet {

    private static ArrayList<ArrayList<byte[]>> encryptedData;
    private static int statusCode;

    public void sendResponse(ArrayList<ArrayList<byte[]>> encryptedValue,
                             HttpServletResponse response,int statusCode)
            throws IOException {
        SendChainResAPI.encryptedData = encryptedValue;
        SendChainResAPI.statusCode= statusCode;
        doPost(response);
    }

    private void doPost(HttpServletResponse response)
            throws IOException {
        ExtraFunctions extraFunctions = new ExtraFunctions();
        JSONObject object = new JSONObject();
        if(encryptedData!=null) {
            SerializeChain serializeChain = new SerializeChain();
            serializeChain.setEncryptedData(encryptedData);
            String data = extraFunctions.convertJavaToJson(serializeChain);
            System.out.println("data:" + data);

            object.put("statusCode", statusCode);
            object.put("msg", data);
        }
        else{
            object.put("statusCode", statusCode);
            object.put("msg", null);
        }
        PrintWriter writer = response.getWriter();
        writer.print(object);
    }
}
