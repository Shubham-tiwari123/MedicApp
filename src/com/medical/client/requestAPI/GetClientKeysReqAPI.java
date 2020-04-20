package com.medical.client.requestAPI;

import com.medical.client.dao.Database;
import com.medical.client.entity.ClientKeys;
import com.medical.client.entity.ServerKeys;
import com.medical.client.utils.ConstantClass;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GetClientKeysReqAPI",urlPatterns = {"/get_keys"})
public class GetClientKeysReqAPI extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Database database = new Database();
            JSONObject object = new JSONObject();
            ClientKeys clientKeys = database.getClientKeys(ConstantClass.STORE_KEYS);
            ServerKeys serverKeys = database.getServerKeys(ConstantClass.STORE_KEYS);
            if(clientKeys!=null || serverKeys!=null){
                String server = serverKeys.getPublicKeyModules().toString().substring(0,65);
                String client = clientKeys.getPublicKeyModules().toString().substring(0,65);
                object.put("PC",client);
                object.put("Server",server);
                object.put("statusCode",200);
            }else{
                object.put("statusCode",301);
            }
            PrintWriter writer = response.getWriter();
            writer.println(object.toJSONString());

        }catch (Exception e){
            JSONObject object = new JSONObject();
            System.out.println("e:"+e);
            object.put("statusCode",301);
            PrintWriter writer = response.getWriter();
            writer.println(object.toJSONString());
        }
    }
}
