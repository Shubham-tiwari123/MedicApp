package com.medical.client.requestAPI;

import com.medical.client.entity.HospitalDetails;
import com.medical.client.responseAPI.RegisterHospitalResApi;
import com.medical.client.service.ExtraFunctions;
import org.json.simple.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "RegisterHospitalReqAPI", urlPatterns = {"/registerHospital"})
public class RegisterHospitalReqAPI extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response){

        RegisterHospitalResApi resApi = new RegisterHospitalResApi();
        HospitalDetails hospitalDetails = new HospitalDetails();
        JSONObject object = new JSONObject();
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
            //Preparing JSON
            object.put("details", jsonString);


            URL url = new URL("http://localhost:8082/registerHospital");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStream output = conn.getOutputStream();
            OutputStreamWriter outputWriter = new OutputStreamWriter(output, StandardCharsets.UTF_8);
            outputWriter.write(object.toString());
            outputWriter.flush();
            outputWriter.close();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("Server successfully hit .....");
                PrintWriter writer = response.getWriter();
                writer.print(response);
                resApi.readResponse(conn, hospitalDetails.getUserName(),response,request);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong try again....");
            e.printStackTrace();
        }

    }
}
