package com.medical.server.service;

import com.medical.server.dao.Database;
import com.medical.server.entity.ClientBlock;
import com.medical.server.entity.ClientBlock2;
import com.medical.server.entity.GenesisBlock;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

public class AppendData implements AppendDataInterface {

    private Database database = new Database();
    private ExtraFunctions extraFunctions = new ExtraFunctions();
    private static ClientBlock block;

    public boolean verifyID(int patientID) {
        if(database.createDbConn() && database.checkCollection(""))
            return database.verifyPatientIdDB(patientID);
        return false;
    }

    public String decryptData(List<byte[]> data, BigInteger modulus,BigInteger expo) {
        List<String> storeDecryptData = new LinkedList<String>();
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
        block = extraFunctions.convertJsonToJava(data,ClientBlock.class);

        ClientBlock2 clientBlock2 = new ClientBlock2();
        clientBlock2.setDate(block.getDate());
        clientBlock2.setTime(block.getTime());
        clientBlock2.setPatientId(block.getPatientId());
        clientBlock2.setDoctorName(block.getDoctorName());
        clientBlock2.setHospitalName(block.getHospitalName());
        clientBlock2.setPrescription(block.getPrescription());
        clientBlock2.setSpecialistType(block.getSpecialistType());

        String convertObj = extraFunctions.convertJavaToJson(clientBlock2);
        String hashValue = calCurrentBlockHash(convertObj);
        return hashValue.equals(block.getCurrentBlockHash());
    }

    public String getLastBlockHashDb(int patientID) {
        List<String> chain = database.getSpecificData(patientID);
        String lastBlockHash = null;
        if(chain.size()==1){
            GenesisBlock block = extraFunctions.convertJsonToJava(chain.get(0),GenesisBlock.class);
            lastBlockHash = block.getPreviousBlockHash();
        }
        return lastBlockHash;
    }

    public String updateBlock(String lastBlockHash) {
        block.setPrevBlockHash(lastBlockHash);
        String data = extraFunctions.convertJavaToJson(block);
        String newHashOfBlock = calCurrentBlockHash(data);
        block.setCurrentBlockHash(newHashOfBlock);
        return extraFunctions.convertJavaToJson(block);
    }

    public String calCurrentBlockHash(String data) {
        return extraFunctions.calculateHash(data);
    }

    public boolean appendBlockInChain(String data) {
        if(database.createDbConn() && database.checkCollection("")){
            return database.updateChain(data);
        }
        return false;
    }
}
