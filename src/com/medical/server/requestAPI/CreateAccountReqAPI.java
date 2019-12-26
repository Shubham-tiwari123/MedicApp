package com.medical.server.requestAPI;

import com.medical.server.entity.GenesisBlockHash;
import com.medical.server.responseAPI.CreateAccountResAPI;
import com.medical.server.service.CreateAccount;
import com.medical.server.utils.VariableClass;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "CreateAccountReqAPI", urlPatterns = {"/createAccount"})

public class CreateAccountReqAPI extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        CreateAccountResAPI resAPI = new CreateAccountResAPI();
        CreateAccount createAccount = new CreateAccount();
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        JSONParser jsonParser = new JSONParser();

        String clientData;
        while ((clientData = reader.readLine()) != null) {
            buffer.append(clientData);
        }
        System.out.println("data for client:\n" + buffer.toString());

        try {
            JSONObject object = (JSONObject) jsonParser.parse(buffer.toString());
            String username = (String) object.get("username");

            /*
            verifying if hospital is registered with server or not....
            if hospital is registered then register the patient else send BAD_REQUEST to client
            */

            if (!createAccount.verifyHospital(username)) {
                long patientId = createAccount.generateNewID();

                GenesisBlockHash block = createAccount.createGenesisBlock(patientId);
                if (createAccount.storeBlock(block, patientId)) {
                    System.out.println("genesis block stored");
                    resAPI.sendResponse(patientId, VariableClass.SUCCESSFUL, response);
                } else
                    resAPI.sendResponse(0, VariableClass.FAILED, response);

            } else
                resAPI.sendResponse(0, VariableClass.BAD_REQUEST, response);
        } catch (Exception e) {
            e.printStackTrace();
            resAPI.sendResponse(0, VariableClass.BAD_REQUEST, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        CreateAccountResAPI resAPI = new CreateAccountResAPI();
        resAPI.sendResponse(0, VariableClass.BAD_REQUEST, response);
    }
}
