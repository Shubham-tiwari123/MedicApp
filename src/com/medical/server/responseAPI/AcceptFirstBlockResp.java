package com.medical.server.responseAPI;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AcceptFirstBlockResp")
public class AcceptFirstBlockResp extends HttpServlet {
    static int size;
    protected void doPost(HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        writer.println("Array size:"+size);
    }
    public  void responseFunction(int size,HttpServletResponse response)
            throws ServletException, IOException {
        AcceptFirstBlockResp.size = size;
        doPost(response);
    }
}
