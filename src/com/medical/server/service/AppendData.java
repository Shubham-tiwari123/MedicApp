package com.medical.server.service;

import com.medical.server.dao.Database;
import com.medical.server.entity.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class AppendData implements AppendDataInterface {

    private Database database = new Database();
    private ExtraFunctions extraFunctions = new ExtraFunctions();
    private static ClientSideBlock block;

    public boolean verifyID(int patientID) {
        return database.verifyPatientIdDB(patientID, "");
    }

    public String decryptData(ArrayList<byte[]> data, SetKeys getKeys) {
        ArrayList<String> storeDecryptData = new ArrayList<String>();
        for (byte[] datum : data) {
            String decryptedData = extraFunctions.decryptData(datum, getKeys.getPublicKeyModules(),
                    getKeys.getPublicKeyExpo());
            storeDecryptData.add(decryptedData);
        }
        StringBuilder builder = new StringBuilder();
        for (String val : storeDecryptData) {
            builder.append(val);
        }
        return builder.toString();
    }

    public boolean verifyData(String data) {
        block = extraFunctions.convertJsonToJava(data, ClientSideBlock.class);
        // convert block to string
        String convertString = extraFunctions.convertJavaToJson(block);
        String hashValue = calCurrentBlockHash(convertString);
        return hashValue.equals(block.getCurrentBlockHash());
    }

    public String getLastBlockHashDb(int patientID) {
        List<byte[]> dataFromDb = database.getSpecificData(patientID);
        List<String> chain = extraFunctions.convertEncryptedData(dataFromDb,
                extraFunctions.getServerKeyFromFile());

        String lastBlockHash = null;
        if (chain.size() == 1) {
            GenesisBlock block = extraFunctions.convertJsonToJava(chain.get(0), GenesisBlock.class);
            lastBlockHash = block.getCurrentBlockHash();
        } else {
            ServerSideBlock serverBlock = extraFunctions.convertJsonToJava(chain.get(chain.size() - 1),
                    ServerSideBlock.class);
            lastBlockHash = serverBlock.getCurrentBlockHash();
        }
        return lastBlockHash;
    }

    public String updateBlock(String lastBlockHash, String data) {
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

    public String calCurrentBlockHash(String data) {
        return extraFunctions.calculateHash(data);
    }

    public boolean appendBlockInChain(int patientId, String data, BigInteger modulus, BigInteger expo) {
        // encrypt the string using server private key
        byte[] encryptedValue = extraFunctions.encryptData(data, modulus, expo);
        return database.updateChain(encryptedValue, patientId);
    }
}
