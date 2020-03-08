package com.medical.client.requestAPI;

import com.medical.client.entity.MedicBlock;
import com.medical.client.entity.SerializeRecord;
import com.medical.client.responseAPI.SendRecordResAPI;
import com.medical.client.service.ExtraFunctions;
import com.medical.client.service.SendRecord;
import org.json.simple.JSONObject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@WebServlet(name = "SendRecordReqAPI",urlPatterns = {"/sendRecord"})
public class SendRecordReqAPI extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response){

        MedicBlock block = new MedicBlock();
        ExtraFunctions extraFunctions = new ExtraFunctions();
        SendRecord sendRecord = new SendRecord();
        SendRecordResAPI resAPI = new SendRecordResAPI();

        String patientID = request.getParameter("patientID");
        String doctorName = request.getParameter("doctorName");
        String hospitalName = request.getParameter("hospitalName");
        String specialityType = request.getParameter("specialityType");
        String prescription = request.getParameter("prescription");

        String hospitalUserName = null;
        Cookie[] cookies = request.getCookies();
        Cookie cookie;
        if(cookies!=null){
            for (Cookie value : cookies) {
                cookie = value;
                if (cookie.getName().equals("loginStatus")) {
                    hospitalUserName = cookie.getValue();
                    break;
                }
            }
        }

        block.setPatientId(Long.parseLong(patientID));
        block.setDate(Date.valueOf(LocalDate.now()));
        block.setTime(Time.valueOf(LocalTime.now()));
        block.setDoctorName(doctorName);
        block.setHospitalName(hospitalName);
        block.setSpecialistType(specialityType);
        block.setPrescription(prescription);
        block.setCurrentBlockHash("0");
        block.setPreviousBlockHash("0");

        try {
            //String hash = sendRecord.calBlockHash(extraFunctions.convertJavaToJson(block));
            String encryptString = sendRecord.prepareBlock(block);
            System.out.println("data before sending:\n"+encryptString);

            // encryption done using client private key
            ArrayList<byte[]> encryptedData = sendRecord.encryptBlock(encryptString);
            SerializeRecord serializeRecord = new SerializeRecord();
            serializeRecord.setEncryptedData(encryptedData);
            String data = extraFunctions.convertJavaToJson(serializeRecord);
            System.out.println("serializeRecord data:" + data);
            JSONObject object = new JSONObject();
            object.put("encrypted", data);
            object.put("hospitalUserName",hospitalUserName);
            object.put("patientId",Long.parseLong(patientID));
            System.out.println("data:::"+object);

            URL url = new URL("http://localhost:8082/append-record");
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStream output =conn.getOutputStream();
            OutputStreamWriter outputWriter = new OutputStreamWriter(output, StandardCharsets.UTF_8);
            outputWriter.write(object.toString());
            outputWriter.flush();
            outputWriter.close();
            if(conn.getResponseCode()== HttpURLConnection.HTTP_OK) {
                System.out.println("server hit");
                //read response and if successful then display block else display try again
                resAPI.readResponse(conn,response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
