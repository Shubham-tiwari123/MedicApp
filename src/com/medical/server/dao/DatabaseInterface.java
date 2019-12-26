package com.medical.server.dao;

import com.medical.server.entity.SetKeys;
import com.medical.server.entity.StoreServerKeys;

import java.util.ArrayList;

public interface DatabaseInterface {
    boolean createDbConn() throws Exception;
    boolean checkCollection(String collectionName) throws Exception;
    boolean verifyPatientIdDB(long patientId,String collectionName) throws Exception;
    boolean saveGenesisBlockDB(String collectionName, ArrayList<byte[]> data,long patientID) throws Exception;
    boolean updateChain(ArrayList<byte[]> data, long patientId,String collectionName) throws Exception;
    SetKeys getServerKey(String collectionName) throws Exception;
    SetKeys getClientKeys(String hospital,String collectionName) throws Exception;
    boolean getServerPrivateKeys(String collectionName) throws Exception;
    ArrayList<ArrayList<byte[]>>  getSpecificData(long patientID,String collectionName) throws Exception;
}
