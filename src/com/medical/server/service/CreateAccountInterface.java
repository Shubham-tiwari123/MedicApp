package com.medical.server.service;

import com.medical.server.entity.GenesisBlockHash;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public interface CreateAccountInterface {

    boolean verifyHospital(String details) throws Exception;

    long generateNewID() throws Exception;

    boolean checkIdDB(long generatedID) throws Exception;

    GenesisBlockHash createGenesisBlock(long generatedID) throws NoSuchAlgorithmException;

    boolean storeBlock(GenesisBlockHash block, long patientID) throws Exception;

    String calBlockHashValue(String data) throws NoSuchAlgorithmException;

    ArrayList<byte[]> encryptBlock(String data) throws Exception;
}
