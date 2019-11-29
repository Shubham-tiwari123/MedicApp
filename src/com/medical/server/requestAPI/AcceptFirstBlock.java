package com.medical.server.requestAPI;

import com.medical.server.responseAPI.AcceptFirstBlockResp;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

@WebServlet(name = "AcceptFirstBlock",urlPatterns = {"/AcceptFirstBlock"})
public class AcceptFirstBlock extends HttpServlet {
    protected void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        System.out.println(request.getRequestURL());
        while((line = reader.readLine())!= null) {
            System.out.println("w");
            buffer.append(line);
        }
        String data = buffer.toString();
        System.out.println("data:"+data);
        JSONParser parser = new JSONParser();
        try {
            JSONObject jSONObject = (JSONObject) parser.parse(data);
            ArrayList<byte[]> encryptedValues = (ArrayList<byte[]>) jSONObject.get("encryptedValues");
            AcceptFirstBlockResp sendResponse = new AcceptFirstBlockResp();
            sendResponse.responseFunction(encryptedValues.size(),response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
