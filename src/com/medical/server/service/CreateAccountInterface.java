package com.medical.server.service;

import com.medical.server.entity.GenesisBlockHash;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public interface CreateAccountInterface {
    long generateNewID();
    boolean checkIdDB(long generatedID);
    GenesisBlockHash createGenesisBlock(long generatedID) throws NoSuchAlgorithmException;
    boolean storeBlock(GenesisBlockHash block, long patientID);
    String calBlockHashValue(String data) throws NoSuchAlgorithmException;
    ArrayList<byte[]> encryptBlock(String data);
}
