package com.medical.server.service;

import com.medical.server.entity.GenesisBlock;

public interface CreateAccountInterface {
    void generateNewID();
    boolean checkIdDB(int generatedID);
    GenesisBlock createGenesisBlock(int generatedID);
    boolean storeBlock(GenesisBlock block);
    int returnPatientID();
}
