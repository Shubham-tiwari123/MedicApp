package com.medical.client.service;

import com.medical.client.dao.Database;
import com.medical.client.entity.ClientSideBlock;
import com.medical.client.entity.ClientSideBlockHash;
import com.medical.client.entity.ServerKeys;
import com.medical.client.entity.SetKeys;
import com.medical.client.utils.VariableClass;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class SendRecord implements SendRecordInterface {
    private ExtraFunctions extraFunctions = new ExtraFunctions();

    @Override
    public String calBlockHash(String data) throws NoSuchAlgorithmException {
        return extraFunctions.calculateHash(data);
    }

    @Override
    public String prepareBlock(ClientSideBlockHash block, String hashValue) {
        ClientSideBlock sideBlock = new ClientSideBlock();

        sideBlock.setPatientId(block.getPatientId());
        sideBlock.setDate(block.getDate());
        sideBlock.setTime(block.getTime());
        sideBlock.setDoctorName(block.getDoctorName());
        sideBlock.setHospitalName(block.getHospitalName());
        sideBlock.setSpecialistType(block.getSpecialistType());
        sideBlock.setPrescription(block.getPrescription());
        sideBlock.setCurrentBlockHash(hashValue);
        return extraFunctions.convertJavaToJson(sideBlock);
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
        ServerKeys keys = getKeysFromDatabase();
        while (count != storeSubString.size()) {
            byte[] encryptedData =  extraFunctions.encryptData(storeSubString.get(count),
                    keys.getPublicKeyModules(), keys.getPublicKeyExpo());
            storeEncryptedValue.add(encryptedData);
            count++;
        }
        return storeEncryptedValue;
    }

    @Override
    public ServerKeys getKeysFromDatabase(){
        Database database = new Database();
        return database.getServerKeys(VariableClass.STORE_KEYS);
    }
}
