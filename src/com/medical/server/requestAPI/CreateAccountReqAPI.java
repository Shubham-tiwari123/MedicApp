package com.medical.server.requestAPI;

import com.medical.server.entity.GenesisBlock;
import com.medical.server.responseAPI.CreateAccountResAPI;
import com.medical.server.service.CreateAccount;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CreateAccountReqAPI",urlPatterns = {"/createAccount"})
public class CreateAccountReqAPI extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CreateAccountResAPI resAPI = new CreateAccountResAPI();
        CreateAccount createAccount = new CreateAccount();
        int patientId = createAccount.generateNewID();
        GenesisBlock block = createAccount.createGenesisBlock(patientId);
        if(createAccount.storeBlock(block))
            resAPI.sendResponse(patientId,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
