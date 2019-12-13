package com.medical.server.requestAPI;

import com.medical.server.entity.GenesisBlockHash;
import com.medical.server.responseAPI.CreateAccountResAPI;
import com.medical.server.service.CreateAccount;
import com.medical.server.utils.VariableClass;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet(name = "CreateAccountReqAPI",urlPatterns = {"/createAccount"})
public class CreateAccountReqAPI extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {
        CreateAccountResAPI resAPI = new CreateAccountResAPI();
        CreateAccount createAccount = new CreateAccount();
        long patientId = createAccount.generateNewID();
        GenesisBlockHash block = null;
        try {
            block = createAccount.createGenesisBlock(patientId);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if(createAccount.storeBlock(block,patientId)){
            System.out.println("genesis block stored");
            resAPI.sendResponse(patientId,VariableClass.SUCCESSFUL,response);
        }
        else
            resAPI.sendResponse(0,VariableClass.FAILED,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {
        CreateAccountResAPI resAPI = new CreateAccountResAPI();
        resAPI.sendResponse(0,VariableClass.BAD_REQUEST,response);
    }
}
