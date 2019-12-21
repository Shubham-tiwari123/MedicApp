package com.medical.client.responseAPI;

import com.medical.client.entity.GetKeys;
import com.medical.client.entity.SerializeKeys;
import com.medical.client.entity.SetKeys;
import com.medical.client.service.ExtraFunctions;
import com.medical.client.service.RegisterUser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@WebServlet(name = "RegisterResApi")
public class RegisterResApi extends HttpServlet {

    private JSONObject jsonObject = new JSONObject();

    public void readResponse(HttpServletResponse response, HttpURLConnection conn,String userName){
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
            JSONObject object = new JSONObject();
            object = (JSONObject) jsonParser.parse(output.toString());

            GetKeys getKeys = extraFunctions.convertJsonToJava(output.toString(),GetKeys.class);

            if(registerUser.verifyServerKey(getKeys)){
                System.out.println("keys are correct");
                SetKeys setKeys = registerUser.generateKeys();
                if(registerUser.storeKeys(getKeys,setKeys)){
                    //encrypt the client private keys before sending to server
                    ArrayList<byte[]> encryptKey = registerUser.encryptKey(getKeys,setKeys);
                    SerializeKeys serializeKeys = new SerializeKeys();
                    serializeKeys.setEncryptedData(encryptKey);
                    String json = extraFunctions.convertJavaToJson(encryptKey);
                    jsonObject.put("keys",json);
                    jsonObject.put("username",userName);
                    doPost(response);
                }
            }else{
                System.out.println("keys are tampered");
            }

        } catch (IOException | ServletException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void doPost(HttpServletResponse response)
            throws ServletException, IOException {
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
        }
    }
}
