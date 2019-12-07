package com.medical.server.service;

import com.medical.server.dao.Database;
import com.medical.server.entity.ClientSideBlock;
import com.medical.server.entity.ServerSideBlock;
import com.medical.server.entity.GenesisBlock;
import com.medical.server.entity.StoreBlock;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class AppendData implements AppendDataInterface {

    private Database database = new Database();
    private ExtraFunctions extraFunctions = new ExtraFunctions();
    private static ClientSideBlock block;

    public boolean verifyID(int patientID) {
        return database.verifyPatientIdDB(patientID,"");
    }

    public String decryptData(ArrayList<byte[]> data, BigInteger modulus, BigInteger expo) {
        ArrayList<String> storeDecryptData = new ArrayList<String>();
        for (byte[] datum : data) {
            String decryptedData = extraFunctions.decryptData(datum, modulus, expo);
            storeDecryptData.add(decryptedData);
        }
        StringBuilder builder = new StringBuilder();
        for(String val: storeDecryptData){
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
        List<String> chain = database.getSpecificData(patientID);
        String lastBlockHash = null;
        if(chain.size()==1){
            GenesisBlock block = extraFunctions.convertJsonToJava(chain.get(0),GenesisBlock.class);
            lastBlockHash = block.getCurrentBlockHash();
        }else{

        }
        return lastBlockHash;
    }

    public String updateBlock(String lastBlockHash,String data) {
        block = extraFunctions.convertJsonToJava(data, ClientSideBlock.class);

        //insert last block hash
        ServerSideBlock serverSideBlock = new ServerSideBlock();
        serverSideBlock.setDate(block.getDate());
        serverSideBlock.setTime(block.getTime());
        serverSideBlock.setPatientId(block.getPatientId());
        serverSideBlock.setDoctorName(block.getDoctorName());
        serverSideBlock.setHospitalName(block.getHospitalName());
        serverSideBlock.setPrescription(block.getPrescription());
        serverSideBlock.setSpecialistType(block.getSpecialistType());
        serverSideBlock.setPreviousBlockHash(lastBlockHash);

        String convertObj = extraFunctions.convertJavaToJson(serverSideBlock);
        // re-calculate hash value
        String newHashOfBlock = calCurrentBlockHash(convertObj);
        // create new block and insert current hash
        StoreBlock storeBlock = new StoreBlock();
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

    public boolean appendBlockInChain(String data) {
        // encrypt the string
        if(database.createDbConn() && database.checkCollection("")){
            return database.updateChain(data);
        }
        return false;
    }
}
