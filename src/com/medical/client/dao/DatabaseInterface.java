package com.medical.client.dao;

import com.medical.client.entity.GetKeys;
import com.medical.client.entity.ServerKeys;
import com.medical.client.entity.SetKeys;

public interface DatabaseInterface {
    boolean createDbConn() throws Exception;

    boolean checkCollection(String collectionName) throws Exception;

    ServerKeys getServerKeys(String collectionName) throws Exception;

    boolean storeKeys(GetKeys keys, String collectionName, SetKeys setKeys) throws Exception;

    SetKeys getClientKeys(String collectionName) throws Exception;
}
