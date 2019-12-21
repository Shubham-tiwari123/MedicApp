package com.medical.client.dao;

import com.medical.client.entity.GetKeys;
import com.medical.client.entity.SetKeys;

import java.util.ArrayList;
import java.util.List;

public interface DatabaseInterface {
    boolean createDbConn();
    boolean checkCollection(String collectionName);
    SetKeys getKeys();
    boolean storeKeys(GetKeys keys,String collectionName,SetKeys setKeys);
}
