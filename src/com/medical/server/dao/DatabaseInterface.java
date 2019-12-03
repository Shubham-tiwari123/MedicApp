package com.medical.server.dao;

import java.util.List;

public interface DatabaseInterface {
    boolean createDbConn();
    boolean checkCollection(String collectionName);
    boolean verifyPatientIdDB(int patientId);
    boolean saveGenesisBlockDB(String collectionName, String data);
    boolean updateChain();
    List getAllDataDB();
    List getSpecificData(int patientID);
}
