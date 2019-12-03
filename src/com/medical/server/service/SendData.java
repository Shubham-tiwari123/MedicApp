package com.medical.server.service;

import java.lang.reflect.Array;
import java.util.List;

public class SendData implements SendDataInterface {
    public boolean verifyID(int patientID) {
        return false;
    }

    public List getDataDB(int patientID) {
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
