package com.medical.client;

import com.medical.block.BlockStructure;
import com.medical.block.BlockStructure2;
import com.medical.block.GenesisBlock;
import com.medical.block.GenesisBlock2;
import com.medical.database.ConnectDB;
import com.medical.util.JSONUtil;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
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
                /*connectDB.insertData(genesisBlock.getId(), COLLECTION_NAME, jsonString.toString(),
                        genesisBlock.getCreationDate().toString());*/
                ArrayList<byte[]> list= encryptBlock(jsonString.toString());
                for(byte[] l:list){
                    System.out.println("enc:"+l);
                }
                String decryptedValue = decryptBlock(list);
                System.out.println("dec:"+decryptedValue);
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
        System.out.print("Patient id");
        int patientId = SCANNER.nextInt();
        if (establishConnection() && checkIfAccountExist(patientId,COLLECTION_NAME)) {
            List jsonList=connectDB.getLastDataOfID(patientId,COLLECTION_NAME);
            int listSize = (int) jsonList.get(1);
            if(listSize==1){
                GenesisBlock2 genesisBlock = JSONUtil.convertJsonToJava(jsonList.get(0).toString(),
                        GenesisBlock2.class);
                BlockStructure block = blockFunction.createNewBlock(genesisBlock.getCurrentBlockHash(),patientId);
                StringBuilder jsonString = new StringBuilder(JSONUtil.convertJavaToJson(block));
                jsonString.deleteCharAt(jsonString.length() - 1);
                jsonString.append(",\"currentBlockHash\":\"" + block.getCurrentBlockHash() + "\"}");
                connectDB.appendRecord(patientId,COLLECTION_NAME,jsonString.toString());
                System.out.println("Appended");
                return true;
            }
            else{
                BlockStructure2 blockStructure2 = JSONUtil.convertJsonToJava(jsonList.get(0).toString(),
                        BlockStructure2.class);
                BlockStructure block = blockFunction.createNewBlock(blockStructure2.getCurrentBlockHash(),patientId);
                StringBuilder jsonString = new StringBuilder(JSONUtil.convertJavaToJson(block));
                jsonString.deleteCharAt(jsonString.length() - 1);
                jsonString.append(",\"currentBlockHash\":\"" + block.getCurrentBlockHash() + "\"}");
                System.out.println("elsewwe:-"+blockStructure2.getCurrentBlockHash());
                connectDB.appendRecord(patientId,COLLECTION_NAME,jsonString.toString());
                System.out.println("Appended");
                return true;
            }
        }
        System.out.println("Cannot append");
        return false;
    }

    @Override
    public void getAllData() {
        System.out.print("Patient id");
        int patientId = SCANNER.nextInt();
        if (establishConnection() && checkIfAccountExist(patientId,COLLECTION_NAME)) {
            List<String> value = connectDB.getDataOfID(patientId, COLLECTION_NAME);
            if(verifyBlocks(value)) {
                for (int i = 0; i < value.size(); i++)
                    System.out.println("v:-" + value.get(i));
            }
            else{
                System.out.println("Blocks tampered");
            }
        }
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

    private boolean verifyBlocks(List<String> value){
        boolean flag=false;
        String previousBlockHash;
        if(value.size()<=1){
            System.out.println("Block is correct");
            return true;
        }
        else{
            int i;
            for (i = value.size()-1; i>=1;) {
                System.out.println("iii:"+i);
                if(i==1){
                    BlockStructure2 blockStructure2 = JSONUtil.convertJsonToJava(value.get(i),
                            BlockStructure2.class);
                    previousBlockHash = blockStructure2.getPrevBlockHash();
                    GenesisBlock2 genesisBlock = JSONUtil.convertJsonToJava(value.get(i - 1),
                            GenesisBlock2.class);
                    if (!previousBlockHash.equals(genesisBlock.getCurrentBlockHash())) {
                        System.out.println("blocks have been manipulated");
                        flag = false;
                        break;
                    } else {
                        System.out.println("blocks verified");
                        flag = true;
                        break;
                    }
                }
                else {
                    BlockStructure2 blockStructure2 = JSONUtil.convertJsonToJava(value.get(i),
                            BlockStructure2.class);
                    previousBlockHash = blockStructure2.getPrevBlockHash();
                    blockStructure2 = JSONUtil.convertJsonToJava(value.get(i - 1),
                            BlockStructure2.class);
                    if (!previousBlockHash.equals(blockStructure2.getCurrentBlockHash())) {
                        System.out.println("blocks have been manipulated");
                        flag = false;
                        break;
                    } else {
                        flag = true;
                        i = i - 2;
                    }
                }
            }
            if(flag)
                return true;
            else
                return false;
        }
    }

    private ArrayList<byte[]> encryptBlock(String value){
        RSAEncryptDecrypt.generateKey();
        int count = 0;
        int start = 0, end = 0;
        String substring;
        ArrayList<String> storeSubString = new ArrayList<>();
        ArrayList<byte[]> storeEncryptedValue = new ArrayList<>();
        while (count <= value.length()) {
            if (value.length() - end > 250) {
                end = start + 251;
                substring = value.substring(start, end);
                storeSubString.add(substring);
                start = start + 251;
            } else {
                start = end;
                end = value.length();
                substring = value.substring(start, end);
                storeSubString.add(substring);
            }
            count = count + 250;
        }
        count = 0;
        while (count != storeSubString.size()) {
            byte[] encryptedData = RSAEncryptDecrypt.encryptData(storeSubString.get(count),
                    SetKeys.getPublicKeyModules(), SetKeys.getPublicKeyExpo());
            storeEncryptedValue.add(encryptedData);
            count++;
        }
        return storeEncryptedValue;
    }

    private String decryptBlock(ArrayList<byte[]> encryptedValues){
        int count=0;
        String substring;
        StringBuilder build = new StringBuilder();
        while (count != encryptedValues.size()) {
            substring = RSAEncryptDecrypt.decryptData(encryptedValues.get(count),
                    SetKeys.getPrivateKeyModules(), SetKeys.getPrivateKeyExpo());
            build.append(substring);
            count++;
        }
        return build.toString();
    }
}
