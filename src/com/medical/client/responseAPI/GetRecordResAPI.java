package com.medical.client.responseAPI;

import com.medical.client.entity.ClientKeys;
import com.medical.client.entity.DeserializeChain;
import com.medical.client.entity.MedicBlock;
import com.medical.client.service.ExtraFunctions;
import com.medical.client.service.GetClientRecord;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.*;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GetRecordResAPI")
public class GetRecordResAPI extends HttpServlet {

    public void readResponse(HttpURLConnection conn) {
        ExtraFunctions extraFunctions = new ExtraFunctions();
        GetClientRecord clientRecord = new GetClientRecord();
        try {
            InputStream inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer resString = new StringBuffer();
            String s = "";
            while ((s = bufferedReader.readLine()) != null)
                resString.append(s);

            System.out.println("data:\n" + resString.toString());
            JSONParser jsonParser = new JSONParser();

            JSONObject object = (JSONObject) jsonParser.parse(resString.toString());
            long status = (long) object.get("statusCode");
            if (status == 200) {
                String data = (String) object.get("msg");
                DeserializeChain chain = extraFunctions.convertJsonToJava(data, DeserializeChain.class);
                ArrayList<ArrayList<byte[]>> encryptedData = chain.getEncryptedData();
                ClientKeys clientKeys = clientRecord.getKeysFromDatabase();
                // decrypting data using client private key
                if (clientKeys != null) {
                    List<MedicBlock> medicBlocks = clientRecord.medicBlocks(encryptedData,clientKeys);
                    if(clientRecord.verifyChain(medicBlocks)){
                        System.out.println("Chain is verified");
                    }
                    else{
                        System.out.println("Chain not verified....try again");
                    }
                }
            } else {
                System.out.println("Error in the response");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
