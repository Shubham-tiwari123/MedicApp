package com.medical.server.service;


import com.medical.server.entity.ServerKeys;

public interface ConnectToHospitalInterface {

    boolean connectUsingSocket(int port) throws Exception;

    String readClientData() throws Exception;

    boolean sendData(String data) throws Exception;

    void closeConnections() throws Exception;

    boolean verifyNetwork(String data) throws Exception;

    String getServerKeys() throws Exception;

    boolean verifyClientKeys(String clientKeys) throws Exception;

    boolean storeClientKeys(String keys) throws Exception;

    String calculateHash(String data) throws Exception;

    ServerKeys generateKeys() throws Exception;

    //generate a digital signature for each pc and send it to them
    // this signature will be used in each transaction to verify the pc
    // store th signature in database along with pc name
}
