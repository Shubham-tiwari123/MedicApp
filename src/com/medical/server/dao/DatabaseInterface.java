package com.medical.server.dao;

import java.util.ArrayList;
import java.util.List;

public interface DatabaseInterface {
    boolean createDbConn();
    boolean checkCollection(String collectionName);
    boolean verifyPatientIdDB(long patientId,String collectionName);
    boolean saveGenesisBlockDB(String collectionName, ArrayList<byte[]> data,long patientID);
    boolean updateChain(ArrayList<byte[]> data, long patientId,String collectionName);
    List getAllDataDB();
    ArrayList<ArrayList<byte[]>>  getSpecificData(long patientID,String collectionName);
}
