package com.medical.client.requestAPI;

import com.medical.client.entity.HospitalDetails;
import com.medical.client.responseAPI.RegisterResApi;
import com.medical.client.service.ExtraFunctions;
import com.medical.client.service.RegisterUser;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "RegisterReqApi", urlPatterns = {"/register"})
public class RegisterReqApi extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RegisterUser registerUser = new RegisterUser();
        HospitalDetails hospitalDetails = new HospitalDetails();
        JSONObject object = new JSONObject();
        ExtraFunctions extraFunctions = new ExtraFunctions();

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String hospitalName  = request.getParameter("hospitalName");
        String hospitalAddress = request.getParameter("hospitalAddress");
        String state = request.getParameter("state");
        String city = request.getParameter("city");

        System.out.println(userName+" "+password+" "+hospitalAddress+" "+hospitalName+" "+state+" "+city);
        hospitalDetails.setPassword(password);
        hospitalDetails.setUserName(userName);
        hospitalDetails.setHospitalName(hospitalName);
        hospitalDetails.setHospitalAddress(hospitalAddress);
        hospitalDetails.setState(state);
        hospitalDetails.setCity(city);

        String jsonString = extraFunctions.convertJavaToJson(hospitalDetails);
        System.out.println("json:"+jsonString);
        //Preparing JSON
        object.put("details",jsonString);

        URL url = new URL("http://localhost:8082/registerHospital");
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        OutputStream output =conn.getOutputStream();
        OutputStreamWriter outputWriter = new OutputStreamWriter(output, StandardCharsets.UTF_8);
        outputWriter.write(object.toString());
        outputWriter.flush();
        outputWriter.close();

        if(conn.getResponseCode()== HttpURLConnection.HTTP_OK) {
            System.out.println("Server successfully hit .....");
            RegisterResApi resApi = new RegisterResApi();
            resApi.readResponse(response,conn,hospitalDetails.getUserName());
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //doPost(request,response);
    }
}
