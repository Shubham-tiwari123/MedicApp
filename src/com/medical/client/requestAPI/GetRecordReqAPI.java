package com.medical.client.requestAPI;

import com.medical.client.dao.Database;
import com.medical.client.entity.DeserializeChain;
import com.medical.client.entity.SetKeys;
import com.medical.client.service.ExtraFunctions;
import com.medical.client.utils.VariableClass;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@WebServlet(name = "GetRecordReqAPI", urlPatterns = {"/readRecord"})
public class GetRecordReqAPI extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ExtraFunctions extraFunctions = new ExtraFunctions();

        String hospitalUserName = request.getParameter("hospitalUserName");
        String patientId = request.getParameter("patientId");
        String statusCode = request.getParameter("statusCode");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("patientId",Long.parseLong(patientId));
        jsonObject.put("statusCode",Long.parseLong(statusCode));
        jsonObject.put("hospitalUserName",hospitalUserName);

        URL url = new URL("http://localhost:8082/getChain");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        OutputStream output =conn.getOutputStream();
        OutputStreamWriter outputWriter = new OutputStreamWriter(output, StandardCharsets.UTF_8);
        outputWriter.write(jsonObject.toString());
        outputWriter.flush();
        outputWriter.close();
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            try {
                InputStream inputStream = conn.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer resString = new StringBuffer();
                String s = "";
                while ((s = bufferedReader.readLine()) != null)
                    resString.append(s);

                System.out.println("data:\n"+resString.toString());
                JSONParser jsonParser = new JSONParser();

                JSONObject object = (JSONObject) jsonParser.parse(resString.toString());
                long status = (long) object.get("statusCode");
                if (status == 200) {
                    String data = (String) object.get("msg");
                    DeserializeChain reads = extraFunctions.convertJsonToJava(data, DeserializeChain.class);
                    ArrayList<ArrayList<byte[]>> encryptedData = reads.getEncryptedData();

                    Database database = new Database();
                    SetKeys getKeys = database.getClientKeys(VariableClass.STORE_KEYS);
                    if (getKeys != null) {
                        for (ArrayList<byte[]> val : encryptedData) {
                            StringBuilder builder = new StringBuilder();
                            for (byte[] encryptedVal : val) {
                                String subString = extraFunctions.decryptData(encryptedVal,
                                        getKeys.getPrivateKeyModules(), getKeys.getPrivateKeyExpo());
                                builder.append(subString);
                            }
                            System.out.println("data:\n" + builder.toString());
                        }
                    }
                } else {
                    System.out.println("status code:" + statusCode);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            System.out.println("something went wrong");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
