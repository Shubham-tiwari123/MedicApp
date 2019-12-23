package com.medical.server.service;

import com.medical.server.entity.SetKeys;

import java.util.ArrayList;
import java.util.List;

public interface SendDataInterface {

    boolean verifyID(long patientID);

    List<String> getDataDB(long patientID);

    SetKeys getClientKeys(String hospitalID);

    ArrayList<ArrayList<byte[]>> encryptDataAgain(SetKeys keys, List<String> data);

}
