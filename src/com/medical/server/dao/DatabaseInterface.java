package com.medical.server.dao;

import com.medical.server.entity.SetKeys;
import com.medical.server.entity.StoreServerKeys;

import java.util.ArrayList;

public interface DatabaseInterface {
    boolean createDbConn();
    boolean checkCollection(String collectionName);
    boolean verifyPatientIdDB(long patientId,String collectionName);
    boolean saveGenesisBlockDB(String collectionName, ArrayList<byte[]> data,long patientID);
    boolean updateChain(ArrayList<byte[]> data, long patientId,String collectionName);
    SetKeys getServerKey(String collectionName);
    SetKeys getClientKeys(String hospital,String collectionName);
    boolean getServerPrivateKeys(String collectionName);
    ArrayList<ArrayList<byte[]>>  getSpecificData(long patientID,String collectionName);
}
