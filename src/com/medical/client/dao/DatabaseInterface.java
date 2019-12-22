package com.medical.client.dao;

import com.medical.client.entity.GetKeys;
import com.medical.client.entity.ServerKeys;
import com.medical.client.entity.SetKeys;

import java.util.ArrayList;
import java.util.List;

public interface DatabaseInterface {
    boolean createDbConn();
    boolean checkCollection(String collectionName);
    ServerKeys getServerKeys(String collectionName);
    boolean storeKeys(GetKeys keys,String collectionName,SetKeys setKeys);
}
