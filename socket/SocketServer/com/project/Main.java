package com.project;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static Socket socket = null;
    private static DataOutputStream out = null;
    private static ExtraFunctions extraFunctions = new ExtraFunctions();

    private static boolean connectToClient() throws Exception {
        ServerSocket server = new ServerSocket(5000);
        System.out.println("Server started");
        System.out.println("Waiting for a client ...");
        //server.setSoTimeout(5000);      //server will hold for five sec and if no client gets
                                         // connected then it will close socket
        socket = server.accept();
        out = new DataOutputStream(socket.getOutputStream());
        System.out.println("Client accepted");
        return true;
    }

    private static String readClientData() throws Exception {
        DataInputStream inputStream = new DataInputStream(new BufferedInputStream
                (socket.getInputStream()));
        return inputStream.readUTF();
    }

    private static boolean sendData(String data) throws Exception {

        if (socket.isConnected()) {
            out.writeUTF(data);
            return true;
        }
        return false;
    }

    private static void closeAllConnection() throws Exception {
        out.close();
        socket.close();
    }

    private static boolean verifyNetwork(String data) throws Exception {
        Object object = new JSONParser().parse(data);
        JSONObject jsonObject = (JSONObject) object;
        long num = (long) jsonObject.get("data");
        String hash = (String) jsonObject.get("hash");

        String calHash = extraFunctions.calculateHash(String.valueOf(num));
        return calHash.equals(hash);
    }

    private static String prepareServerKeys() throws Exception {
        System.out.println("pe");
        SetKeys keys = new SetKeys();
        String pubModHash = extraFunctions.calculateHash(keys.getPublicKeyModules().toString());
        String pubExpoHash = extraFunctions.calculateHash(keys.getPublicKeyExpo().toString());
        JSONObject object = new JSONObject();
        object.put("modValueS", keys.getPublicKeyModules().toString());
        object.put("modHashS", pubModHash);
        object.put("expoValueS", keys.getPublicKeyExpo().toString());
        object.put("expoHashS", pubExpoHash);
        return object.toString();
    }

    private static boolean verifyClientKeys(String clientKeys) throws Exception {
        Object object = new JSONParser().parse(clientKeys);
        JSONObject jsonObject = (JSONObject) object;
        String modHash = (String) jsonObject.get("modHash");
        String expoHash = (String) jsonObject.get("expoHash");
        String modValue = (String) jsonObject.get("modValue");
        String expoValue = (String) jsonObject.get("expoValue");

        return modHash.equals(extraFunctions.calculateHash(modValue)) && expoHash
                .equals(extraFunctions.calculateHash(expoValue));
    }

    private static boolean storeDb() {
        return true;
    }

    public static void main(String[] args) {
        System.out.println("Welcome to socket server");
        int count = 0;
        int statusCode = 200;
        String clientData = "";
        try {
            if (connectToClient()) {
                while (socket.isConnected()) {
                    clientData = readClientData();
                    System.out.println("Data from client:" + clientData);
                    switch (count) {
                        case 0:
                            if (verifyNetwork(clientData)) {
                                System.out.println("Network is secured");
                                if (sendData("200"))
                                    count++;
                                else {
                                    statusCode = 400;
                                    closeAllConnection();
                                }
                            } else {
                                statusCode = 400;
                                sendData("400");
                                closeAllConnection();
                                System.out.println("Error in network.....Closing socket");
                            }
                            break;

                        case 1:
                            if (verifyClientKeys(clientData)) {
                                String serverKeys = prepareServerKeys();
                                System.out.println("Client keys are verified");
                                if (sendData("200")) {
                                    if (sendData(serverKeys)) {
                                        count++;
                                    } else {
                                        statusCode = 400;
                                        closeAllConnection();
                                    }
                                } else {
                                    statusCode = 400;
                                    closeAllConnection();
                                }
                            } else {
                                statusCode = 400;
                                sendData("400");
                                closeAllConnection();
                                System.out.println("Client keys are tampered...Closing socket");
                            }
                            break;

                        case 2:
                            if (clientData.equals("200")) {
                                //store the data in db and will send a final 200 to client
                                if(storeDb()) {
                                    System.out.println("keys stored");
                                    sendData("200OK");
                                    closeAllConnection();
                                }
                                else{
                                    System.out.println("Error in db");
                                    sendData("400");
                                    statusCode = 400;
                                    closeAllConnection();
                                }
                            }else{
                                System.out.println("Server keys are tampered");
                                statusCode = 400;
                                closeAllConnection();
                            }
                    }
                    if (socket.isClosed())
                        break;
                }
            } else {
                statusCode = 400;
                System.out.println("Something went wrong....try again");
            }

            if (statusCode == 200) {
                System.out.println("Keys exchanged and stored in db");
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }


}
