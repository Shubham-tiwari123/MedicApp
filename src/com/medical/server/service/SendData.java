package com.medical.server.service;

import com.medical.server.dao.Database;
import com.medical.server.entity.SetKeys;
import com.medical.server.utils.VariableClass;

import java.util.ArrayList;
import java.util.List;

public class SendData implements SendDataInterface {

    private Database database = new Database();
    private ExtraFunctions extraFunctions = new ExtraFunctions();

    public boolean verifyID(long patientID) {
        return !database.verifyPatientIdDB(patientID, VariableClass.STORE_DATA_COLLECTION);
    }

    public List<String> getDataDB(long patientID) {
        ArrayList<ArrayList<byte[]>> getAllData= database.getSpecificData(patientID,VariableClass.STORE_DATA_COLLECTION);
        List<String> convertToString = new ArrayList<>();
        for(ArrayList<byte[]> val:getAllData){
            StringBuilder builder = new StringBuilder();
            for (byte[] encryptedVal:val){
                String subString = extraFunctions.decryptData(encryptedVal,VariableClass.priMod,VariableClass.priExpo);
                builder.append(subString);
            }
            convertToString.add(builder.toString());
        }
        return convertToString;
    }

    public SetKeys getKeysOfClient(int hospitalID) {
        SetKeys keys = new SetKeys();
        // get client keys from file using hospitalID
        return keys;
    }

    public ArrayList<ArrayList<byte[]>> encryptDataAgain(SetKeys keys, List<String> data) {
        ArrayList<ArrayList<byte[]>> encryptDataList = new ArrayList<>();
        for (String val : data) {
            // encrypt data and store it in encrypted data;
            // send the data to the client
            ArrayList<byte[]> subList = encryptBlock(val,keys);
            encryptDataList.add(subList);
        }
        return encryptDataList;
    }

    public ArrayList<byte[]> encryptBlock(String data,SetKeys keys) {
        System.out.println("encrypting block before sending....");
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
                    VariableClass.clientPubMod, VariableClass.clientPubExpo);
            storeEncryptedValue.add(encryptedData);
            count++;
        }
        return storeEncryptedValue;
    }
}
