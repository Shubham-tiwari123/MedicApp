package com.medical.server.service;

import com.medical.server.entity.GenesisBlock;
import java.util.ArrayList;

public interface CreateAccountInterface {
    int generateNewID();
    boolean checkIdDB(int generatedID);
    GenesisBlock createGenesisBlock(int generatedID);
    boolean storeBlock(GenesisBlock block);
    int returnPatientID();
    String calBlockHashValue(String data);
    ArrayList<byte[]> encryptBlock(String data);
}
