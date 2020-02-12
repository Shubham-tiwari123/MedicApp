package com.medical.client.dao;

import com.medical.client.entity.ClientKeys;
import com.medical.client.entity.ServerKeys;

public interface DatabaseInterface {
    boolean createDbConn() throws Exception;

    boolean checkCollection(String collectionName) throws Exception;

    ServerKeys getServerKeys(String collectionName) throws Exception;

    /*boolean storeKeys(GetKeys keys, String collectionName, SetKeys setKeys) throws Exception;*/

    /*SetKeys getClientKeys(String collectionName) throws Exception;*/

    boolean storeServerKeys(ServerKeys keys, String collectionName) throws Exception;

    boolean storeClientKeys(ClientKeys keys, String collectionName) throws Exception;

    ClientKeys getClientKeys2(String collectionName) throws Exception;

    boolean deleteServerKeys(String collectionName) throws Exception;

    boolean checkKeysExists(String collectionName) throws Exception;

}
