package com.medical.server.dao;

import com.medical.server.entity.HospitalDetails;
import com.medical.server.entity.SetKeys;

public interface DatabaseHospitalInterface {
    boolean createDbConn();
    boolean checkCollection(String collectionName);
    boolean verifyUsername(String userName,String collectionName);
    boolean registerHospital(String collectionName, HospitalDetails details);
    boolean storeClientKeys(SetKeys keys, String collectionName, String userName);
    boolean deleteHospital(String userName,String collectionName);
}
