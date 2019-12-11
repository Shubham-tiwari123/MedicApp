package com.medical.server.responseAPI;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AppendBlockResAPI")
public class AppendBlockResAPI extends HttpServlet {

    static int statusCode;

    public void setStatusCode(int statusCode,HttpServletResponse response)
            throws ServletException, IOException {
        AppendBlockResAPI.statusCode = statusCode;
        doPost(response);
    }
    protected void doPost(HttpServletResponse response)
            throws ServletException, IOException {

    }
}
