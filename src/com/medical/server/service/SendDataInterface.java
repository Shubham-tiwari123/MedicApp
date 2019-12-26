package com.medical.server.service;

import com.medical.server.entity.SetKeys;

import java.util.ArrayList;
import java.util.List;

public interface SendDataInterface {

    boolean verifyID(long patientID) throws Exception;

    List<String> getDataDB(long patientID) throws Exception;

    SetKeys getClientKeys(String hospitalID) throws Exception;

    ArrayList<ArrayList<byte[]>> encryptDataAgain(SetKeys keys, List<String> data) throws Exception;

}
