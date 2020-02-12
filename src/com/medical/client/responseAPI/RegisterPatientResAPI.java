package com.medical.client.responseAPI;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

@WebServlet(name = "RegisterPatientReqAPI")
public class RegisterPatientResAPI extends HttpServlet {

    public void readResponse(HttpURLConnection conn){
        try {
            InputStream inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer output = new StringBuffer();
            String s = "";
            while ((s = bufferedReader.readLine()) != null)
                output.append(s);
            System.out.println("data:\n"+output.toString());
            JSONParser jsonParser = new JSONParser();
            JSONObject object = (JSONObject) jsonParser.parse(output.toString());
            long status = (long) object.get("statusCode");
            if(status==200){
                long patientId = (long) object.get("patientId");
                System.out.println("patient id:"+patientId);
            }else{
                System.out.println("something went wrong please try again....");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
