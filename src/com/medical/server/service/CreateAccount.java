package com.medical.server.service;

import com.medical.server.dao.Database;
import com.medical.server.dao.DatabaseHospital;
import com.medical.server.entity.GenesisBlockEncrypt;
import com.medical.server.entity.GenesisBlockHash;
import com.medical.server.entity.StoreServerKeys;
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
    private DatabaseHospital databaseHospital = new DatabaseHospital();

    @Override
    public boolean verifyHospital(String details) throws Exception {
        System.out.println("verify if username exists/not exists (file name):"+getClass());
        return databaseHospital.verifyUsername(details, VariableClass.REGISTER_HEALTH_CARE);
    }

    @Override
    public long generateNewID() throws Exception {
        int count=0;
        long generatedID;
        do {
            Random random = new Random();
            generatedID = 10000+random.nextInt(89999);
            count++;
        }while(!checkIdDB(generatedID) && count!=7);
        if(count==7){
            System.out.println("server times out");
            throw new Exception("Not of limit");
        }
        System.out.println("generated id:"+ generatedID);
        return generatedID;
    }

    @Override
    public boolean checkIdDB(long generatedID) throws Exception{
        return database.verifyPatientIdDB(generatedID, VariableClass.STORE_DATA_COLLECTION);
    }

    @Override
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

    @Override
    public String calBlockHashValue(String data) throws NoSuchAlgorithmException {
        return extraFunctions.calculateHash(data);
    }

    @Override
    public boolean storeBlock(GenesisBlockHash block, long patientID) throws Exception{
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

    @Override
    public ArrayList<byte[]> encryptBlock(String data) throws Exception{
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

        if(database.getServerPrivateKeys(VariableClass.STORE_KEYS))
        while (count != storeSubString.size()) {
            byte[] encryptedData =  extraFunctions.encryptData(storeSubString.get(count),
                    StoreServerKeys.getPrivateKeyModules(), StoreServerKeys.getPrivateKeyExpo());
            storeEncryptedValue.add(encryptedData);
            count++;
        }
        return storeEncryptedValue;
    }
}
