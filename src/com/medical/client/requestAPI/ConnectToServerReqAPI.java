package com.medical.client.requestAPI;

import com.medical.client.dao.Database;
import com.medical.client.entity.ClientKeys;
import com.medical.client.entity.ServerKeys;
import com.medical.client.service.ConnectToServer;
import com.medical.client.utils.VariableClass;
import org.json.simple.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet(name = "ConnectDeviceReqAPI",urlPatterns = {"/connect-server"})
public class ConnectToServerReqAPI extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        ConnectToServer connect = new ConnectToServer();
        int count = 0;
        String resString = "";
        int statusCode = 200;
        try {
            System.out.println("client hit");
            URL url = new URL("http://localhost:8080/connect-server");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("hit server");
                if (connect.openSocket("127.0.0.1", 4000)) {
                    System.out.println("socket");
                    if (connect.sendData(connect.verifyNetwork())) {
                        while (ConnectToServer.socket.isConnected()) {
                            System.out.println("waiting for response");
                            resString = connect.readServerData();
                            switch (count) {
                                case 0:
                                    if (resString.equals("200")) {
                                        System.out.println("Network is secured");
                                        String clientKeys = connect.prepareKeysToSend();
                                        if (connect.sendData(clientKeys)) {
                                            System.out.println("Client keys send...");
                                            count++;
                                        } else {
                                            statusCode = 400;
                                            connect.closeConnections();
                                        }
                                    } else {
                                        System.out.println("Issue in network....try again");
                                        statusCode = 400;
                                        connect.closeConnections();
                                    }
                                    break;

                                case 1:
                                    if (resString.equals("200")) {
                                        String serverKeys = connect.readServerData();
                                        System.out.println("server keys:\n" + serverKeys);
                                        if (connect.verifyServerKeys(serverKeys)) {
                                            System.out.println("keys verified");
                                            //store its and server keys in db
                                            count++;
                                            if(connect.storeServerKeys(serverKeys)) {
                                                connect.sendData("200");
                                                String data = connect.readServerData();
                                                if(data.equals("200OK"))
                                                    connect.closeConnections();
                                                else{
                                                    if(connect.deleteServerKeys())
                                                        System.out.println("server keys deleted");
                                                    System.out.println("Issue in server storing");
                                                    statusCode = 400;
                                                    connect.closeConnections();
                                                }
                                            }else{
                                                System.out.println("keys not stored...db error");
                                                statusCode = 400;
                                                connect.sendData("400");
                                                connect.closeConnections();
                                            }
                                        } else {
                                            System.out.println("keys not verified");
                                            statusCode = 400;
                                            connect.sendData("400");
                                            connect.closeConnections();
                                        }
                                    }else {
                                        System.out.println("Tampered client keys ....try again");
                                        statusCode = 400;
                                        connect.closeConnections();
                                    }
                                    break;
                            }
                            if (ConnectToServer.socket.isClosed()) {
                                System.out.println("socket closed "+ConnectToServer.socket.isConnected());
                                break;
                            }
                        }
                    } else {
                        statusCode = 400;
                        System.out.println("Something went wrong....try again");
                        connect.closeConnections();
                    }
                } else {
                    statusCode = 400;
                    System.out.println("Something went wrong....try again");
                }

                if (statusCode == 200) {
                    System.out.println("Keys exchanged and stored in db");
                }
            }
            else{
                System.out.println("socket is closed try again");
            }
        }catch (Exception e){
            //System.out.println(e);
            try {
                connect.closeConnections();
                System.out.println("catch throwing....closing connection");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        try {
            doPost(request,response);
            Database database = new Database();
            ClientKeys clientKeys = database.getClientKeys2(VariableClass.STORE_KEYS);
            ServerKeys serverKeys = database.getServerKeys(VariableClass.STORE_KEYS);

            String server = serverKeys.getPublicKeyModules().toString().substring(0,80);
            String client = clientKeys.getPublicKeyModules().toString().substring(0,80);
            JSONObject object = new JSONObject();
            object.put("PC",client);
            object.put("Server",server);
            PrintWriter writer = response.getWriter();
            writer.println(object.toJSONString());

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
//0735393422  Gauteng