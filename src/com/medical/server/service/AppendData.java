package com.medical.server.service;

import com.medical.server.dao.Database;
import com.medical.server.entity.ClientBlock;
import com.medical.server.entity.ClientBlock2;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

public class AppendData implements AppendDataInterface {

    Database database = new Database();
    ExtraFunctions extraFunctions = new ExtraFunctions();
    public boolean verifyID(int patientID) {
        if(database.createDbConn() && database.checkCollection(""))
            if(database.verifyPatientIdDB(patientID))
                return true;
        return false;
    }

    public String decryptData(List<byte[]> data, BigInteger modulus,BigInteger expo) {
        List<String> storeDecryptData = new LinkedList<String>();
        for(int i=0;i<data.size();i++) {
            String decryptedData = extraFunctions.decryptData(data.get(i),modulus,expo);
            storeDecryptData.add(decryptedData);
        }
        StringBuilder builder = new StringBuilder();
        for(String val: storeDecryptData){
            builder.append(val);
        }
        return builder.toString();
    }

    public boolean verifyData(String data) {
        ClientBlock block = extraFunctions.convertJsonToJava(data,ClientBlock.class);

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
        if(hashValue.equals(block.getCurrentBlockHash()))
            return true;
        return false;
    }

    public String getLastBlockHashDb(int patientID) {
        List chain = database.getSpecificData(patientID);
        
        return null;
    }

    public String insertLastBlockHash(String data, String lastBlockHash) {
        return null;
    }

    public String calCurrentBlockHash(String data) {
        String hashValue = extraFunctions.calculateHash(data);
        return hashValue;
    }

    public String updateCurrentBlockHash(String currBlockHash) {
        return null;
    }

    public boolean appendBlockInChain(String data) {
        return false;
    }
}
