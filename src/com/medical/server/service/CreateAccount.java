package com.medical.server.service;

import com.medical.server.dao.Database;
import com.medical.server.entity.GenesisBlock;
import java.util.Random;

public class CreateAccount implements CreateAccountInterface{

    Database database =new Database();
    ExtraFunctions extraFunctions = new ExtraFunctions();
    static int generatedID = 0;

    public void generateNewID() {
        do {
            Random random = new Random();
            generatedID = 10000+random.nextInt(99999);
        }while (checkIdDB(generatedID));
    }

    public boolean checkIdDB(int generatedID) {
        if(database.createDbConn() && database.checkCollection("collectionName")){
            if(database.verifyPatientIdDB(generatedID))
                return true;
        }
        return false;
    }

    public GenesisBlock createGenesisBlock(int generatedID) {
        GenesisBlock genesisBlock = new GenesisBlock();
        return genesisBlock;
    }

    public boolean storeBlock(GenesisBlock block) {
        String data = extraFunctions.convertJavaToJson(block);
        if(database.createDbConn() && database.checkCollection("collectionName")){
            if(database.saveGenesisBlockDB("",""))
                return true;
        }
        return false;
    }

    public int returnPatientID() {
        return generatedID;
    }
}
