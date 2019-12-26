package com.medical.server.responseAPI;

import com.medical.server.entity.SetKeys;
import com.medical.server.entity.StoreServerKeys;
import com.medical.server.service.ExtraFunctions;
import com.medical.server.utils.VariableClass;
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
        doPost(response);
    }

    private void doPost(HttpServletResponse response)
            throws IOException {
        ExtraFunctions extraFunctions = new ExtraFunctions();
        JSONObject object = new JSONObject();
        JSONObject valueObj = new JSONObject();
        PrintWriter writer = response.getWriter();

        if(statusCode==200) {
            try {
                String modulesHashValue = extraFunctions.calculateHash(keys.getPublicKeyModules().toString());
                String expoHashValue = extraFunctions.calculateHash(keys.getPublicKeyExpo().toString());
                System.out.println("Preparing keys to send with hash");
                valueObj.put("modulesHashValue", modulesHashValue);
                valueObj.put("expoHashValue", expoHashValue);
                valueObj.put("modulesValue", keys.getPublicKeyModules().toString());
                valueObj.put("expoValue", keys.getPublicKeyExpo().toString());

                object.put("statusCode", statusCode);
                object.put("valueObj", valueObj);
                System.out.println("sending server public key to client");
                writer.print(object);

            }catch (Exception e){
                e.printStackTrace();
                System.out.println("sending error");
                object.put("statusCode", VariableClass.FAILED);
                writer.print(object);
            }
        }else{
            System.out.println("sending error");
            object.put("statusCode", statusCode);
        }

    }
}
