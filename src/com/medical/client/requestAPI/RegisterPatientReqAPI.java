package com.medical.client.requestAPI;

import com.medical.client.entity.PatientRecord;
import com.medical.client.responseAPI.RegisterPatientResAPI;
import com.medical.client.service.ExtraFunctions;
import com.medical.client.utils.ConstantClass;
import org.json.simple.JSONObject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "RegisterPatientReqAPI")
public class RegisterPatientReqAPI extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RegisterPatientResAPI resAPI = new RegisterPatientResAPI();
        ExtraFunctions extraFunctions = new ExtraFunctions();
        String hospitalUserName = null;
        try {
            Cookie[] cookies = request.getCookies();
            Cookie cookie;
            if(cookies!=null){
                for (Cookie value : cookies) {
                    cookie = value;
                    if (cookie.getName().equals("loginHospitalStatus")) {
                        String[] val = cookie.getValue().split("&");
                        System.out.println("Val:"+val[0]+" val:"+val[1]);
                        hospitalUserName = val[0];
                        break;
                    }
                }
            }
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String gender = request.getParameter("gender");
            String phoneNumber = request.getParameter("phoneNumber");
            String age = request.getParameter("age");

            System.out.println(hospitalUserName + " " + name + " " + address + " " +
                    gender + " " + phoneNumber + " " + age);

            PatientRecord patientRecord = new PatientRecord();
            patientRecord.setPatientID(0);
            patientRecord.setName(name);
            patientRecord.setAddress(address);
            patientRecord.setGender(gender);
            patientRecord.setPhoneNumber(Long.parseLong(phoneNumber));
            patientRecord.setAge(Integer.parseInt(age));

            String jsonString = extraFunctions.convertJavaToJson(patientRecord);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("hospitalUserName",hospitalUserName);
            jsonObject.put("patientData",jsonString);

            URL url = new URL(ConstantClass.REGISTER_PATIENT);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStream output = conn.getOutputStream();
            OutputStreamWriter outputWriter = new OutputStreamWriter(output, StandardCharsets.UTF_8);
            outputWriter.write(jsonObject.toString());
            outputWriter.flush();
            outputWriter.close();
            if(conn.getResponseCode()== HttpURLConnection.HTTP_OK) {
                resAPI.readResponse(conn,response);
            }else{
                System.out.println("Cannot connect to server...try after sometime....");
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("statusCode",400);
                PrintWriter printWriter = response.getWriter();
                printWriter.println(jsonObject1.toString());
            }

        }catch (Exception e){
            System.out.println("Something went wrong try again......");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("statusCode",400);
            PrintWriter printWriter = response.getWriter();
            printWriter.println(jsonObject.toString());
            e.printStackTrace();
        }
    }
}
