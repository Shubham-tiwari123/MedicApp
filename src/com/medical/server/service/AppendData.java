package com.medical.server.service;

import com.medical.server.dao.Database;
import com.medical.server.entity.*;
import com.medical.server.utils.VariableClass;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class AppendData implements AppendDataInterface {

    private Database database = new Database();
    private ExtraFunctions extraFunctions = new ExtraFunctions();
    private static ClientSideBlock block;

    @Override
    public boolean verifyID(long patientID) {
        return !database.verifyPatientIdDB(patientID, VariableClass.STORE_DATA_COLLECTION);
    }

    @Override
    public String decryptData(ArrayList<byte[]> data, SetKeys getKeys) {
        ArrayList<String> storeDecryptData = new ArrayList<String>();

        for (byte[] datum : data) {
            String decryptedData = extraFunctions.decryptData(datum, VariableClass.priMod,
                    VariableClass.priExpo);
            storeDecryptData.add(decryptedData);
        }
        StringBuilder builder = new StringBuilder();
        for (String val : storeDecryptData) {
            builder.append(val);
        }
        return builder.toString();
    }

    @Override
    public boolean verifyData(String data) throws NoSuchAlgorithmException {
        block = extraFunctions.convertJsonToJava(data, ClientSideBlock.class);

        ClientSideBlockHash blockHash = new ClientSideBlockHash();
        blockHash.setPatientId(block.getPatientId());
        blockHash.setDate(block.getDate());
        blockHash.setTime(block.getTime());
        blockHash.setDoctorName(block.getDoctorName());
        blockHash.setHospitalName(block.getHospitalName());
        blockHash.setSpecialistType(block.getSpecialistType());
        blockHash.setPrescription(block.getPrescription());

        // convert block to string
        String convertString = extraFunctions.convertJavaToJson(blockHash);
        System.out.println("verify block:\n"+convertString);
        String hashValue = calCurrentBlockHash(convertString);
        System.out.println("hash2:"+hashValue);

        return hashValue.equals(block.getCurrentBlockHash());
    }

    @Override
    public String getLastBlockHashDb(long patientID) {

        ArrayList<ArrayList<byte[]>> dataFromDb = database.getSpecificData(patientID,
                VariableClass.STORE_DATA_COLLECTION);
        System.out.println("size of array list:"+dataFromDb.size()+"\n"+dataFromDb.get(dataFromDb.size()-1));

        String chain = extraFunctions.convertEncryptedData(dataFromDb.get(dataFromDb.size()-1),
                database.getServerKey(VariableClass.STORE_KEYS));

        System.out.println("chains:"+chain);
        String lastBlockHash = null;
        if (dataFromDb.size() == 1) {
            GenesisBlockEncrypt block = extraFunctions.convertJsonToJava(chain, GenesisBlockEncrypt.class);
            lastBlockHash = block.getCurrentBlockHash();
        } else {
            ServerSideBlock serverBlock = extraFunctions.convertJsonToJava(chain,ServerSideBlock.class);
            lastBlockHash = serverBlock.getCurrentBlockHash();
        }
        System.out.println("last:"+lastBlockHash);
        return lastBlockHash;
    }

    @Override
    public String updateBlock(String lastBlockHash, String data) throws NoSuchAlgorithmException {
        block = extraFunctions.convertJsonToJava(data, ClientSideBlock.class);

        //insert last block hash
        CreateNewBlock newBlock = new CreateNewBlock();
        newBlock.setDate(block.getDate());
        newBlock.setTime(block.getTime());
        newBlock.setPatientId(block.getPatientId());
        newBlock.setDoctorName(block.getDoctorName());
        newBlock.setHospitalName(block.getHospitalName());
        newBlock.setPrescription(block.getPrescription());
        newBlock.setSpecialistType(block.getSpecialistType());
        newBlock.setPreviousBlockHash(lastBlockHash);

        String convertObj = extraFunctions.convertJavaToJson(newBlock);
        // re-calculate hash value
        String newHashOfBlock = calCurrentBlockHash(convertObj);
        // create new block and insert current hash
        ServerSideBlock storeBlock = new ServerSideBlock();
        storeBlock.setDate(block.getDate());
        storeBlock.setTime(block.getTime());
        storeBlock.setPatientId(block.getPatientId());
        storeBlock.setDoctorName(block.getDoctorName());
        storeBlock.setHospitalName(block.getHospitalName());
        storeBlock.setPrescription(block.getPrescription());
        storeBlock.setSpecialistType(block.getSpecialistType());
        storeBlock.setPreviousBlockHash(lastBlockHash);
        storeBlock.setCurrentBlockHash(newHashOfBlock);
        //return the new block as string for encryption
        return extraFunctions.convertJavaToJson(storeBlock);
    }

    @Override
    public String calCurrentBlockHash(String data) throws NoSuchAlgorithmException {
        return extraFunctions.calculateHash(data);
    }

    @Override
    public boolean appendBlockInChain(long patientId, String data, SetKeys keys) {
        // encrypt the string using server private key
        ArrayList<byte[]> encryptedValue = encryptBlock(data);
        return database.updateChain(encryptedValue, patientId,VariableClass.STORE_DATA_COLLECTION);
    }

    @Override
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
