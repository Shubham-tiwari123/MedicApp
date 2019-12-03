package com.medical.server.dao;

import java.util.List;

public class Database implements DatabaseInterface {

    public boolean createDbConn() {
        return false;
    }

    public boolean checkCollection(String collectionName) {
        return false;
    }

    public boolean verifyPatientIdDB(int patientId) {
        return false;
    }

    public boolean saveGenesisBlockDB(String collectionName, String data) {
        return false;
    }

    public boolean updateChain() {
        return false;
    }

    public List getAllDataDB() {
        return null;
    }

    public List getSpecificData(int patientID) {
        return null;
    }
}
