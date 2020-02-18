package com.medical.server.service;

import java.util.ArrayList;

public interface AppendDataInterface {

    boolean verifyID(long patientID) throws Exception;

    boolean verifyHospital(String username) throws Exception;

    String decryptData(ArrayList<byte[]> data,String hospitalUserName) throws Exception;

    boolean verifyData(String data) throws Exception;

    String getLastBlockHashDb(long patientID) throws Exception;

    String calCurrentBlockHash(String data) throws Exception;

    String updateBlock(String lastBlockHash, String data) throws Exception;

    ArrayList<byte[]> encryptBlock(String data) throws Exception;

    boolean getServerKeys() throws Exception;

    boolean appendBlockInChain(long patientId, String data) throws Exception;

    public <T> T convertJsonToJava(String jsonString, Class<T> obj) throws Exception;

    public String convertJavaToJson(Object object) throws Exception;
}
