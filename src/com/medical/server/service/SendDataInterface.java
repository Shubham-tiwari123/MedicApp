package com.medical.server.service;

import java.lang.reflect.Array;
import java.util.List;

public interface SendDataInterface {
    boolean verifyID(int patientID);
    List getDataDB(int patientID);
    String getKeysOfClientDB(int hospitalID);
    Array encryptData();
    List returnEncryptedData();
}
