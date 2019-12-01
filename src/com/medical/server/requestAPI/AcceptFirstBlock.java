package com.medical.server.requestAPI;

import com.medical.server.responseAPI.AcceptFirstBlockResp;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "AcceptFirstBlock",urlPatterns = {"/AcceptFirstBlock"})
public class AcceptFirstBlock extends HttpServlet {
    protected void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println(request.getRequestURL());
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while((line = reader.readLine())!= null) {
            System.out.println("l:-"+line);
            buffer.append(line);
        }
        String data = buffer.toString();
        JSONParser parser = new JSONParser();
        try {
            JSONObject jSONObject = (JSONObject) parser.parse(data);
            ArrayList<String> encryptedValues = (ArrayList<String>) jSONObject.get("encryptedValues");
            AcceptFirstBlockResp sendResponse = new AcceptFirstBlockResp();
            sendResponse.responseFunction(encryptedValues.size(),response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
