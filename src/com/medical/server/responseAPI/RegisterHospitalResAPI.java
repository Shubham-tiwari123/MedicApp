package com.medical.server.responseAPI;

import com.medical.server.entity.SetKeys;
import com.medical.server.service.ExtraFunctions;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

@WebServlet(name = "RegisterHospitalResAPI")
public class RegisterHospitalResAPI extends HttpServlet {

    private static int statusCode;
    private SetKeys keys;

    public void setStatusCode(int statusCode, HttpServletResponse response, SetKeys keys)
            throws IOException {
        RegisterHospitalResAPI.statusCode = statusCode;
        this.keys = keys;
        try {
            doPost(response);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void doPost(HttpServletResponse response)
            throws IOException, NoSuchAlgorithmException {
        ExtraFunctions extraFunctions = new ExtraFunctions();
        JSONObject object = new JSONObject();
        if(statusCode==200) {
            String modulesHashValue = extraFunctions.calculateHash(keys.getPublicKeyModules().toString());
            String expoHashValue = extraFunctions.calculateHash(keys.getPublicKeyExpo().toString());
            object.put("statusCode", statusCode);
            object.put("modulesHashValue", modulesHashValue);
            object.put("expoHashValue", expoHashValue);
            object.put("modulesValue", keys.getPublicKeyModules().toString());
            object.put("expoValue", keys.getPublicKeyExpo().toString());

        }else{
            object.put("statusCode", statusCode);
        }

        PrintWriter writer = response.getWriter();
        writer.print(object);
    }
}
