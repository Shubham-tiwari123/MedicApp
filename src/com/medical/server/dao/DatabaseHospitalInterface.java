package com.medical.server.dao;

import com.medical.server.entity.HospitalDetails;
import com.medical.server.entity.SetKeys;

public interface DatabaseHospitalInterface {

    boolean createDbConn() throws Exception;

    boolean checkCollection(String collectionName) throws Exception;

    boolean verifyUsername(String userName, String collectionName) throws Exception;

    boolean registerHospital(String collectionName, HospitalDetails details) throws Exception;

    boolean storeClientKeys(String pubMod, String pubExpo, String userName, String collectionName)
            throws Exception;

    boolean storeServerKey(SetKeys keys, String collectionName) throws Exception;

    SetKeys getServerKeys(String collectionName) throws Exception;

    boolean deleteHospital(String userName, String collectionName) throws Exception;
}
