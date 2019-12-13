package com.medical.server.service;

import com.medical.server.dao.Database;
import com.medical.server.entity.GenesisBlockEncrypt;
import com.medical.server.entity.GenesisBlockHash;
import com.medical.server.utils.VariableClass;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

public class CreateAccount implements CreateAccountInterface{

    private Database database =new Database();
    private ExtraFunctions extraFunctions = new ExtraFunctions();
    private static long generatedID = 0;

    public long generateNewID() {
        do {
            Random random = new Random();
            generatedID = 10000+random.nextInt(99999);
        }while(!checkIdDB(generatedID));
        System.out.println("generated id:"+generatedID);
        return generatedID;
    }

    public boolean checkIdDB(long generatedID) {
        return database.verifyPatientIdDB(generatedID, VariableClass.STORE_DATA_COLLECTION);
    }

    public GenesisBlockHash createGenesisBlock(long generatedID) throws NoSuchAlgorithmException {
        System.out.println("creating genesis block.....");
        GenesisBlockHash block = new GenesisBlockHash();
        block.setId(generatedID);
        block.setCreationDate(Date.valueOf(LocalDate.now()));
        block.setCreationTime(Time.valueOf(LocalTime.now()));
        block.setCompanyName("medicApp");
        block.setPreviousBlockHash("shivamB56vishankC13divyaC19mehulC15");
        String jsonString = extraFunctions.convertJavaToJson(block);
        block.setCurrentBlockHash(calBlockHashValue(jsonString));
        System.out.println("current hash:"+block.getCurrentBlockHash());
        return block;
    }

    public String calBlockHashValue(String data) throws NoSuchAlgorithmException {
        return extraFunctions.calculateHash(data);
    }

    public boolean storeBlock(GenesisBlockHash block, long patientID) {
        GenesisBlockEncrypt blockEncrypt = new GenesisBlockEncrypt();
        blockEncrypt.setId(block.getId());
        blockEncrypt.setCompanyName(block.getCompanyName());
        blockEncrypt.setCreationDate(block.getCreationDate());
        blockEncrypt.setCreationTime(block.getCreationTime());
        blockEncrypt.setPreviousBlockHash(block.getPreviousBlockHash());
        blockEncrypt.setCurrentBlockHash(block.getCurrentBlockHash());

        System.out.println("storing genesis block");
        String data = extraFunctions.convertJavaToJson(blockEncrypt);
        System.out.println("data:\n  "+data);
        ArrayList<byte[]> encryptedData = encryptBlock(data);
        return database.saveGenesisBlockDB(VariableClass.STORE_DATA_COLLECTION,encryptedData,
               patientID);
    }

    public ArrayList<byte[]> encryptBlock(String data) {
        System.out.println("encrypting genesis block....");
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
        //SetKeys keys = extraFunctions.getServerKeyFromFile();

        while (count != storeSubString.size()) {
            byte[] encryptedData =  extraFunctions.encryptData(storeSubString.get(count),
                    VariableClass.pubMod, VariableClass.pubExpo);
            storeEncryptedValue.add(encryptedData);
            count++;
        }
        return storeEncryptedValue;
    }
}
