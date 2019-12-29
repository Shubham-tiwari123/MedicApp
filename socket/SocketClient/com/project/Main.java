package com.project;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static ExtraFunctions extraFunctions = new ExtraFunctions();
    Scanner s = new Scanner(System.in);
    private static Socket socket;
    private static DataOutputStream out;

    private static String hashValue(String data) throws Exception {
        return extraFunctions.calculateHash(data);
    }

    private static boolean openSocket(String address, int port) throws Exception {
        socket = new Socket(address, port);
        System.out.println("connected");
        out = new DataOutputStream(socket.getOutputStream());
        return true;
    }

    private static boolean sendData(String data) throws Exception {
        if (socket.isConnected()) {
            out.writeUTF(data);
            return true;
        }
        return false;
    }

    private static String readServerResponse() throws Exception {
        DataInputStream inputStream = new DataInputStream(new BufferedInputStream
                (socket.getInputStream()));
        return inputStream.readUTF();
    }

    private static void closeAllConnection() throws Exception {
        out.close();
        socket.close();
    }

    private static String verifyNetwork() throws Exception {
        Random random = new Random();
        int data = 1000 + random.nextInt(5000);
        String hashData = hashValue(String.valueOf(data));
        JSONObject object = new JSONObject();
        object.put("data", data);
        object.put("hash", hashData);
        return object.toString();


    }

    private static String prepareKeys() throws Exception {
        SetKeys keys = new SetKeys();
        String pubModHash = hashValue(keys.getPublicKeyModules().toString());
        String pubExpoHash = hashValue(keys.getPublicKeyExpo().toString());
        JSONObject object = new JSONObject();
        object.put("modValue", keys.getPublicKeyModules().toString());
        object.put("modHash", pubModHash);
        object.put("expoValue", keys.getPublicKeyExpo().toString());
        object.put("expoHash", pubExpoHash);
        return object.toString();
    }

    private static boolean verifyServerKeys(String serverKeys) throws Exception {
        System.out.println("Verifying keys");
        Object object = new JSONParser().parse(serverKeys);
        JSONObject jsonObject = (JSONObject) object;
        String modHash = (String) jsonObject.get("modHashS");
        String expoHash = (String) jsonObject.get("expoHashS");
        String modValue = (String) jsonObject.get("modValueS");
        String expoValue = (String) jsonObject.get("expoValueS");

        return modHash.equals(extraFunctions.calculateHash(modValue)) && expoHash
                .equals(extraFunctions.calculateHash(expoValue));
    }

    private static boolean storeDb() {
        return true;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to socket client");
        int count = 0;
        String response = "";
        int statusCode = 200;
        try {
            if (openSocket("127.0.0.1", 5000)) {
                String data = verifyNetwork();
                if (sendData(data)) {
                    while (socket.isConnected()) {
                        System.out.println("waiting for response");
                        response = readServerResponse();
                        System.out.println("resp:" + response);
                        switch (count) {
                            case 0:
                                if (response.equals("200")) {
                                    String clientKeys = prepareKeys();
                                    if (sendData(clientKeys)) {
                                        System.out.println("case 1 true");
                                        count++;
                                    } else {
                                        statusCode = 400;
                                        closeAllConnection();
                                    }
                                } else {
                                    System.out.println("Issue in network....try again");
                                    statusCode = 400;
                                    closeAllConnection();
                                }
                                break;

                            case 1:
                                if (response.equals("200")) {
                                    String serverKeys = readServerResponse();
                                    System.out.println("server keys:\n" + serverKeys);
                                    if (verifyServerKeys(serverKeys)) {
                                        System.out.println("keys verified");
                                        //store its and server keys in db
                                        count++;
                                        if(storeDb()) {
                                            sendData("200");
                                            data = readServerResponse();
                                            if(data.equals("200OK"))
                                                closeAllConnection();
                                            else{
                                                //delete the keys
                                                System.out.println("Issue in server storing");
                                                statusCode = 400;
                                                closeAllConnection();
                                            }
                                        }else{
                                            System.out.println("keys not stored...db error");
                                            statusCode = 400;
                                            sendData("400");
                                            closeAllConnection();
                                        }
                                    } else {
                                        System.out.println("keys not verified");
                                        statusCode = 400;
                                        sendData("400");
                                        closeAllConnection();
                                    }
                                }else {
                                    System.out.println("Tampered client keys ....try again");
                                    statusCode = 400;
                                    closeAllConnection();
                                }
                                break;
                        }
                        if (socket.isClosed())
                            break;
                    }
                } else {
                    statusCode = 400;
                    System.out.println("Something went wrong....try again");
                    closeAllConnection();
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
            System.out.println("count:" + count);
            closeAllConnection();
            System.out.println("Exception :-" + e);
        }
    }


}