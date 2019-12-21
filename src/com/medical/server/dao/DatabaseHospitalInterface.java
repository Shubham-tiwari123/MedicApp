package com.medical.server.dao;

import com.medical.server.entity.HospitalDetails;
import com.medical.server.entity.SetKeys;

public interface DatabaseHospitalInterface {
    boolean createDbConn();
    boolean checkCollection(String collectionName);
    boolean verifyUsername(String userName,String collectionName);
    boolean registerHospital(String collectionName, HospitalDetails details);
    boolean storeClientKeys(String pubMod,String pubExpo,String userName,String collectionName);
    boolean storeServerKey(SetKeys keys, String collectionName);
    SetKeys getServerKeys(String collectionName);
    boolean deleteHospital(String userName,String collectionName);
}
