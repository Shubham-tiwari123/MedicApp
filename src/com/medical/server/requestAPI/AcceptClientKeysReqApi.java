package com.medical.server.requestAPI;

import com.medical.server.entity.DeserializeKeys;
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
        JSONParser parser = new JSONParser();
        ArrayList<byte[]> encryptedData = null;

        try {
            JSONObject jSONObject = (JSONObject) parser.parse(data);
            String subString = (String) jSONObject.get("keys");
            String userName = (String) jSONObject.get("username");
            DeserializeKeys deserializeKey = extraFunctions.convertJsonToJava(subString,DeserializeKeys.class);

            //decrypt keys
            // Get server private keys
            SetKeys keys = registerHospital.getServerKeys();
            String clientPubExpo = extraFunctions.decryptData(deserializeKey.getEncryptedData().get(0),
                    keys.getPrivateKeyModules(),keys.getPrivateKeyExpo());

            String clientPubMod = extraFunctions.decryptData(deserializeKey.getEncryptedData().get(1),
                    keys.getPrivateKeyModules(),keys.getPrivateKeyExpo());


        }catch (Exception e){
            System.out.println(e);
        }
    }

}
