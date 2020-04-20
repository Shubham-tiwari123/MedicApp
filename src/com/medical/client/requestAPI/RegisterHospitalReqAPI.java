package com.medical.client.requestAPI;

import com.medical.client.entity.HospitalDetails;
import com.medical.client.responseAPI.RegisterHospitalResApi;
import com.medical.client.service.ExtraFunctions;
import org.json.simple.JSONObject;

import javax.servlet.annotation.WebServlet;
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

@WebServlet(name = "RegisterHospitalReqAPI", urlPatterns = {"/register_hospital"})
public class RegisterHospitalReqAPI extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        RegisterHospitalResApi resApi = new RegisterHospitalResApi();
        HospitalDetails hospitalDetails = new HospitalDetails();
        JSONObject object;
        ExtraFunctions extraFunctions = new ExtraFunctions();

        try {
            String userName = request.getParameter("userName");
            String password = request.getParameter("password");
            String hospitalName = request.getParameter("hospitalName");
            String hospitalAddress = request.getParameter("hospitalAddress");
            String state = request.getParameter("state");
            String city = request.getParameter("city");
            String phoneNumber = request.getParameter("phoneNumber");

            System.out.println(userName + " " + password + " " + hospitalAddress + " " +
                    hospitalName + " " + state + " " + city);

            hospitalDetails.setPassword(password);
            hospitalDetails.setUserName(userName);
            hospitalDetails.setHospitalName(hospitalName);
            hospitalDetails.setHospitalAddress(hospitalAddress);
            hospitalDetails.setState(state);
            hospitalDetails.setCity(city);
            hospitalDetails.setPhoneNumber(phoneNumber);

            String jsonString = extraFunctions.convertJavaToJson(hospitalDetails);
            System.out.println("Hospital details to send:\n" + jsonString);

            URL url = new URL("http://localhost:8082/register-hospital");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStream output = conn.getOutputStream();
            OutputStreamWriter outputWriter = new OutputStreamWriter(output, StandardCharsets.UTF_8);
            outputWriter.write(jsonString);
            outputWriter.flush();
            outputWriter.close();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("Server successfully hit .....");
                resApi.readResponse(conn,response);
            }else{
                object = new JSONObject();
                object.put("statusCode", 400);
                PrintWriter printWriter = response.getWriter();
                printWriter.println(object);
            }
        } catch (Exception e) {
            System.out.println("Exception:"+e);
            object = new JSONObject();
            object.put("statusCode", 400);
            PrintWriter printWriter = response.getWriter();
            printWriter.println(object);
        }
    }
}
