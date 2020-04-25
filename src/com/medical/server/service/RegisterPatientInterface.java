package com.medical.server.service;

import com.medical.server.entity.GenesisBlock;
import com.medical.server.entity.PatientRecord;
import java.util.ArrayList;
import java.util.List;

public interface RegisterPatientInterface {

    boolean verifyHospital(String details) throws Exception;

    long generateNewID() throws Exception;

    boolean storePatient(PatientRecord patientRecord,String hospitalEmail) throws Exception;

    boolean checkIdDB(long generatedID) throws Exception;

    GenesisBlock createGenesisBlock(long generatedID) throws Exception;

    boolean storeBlock(GenesisBlock block, long patientID) throws Exception;

    String calBlockHashValue(String data) throws Exception;

    ArrayList<byte[]> encryptBlock(String data) throws Exception;

    List<String> getAllPatients() throws Exception;
}
