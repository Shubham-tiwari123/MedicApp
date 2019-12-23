package com.medical.server.requestAPI;

import com.medical.server.entity.GenesisBlockHash;
import com.medical.server.responseAPI.CreateAccountResAPI;
import com.medical.server.service.CreateAccount;
import com.medical.server.service.RegisterHospital;
import com.medical.server.utils.VariableClass;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet(name = "CreateAccountReqAPI",urlPatterns = {"/createAccount"})
public class CreateAccountReqAPI extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {

        RegisterHospital registerHospital = new RegisterHospital();
        CreateAccountResAPI resAPI = new CreateAccountResAPI();
        CreateAccount createAccount = new CreateAccount();
        // get hospital username from request and verify if hospital exists or not

        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();

        String line;
        while((line = reader.readLine())!= null) {
            buffer.append(line);
        }
        String data = buffer.toString();
        System.out.println("data for client:\n"+data);
        String username = null;

        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject object= (JSONObject) jsonParser.parse(data);
            username = (String) object.get("username");
            System.out.println("username:"+username);
            if(!registerHospital.checkUserName(username)) {
                System.out.println("hospital exists");
                long patientId = createAccount.generateNewID();
                GenesisBlockHash block = null;
                block = createAccount.createGenesisBlock(patientId);
                if (createAccount.storeBlock(block, patientId)) {
                    System.out.println("genesis block stored");
                    resAPI.sendResponse(patientId, VariableClass.SUCCESSFUL, response);
                } else
                    resAPI.sendResponse(0, VariableClass.FAILED, response);
            }
            else
                resAPI.sendResponse(0, VariableClass.BAD_REQUEST, response);
        } catch (Exception e) {
            e.printStackTrace();
            resAPI.sendResponse(0, VariableClass.BAD_REQUEST, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {
        CreateAccountResAPI resAPI = new CreateAccountResAPI();
        resAPI.sendResponse(0,VariableClass.BAD_REQUEST,response);
    }
}
