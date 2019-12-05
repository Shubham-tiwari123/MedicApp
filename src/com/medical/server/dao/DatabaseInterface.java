package com.medical.server.dao;

import java.util.ArrayList;
import java.util.List;

public interface DatabaseInterface {
    boolean createDbConn();
    boolean checkCollection(String collectionName);
    boolean verifyPatientIdDB(int patientId,String collectionName);
    boolean saveGenesisBlockDB(String collectionName, ArrayList<byte[]> data);
    boolean updateChain(String data);
    List getAllDataDB();
    List getSpecificData(int patientID);
}
