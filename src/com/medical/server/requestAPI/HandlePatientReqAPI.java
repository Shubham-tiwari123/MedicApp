package com.medical.server.requestAPI;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "HandlePatientReqAPI")
public class HandlePatientReqAPI extends HttpServlet{

    protected void deactivatePatient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void activatePatient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
