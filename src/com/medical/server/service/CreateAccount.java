package com.medical.server.service;

import com.medical.server.dao.Database;
import com.medical.server.entity.GenesisBlock;
import com.medical.server.entity.SetKeys;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;

public class CreateAccount implements CreateAccountInterface{

    private Database database =new Database();
    private ExtraFunctions extraFunctions = new ExtraFunctions();
    private static int generatedID = 0;

    public int generateNewID() {
        do {
            Random random = new Random();
            generatedID = 10000+random.nextInt(99999);
        }while(!checkIdDB(generatedID));
        return generatedID;
    }

    public boolean checkIdDB(int generatedID) {
        return database.verifyPatientIdDB(generatedID,"");
    }

    public GenesisBlock createGenesisBlock(int generatedID) {
        GenesisBlock block = new GenesisBlock();
        block.setId(generatedID);
        block.setCreationDate(Date.valueOf(""));
        block.setCreationTime(Time.valueOf(""));
        block.setCompanyName("medicApp");
        block.setPreviousBlockHash("shivamB56vishankC13divyaC19mehulC15");
        String jsonString = extraFunctions.convertJavaToJson(block);
        block.setCurrentBlockHash(calBlockHashValue(jsonString));
        return block;
    }

    public String calBlockHashValue(String data){
        return extraFunctions.calculateHash(data);
    }

    public boolean storeBlock(GenesisBlock block) {
        String data = extraFunctions.convertJavaToJson(block);
        ArrayList<byte[]> encryptedData = encryptBlock(data);
        return database.saveGenesisBlockDB("", encryptedData);
    }

    public ArrayList<byte[]> encryptBlock(String data) {
        int count = 0;
        int start = 0, end = 0;
        String substring;
        ArrayList<String> storeSubString = new ArrayList<String>();
        ArrayList<byte[]> storeEncryptedValue = new ArrayList<byte[]>();
        while (count <= data.length()) {
            if (data.length() - end > 250) {
                end = start + 251;
                substring = data.substring(start, end);
                storeSubString.add(substring);
                start = start + 251;
            } else {
                start = end;
                end = data.length();
                substring = data.substring(start, end);
                storeSubString.add(substring);
            }
            count = count + 250;
        }
        count = 0;
        SetKeys keys = extraFunctions.getServerKeyFromFile();
        while (count != storeSubString.size()) {
            byte[] encryptedData =  extraFunctions.encryptData(storeSubString.get(count),
                    keys.getPublicKeyModules(), keys.getPublicKeyExpo());
            storeEncryptedValue.add(encryptedData);
            count++;
        }
        return storeEncryptedValue;
    }
}
