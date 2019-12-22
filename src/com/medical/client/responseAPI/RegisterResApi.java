package com.medical.client.responseAPI;

import com.medical.client.entity.GetKeys;
import com.medical.client.entity.SerializeKeys;
import com.medical.client.entity.SetKeys;
import com.medical.client.service.ExtraFunctions;
import com.medical.client.service.RegisterUser;
import com.medical.client.utils.VariableClass;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@WebServlet(name = "RegisterResApi")
public class RegisterResApi extends HttpServlet {

    private JSONObject jsonObject = new JSONObject();
    private String userName;
    private long status;

    public void readResponse(HttpServletResponse response, HttpURLConnection conn,String userName){
        this.userName = userName;
        RegisterUser registerUser = new RegisterUser();
        ExtraFunctions extraFunctions = new ExtraFunctions();
        InputStream inputStream = null;
        try {
            inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer output = new StringBuffer();
            String s = "";
            while ((s = bufferedReader.readLine()) != null)
                output.append(s);
            System.out.println("data:\n"+output.toString());
            JSONParser jsonParser = new JSONParser();
            JSONObject object = (JSONObject) jsonParser.parse(output.toString());
            long status = (long) object.get("statusCode");
            this.status = status;

            if(status==200){
                System.out.println("status 200");
                JSONObject valueObj = (JSONObject) object.get("valueObj");
                GetKeys getKeys = extraFunctions.convertJsonToJava(valueObj.toString(),GetKeys.class);

                if(registerUser.verifyServerKey(getKeys)){
                    System.out.println("keys are correct");
                    SetKeys setKeys = registerUser.generateKeys();
                    if(registerUser.storeKeys(getKeys,setKeys)){
                        //encrypt the client private keys before sending to server
                        ArrayList<byte[]> encryptPublicMod = registerUser.encryptKey(getKeys,setKeys.getPublicKeyModules().toString());
                        ArrayList<byte[]> encryptPublicExpo = registerUser.encryptKey(getKeys,setKeys.getPublicKeyExpo().toString());

                        SerializeKeys serializeKeys = new SerializeKeys();
                        SerializeKeys serializeKeys2 = new SerializeKeys();

                        serializeKeys.setEncryptedData(encryptPublicExpo);
                        serializeKeys2.setEncryptedData(encryptPublicMod);

                        String publicExpoString = extraFunctions.convertJavaToJson(serializeKeys);
                        String publicModString = extraFunctions.convertJavaToJson(serializeKeys2);

                        jsonObject.put("publicExpoString",publicExpoString);
                        jsonObject.put("publicModString",publicModString);
                        doPost(response);
                    }
                    else{
                        this.status = VariableClass.FAILED;
                        doPost(response);
                    }
                }else{
                    System.out.println("keys are tampered");
                    this.status = VariableClass.FAILED;
                    doPost(response);
                }
            }
            else{
                System.out.println("status: "+status);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doPost(HttpServletResponse response)
            throws IOException {
        jsonObject.put("status",status);
        jsonObject.put("userName",userName);

        URL url = new URL("http://localhost:8082/shareKeys");
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        OutputStream output =conn.getOutputStream();
        OutputStreamWriter outputWriter = new OutputStreamWriter(output, StandardCharsets.UTF_8);
        outputWriter.write(jsonObject.toString());
        outputWriter.flush();
        outputWriter.close();

        if(conn.getResponseCode()== HttpURLConnection.HTTP_OK) {
            System.out.println("Server successfully hit 2.....");
            InputStream inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer outputBuf = new StringBuffer();
            String s = "";
            while ((s = bufferedReader.readLine()) != null)
                outputBuf.append(s);
            System.out.println("data:\n"+outputBuf.toString());
            JSONParser jsonParser = new JSONParser();
            JSONObject object = null;
            try {
                object = (JSONObject) jsonParser.parse(outputBuf.toString());
                long status = (long) object.get("status");
                if(status==200){
                    System.out.println("Integration with server done");
                }
                else{
                    System.out.println("some error has occurred..... try again");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}