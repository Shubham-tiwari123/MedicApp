package com.medical.server.service;

import com.medical.server.entity.SetKeys;
import java.util.List;

public interface SendDataInterface {
    boolean verifyID(int patientID);

    List<String> getDataDB(int patientID);

    SetKeys getKeysOfClient(int hospitalID);

    List<byte[]> encryptDataAgain(SetKeys keys, List<String> data);
}
