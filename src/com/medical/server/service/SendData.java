package com.medical.server.service;

import com.medical.server.dao.Database;

import java.lang.reflect.Array;
import java.util.List;

public class SendData implements SendDataInterface {

    private Database database = new Database();
    private ExtraFunctions extraFunctions = new ExtraFunctions();

    public boolean verifyID(int patientID) {
        if(database.createDbConn() && database.checkCollection(""))
            return database.verifyPatientIdDB(patientID);
        return false;
    }

    public List<String> getDataDB(int patientID) {
        List<String> getAllData = database.getSpecificData(patientID);

        return null;
    }

    public String getKeysOfClientDB(int hospitalID) {
        return null;
    }

    public Array encryptData() {
        return null;
    }

    public List returnEncryptedData() {
        return null;
    }
}
