package com.medical.client;

import com.medical.block.BlockStructure;
import com.medical.block.BlockStructure2;
import com.medical.block.GenesisBlock;
import com.medical.block.GenesisBlock2;
import com.medical.database.ConnectDB;
import com.medical.util.JSONUtil;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ClientFunctions implements ClientMethods{
    private BlockFunction blockFunction = new BlockFunction();
    private ConnectDB connectDB = new ConnectDB();
    private final String COLLECTION_NAME="storeData";

    @Override
    public boolean createAccount() {
        System.out.print("Patient id");
        int patientId = SCANNER.nextInt();
        if (establishConnection()) {
            if (checkIfAccountExist(patientId, COLLECTION_NAME)) {
                System.out.println("account already created");
                return true;
            } else {
                GenesisBlock genesisBlock = blockFunction.createFirstBlock(patientId);
                StringBuilder jsonString = new StringBuilder(JSONUtil.convertJavaToJson(genesisBlock));
                jsonString.deleteCharAt(jsonString.length() - 1);
                jsonString.append(",\"currentBlockHash\":\"" + genesisBlock.getCurrentBlockHash() + "\"}");
                connectDB.insertData(genesisBlock.getId(), COLLECTION_NAME, jsonString.toString(),
                        genesisBlock.getCreationDate().toString());
                System.out.println("new Account created");
                return true;
            }
        }
        System.out.println("can not connect");
        return false;
    }

    private boolean checkIfAccountExist(int patientId, String collectionName){
        if(connectDB.checkId(patientId,collectionName))
            return true;
        return false;
    }

    @Override
    public boolean appendData() {
        return false;
    }

    @Override
    public void getAllData() {
        System.out.print("Patient id");
        int patientId = SCANNER.nextInt();
        List<String> value = connectDB.getDataOfID(patientId, COLLECTION_NAME);
            for (int i = 0; i < value.size(); i++)
                System.out.println("v:-" + value.get(i));

    }

    private boolean establishConnection() {
        if(connectDB.connectToMongo()){
            if(!connectDB.checkCollection(COLLECTION_NAME)){
                connectDB.createCollection(COLLECTION_NAME);
                return true;
            }
            else
                return true;
        }
        return false;
    }

}
