package com.medical.client.responseAPI;

import com.medical.client.entity.GetKeys;
import com.medical.client.entity.SerializeKeys;
import com.medical.client.entity.SetKeys;
import com.medical.client.service.ExtraFunctions;
import com.medical.client.service.RegisterUser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@WebServlet(name = "RegisterHospitalResApi")
public class RegisterHospitalResApi extends HttpServlet {

    private JSONObject jsonObject = new JSONObject();
    private String userName;
    private long status;

    public void readResponse(HttpURLConnection conn,String userName){
        try {
            this.userName = userName;
            RegisterUser registerUser = new RegisterUser();
            ExtraFunctions extraFunctions = new ExtraFunctions();
            StringBuffer clientData = new StringBuffer();
            InputStream inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String readData = "";

            while ((readData = bufferedReader.readLine()) != null)
                clientData.append(readData);

            System.out.println("Getting response from server:\n" + clientData.toString());
            JSONParser jsonParser = new JSONParser();
            JSONObject object = (JSONObject) jsonParser.parse(clientData.toString());
            this.status = (long) object.get("statusCode");

            if (this.status == 200) {
                System.out.println("status 200");
                JSONObject valueObj = (JSONObject) object.get("valueObj");
                GetKeys getKeys = extraFunctions.convertJsonToJava(valueObj.toString(), GetKeys.class);

                if (registerUser.verifyServerKey(getKeys)) {
                    System.out.println("keys are correct");
                    SetKeys setKeys = registerUser.generateKeys();
                    if(setKeys!=null) {
                        if (registerUser.storeKeys(getKeys, setKeys)) {
                            //encrypt the client private keys before sending to server
                            this.status=700;
                            ArrayList<byte[]> encryptPublicMod = registerUser.encryptKey(getKeys,
                                    setKeys.getPublicKeyModules().toString());
                            ArrayList<byte[]> encryptPublicExpo = registerUser.encryptKey(getKeys,
                                    setKeys.getPublicKeyExpo().toString());

                            SerializeKeys serializeKeys = new SerializeKeys();
                            SerializeKeys serializeKeys2 = new SerializeKeys();

                            serializeKeys.setEncryptedData(encryptPublicExpo);
                            serializeKeys2.setEncryptedData(encryptPublicMod);

                            String publicExpoString = extraFunctions.convertJavaToJson(serializeKeys);
                            String publicModString = extraFunctions.convertJavaToJson(serializeKeys2);

                            jsonObject.put("publicExpoString", publicExpoString);
                            jsonObject.put("publicModString", publicModString);
                            this.status=200;
                            doPost();
                        } else {
                            System.out.println("error occurred while saving keys....try again ");
                        }
                    }
                    else{
                        System.out.println("error occurred while getting keys from db....try again ");
                    }
                } else {
                    System.out.println("keys are tampered....try again");
                }
            } else {
                System.out.println("error from server....try again:");
            }
        }catch (Exception e){
            System.out.println("status: " + status);
            if(this.status==700){
                //delete the keys stored
            }
        }
    }

    private void doPost(){
        jsonObject.put("status",status);
        jsonObject.put("userName",userName);

        try {
            URL url = new URL("http://localhost:8082/shareKeys");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStream output = conn.getOutputStream();
            OutputStreamWriter outputWriter = new OutputStreamWriter(output, StandardCharsets.UTF_8);
            outputWriter.write(jsonObject.toString());
            outputWriter.flush();
            outputWriter.close();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("Server successfully hit 2.....");
                InputStream inputStream = conn.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer clientData = new StringBuffer();
                String readData = "";

                while ((readData = bufferedReader.readLine()) != null)
                    clientData.append(readData);

                System.out.println("data:\n" + clientData.toString());
                JSONParser jsonParser = new JSONParser();
                JSONObject object = null;

                try {
                    object = (JSONObject) jsonParser.parse(clientData.toString());
                    long status = (long) object.get("status");

                    if (status == 200) {
                        System.out.println("Integration with server done");
                    } else {
                        System.out.println("some error has occurred..... try again");
                        // delete the keys stored in db
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                    System.out.println("some error has occurred..... try again");
                    // delete the keys stored in db
                }
            } else {
                System.out.println("some error has occurred..... try again");
                // delete the keys stored in db
            }
        }catch (Exception e){
            //delete keys stored in db
            e.printStackTrace();
        }
    }
}