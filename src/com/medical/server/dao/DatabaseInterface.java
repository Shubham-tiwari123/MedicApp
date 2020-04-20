package com.medical.server.dao;

import com.medical.server.entity.ClientKeys;
import com.medical.server.entity.HospitalDetails;
import com.medical.server.entity.PatientRecord;
import com.medical.server.entity.ServerKeys;
import java.util.ArrayList;
import java.util.List;

public interface DatabaseInterface {

    boolean createDbConn() throws Exception;

    boolean checkCollection(String collectionName) throws Exception;

    boolean verifyPatientIdDB(long patientId, String collectionName) throws Exception;

    boolean saveGenesisBlockDB(String collectionName, ArrayList<byte[]> data, long patientID) throws Exception;

    boolean updateChain(ArrayList<byte[]> data, long patientId, String collectionName) throws Exception;

    ServerKeys getServerKey(String collectionName) throws Exception;

    ClientKeys getClientKeys(String hospital, String collectionName) throws Exception;

    boolean storeServerKeys(ServerKeys keys, String collectionName) throws Exception;

    boolean storeClientKeys(ClientKeys keys, String collectionName, String signature) throws Exception;

    ArrayList<ArrayList<byte[]>> getSpecificData(long patientID, String collectionName) throws Exception;

    boolean verifyHospital(String userName, String collectionName) throws Exception;

    boolean registerHospital(String collectionName, HospitalDetails details) throws Exception;

    boolean registerPatient(String collectionName, PatientRecord record) throws Exception;

    boolean checkLoginCredentials(String userName,String password,String collectionName) throws Exception;

    List<String> getAllHospitals(String collectionName) throws Exception;

    List<String> getAllPatients(String collectionName) throws Exception;

    HospitalDetails getHospitalDetails(String email,String collectionName) throws Exception;
}
