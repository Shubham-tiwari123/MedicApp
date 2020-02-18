package com.medical.server.service;

import com.medical.server.entity.ClientKeys;
import java.util.ArrayList;
import java.util.List;

public interface SendDataInterface {

    boolean verifyHospital(String userName) throws Exception;

    boolean verifyID(long patientID) throws Exception;

    List<String> getDataDB(long patientID) throws Exception;

    ClientKeys getClientKeys(String hospitalID) throws Exception;

    ArrayList<ArrayList<byte[]>> encryptDataAgain(ClientKeys keys, List<String> data) throws Exception;

}
