package com.medical.server.dao;

import com.medical.server.entity.ClientKeys;
import com.medical.server.entity.ServerKeys;
import com.medical.server.entity.SetKeys;
import java.util.ArrayList;

public interface DatabaseInterface {
    boolean createDbConn() throws Exception;

    boolean checkCollection(String collectionName) throws Exception;

    boolean verifyPatientIdDB(long patientId, String collectionName) throws Exception;

    boolean saveGenesisBlockDB(String collectionName, ArrayList<byte[]> data, long patientID) throws Exception;

    boolean updateChain(ArrayList<byte[]> data, long patientId, String collectionName) throws Exception;

    ServerKeys getServerKey(String collectionName) throws Exception;

    ClientKeys getClientKeys(String hospital, String collectionName) throws Exception;

    boolean getServerPrivateKeys(String collectionName) throws Exception;

    boolean storeServerKeys(ServerKeys keys, String collectionName) throws Exception;

    boolean storeClientKeys(ClientKeys keys, String collectionName, String signature) throws Exception;

    ArrayList<ArrayList<byte[]>> getSpecificData(long patientID, String collectionName) throws Exception;
}
