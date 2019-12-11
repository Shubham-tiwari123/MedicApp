package com.medical.server.responseAPI;

import com.sun.deploy.net.HttpResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SendChainResAPI")
public class SendChainResAPI extends HttpServlet {

    static List<byte[]> encryptedValue;
    static int statusCode;
    public void sendResponse(List<byte[]> encryptedValue,HttpServletResponse response)
            throws ServletException, IOException {
        if(!encryptedValue.isEmpty()) {
            SendChainResAPI.encryptedValue = encryptedValue;
            statusCode= 200;
        }
        else{
            statusCode = 404;
        }
        doPost(response);
    }

    protected void doPost(HttpServletResponse response)
            throws ServletException, IOException {

    }
}
