package com.medical.server.utils;

import com.medical.server.service.ExtraFunctions;
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

@WebServlet(name = "ReadDataResAPI",urlPatterns = {"/readData"})
public class ReadDataResAPI extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        try {
            ExtraFunctions extraFunctions = new ExtraFunctions();
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while((line = reader.readLine())!= null) {
                buffer.append(line);
            }
            String data = buffer.toString();
            System.out.println("data:\n"+data);

            JSONParser jsonParser = new JSONParser();

            JSONObject object = (JSONObject) jsonParser.parse(data);
            long statusCode = (long) object.get("statusCode");
            if(statusCode==200){
                data = (String) object.get("msg");
                DeserializeChain reads = extraFunctions.convertJsonToJava(data,DeserializeChain.class);
                ArrayList<ArrayList<byte[]>> encryptedData = reads.getEncryptedData();

                for(ArrayList<byte[]> val:encryptedData){
                    StringBuilder builder = new StringBuilder();
                    for (byte[] encryptedVal:val){
                        String subString = extraFunctions.decryptData(encryptedVal,
                                VariableClass.clientPriMod,VariableClass.clientPriExpo);
                        builder.append(subString);
                    }
                    System.out.println("data:\n"+builder.toString());
                }
            }
            else{
                System.out.println("status code:"+statusCode);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
