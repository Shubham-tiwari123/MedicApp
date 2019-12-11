package com.medical.server.responseAPI;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CreateAccountResAPI")
public class CreateAccountResAPI extends HttpServlet {
    static int patientId;

    public void sendResponse(int patientID,HttpServletResponse response)
            throws ServletException, IOException {
        patientId=patientID;
        doPost(response);
    }

    protected void doPost(HttpServletResponse response)
            throws ServletException, IOException {

    }
}
