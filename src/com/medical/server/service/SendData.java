package com.medical.server.service;

import com.medical.server.dao.Database;
import com.medical.server.entity.SetKeys;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SendData implements SendDataInterface {

    private Database database = new Database();
    private ExtraFunctions extraFunctions = new ExtraFunctions();

    public boolean verifyID(int patientID) {
        return database.verifyPatientIdDB(patientID, "");
    }

    public List<String> getDataDB(int patientID) {
        //List<byte[]> getAllData = database.getSpecificData(patientID);
        List<byte[]> getAllData=null;
        List<String> convertToString = new ArrayList<>();
        return convertToString;
    }

    public SetKeys getKeysOfClient(int hospitalID) {
        SetKeys keys = new SetKeys();
        // get client keys from file using hospitalID
        return keys;
    }

    public List<byte[]> encryptDataAgain(SetKeys keys, List<String> data) {
        List<byte[]> encryptData = new LinkedList<byte[]>();
        for (String val : data) {
            // encrypt data and store it in encrypted data;
            // send the data to the client
        }
        return null;
    }
}
