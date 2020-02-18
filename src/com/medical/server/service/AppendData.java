package com.medical.server.service;

import com.medical.server.dao.Database;
import com.medical.server.entity.*;
import com.medical.server.utils.VariableClass;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class AppendData implements AppendDataInterface {

    private Database database = new Database();
    private ExtraFunctions extraFunctions = new ExtraFunctions();
    private static MedicBlock block;

    @Override
    public boolean verifyID(long patientID) throws Exception {
        return !database.verifyPatientIdDB(patientID, VariableClass.REGISTER_PATIENT);
    }

    @Override
    public boolean verifyHospital (String username) throws Exception{
        return database.verifyHospital(username,VariableClass.REGISTER_HEALTH_CARE);
    }

    @Override
    public String decryptData(ArrayList<byte[]> data,String hospitalUserName)  throws Exception{
        ClientKeys clientKeys = database.getClientKeys(hospitalUserName,VariableClass.STORE_KEYS);

        ArrayList<String> storeDecryptData = new ArrayList<String>();

        for (byte[] datum : data) {
            String decryptedData = extraFunctions.decryptData(datum, clientKeys.getClientPubKeyMod(),
                    clientKeys.getClientPubKeyExpo());
            storeDecryptData.add(decryptedData);
        }
        StringBuilder builder = new StringBuilder();
        for (String val : storeDecryptData) {
            builder.append(val);
        }
        return builder.toString();
    }

    @Override
    public boolean verifyData(String data) throws Exception {
        MedicBlock block = convertJsonToJava(data, MedicBlock.class);
        String currentBlockHash = block.getCurrentBlockHash();
        block.setCurrentBlockHash("0");
        String reCalculateHash = calCurrentBlockHash(convertJavaToJson(block));
        System.out.println("hash:"+currentBlockHash+" ; "+reCalculateHash);
        return reCalculateHash.equals(currentBlockHash);
    }

    @Override
    public String getLastBlockHashDb(long patientID) throws Exception{

        ArrayList<ArrayList<byte[]>> dataFromDb = database.getSpecificData(patientID,
                VariableClass.STORE_DATA_COLLECTION);

        String chain = extraFunctions.convertEncryptedData(dataFromDb.get(dataFromDb.size()-1),
                database.getServerKey(VariableClass.STORE_KEYS));

        System.out.println("chains:"+chain);
        String lastBlockHash = null;
        if (dataFromDb.size() == 1) {
            GenesisBlock block = convertJsonToJava(chain, GenesisBlock.class);
            lastBlockHash = block.getCurrentBlockHash();
        } else {
            MedicBlock medicBlock = convertJsonToJava(chain,MedicBlock.class);
            lastBlockHash = medicBlock.getCurrentBlockHash();
        }
        System.out.println("last:"+lastBlockHash);
        return lastBlockHash;
    }

    @Override
    public String updateBlock(String lastBlockHash, String data) throws Exception {
        System.out.println("updating block");
        MedicBlock block = convertJsonToJava(data, MedicBlock.class);
        block.setPreviousBlockHash(lastBlockHash);
        String convertObj = convertJavaToJson(block);
        System.out.println("previous added:"+convertObj);
        // re-calculate hash value
        String newHashOfBlock = calCurrentBlockHash(convertObj);
        // create new block and insert current hash
        block.setCurrentBlockHash(newHashOfBlock);
        return convertJavaToJson(block);
    }

    @Override
    public String calCurrentBlockHash(String data) throws NoSuchAlgorithmException {
        return extraFunctions.calculateHash(data);
    }

    @Override
    public boolean appendBlockInChain(long patientId, String data) throws Exception{
        // encrypt the string using server private key
        ArrayList<byte[]> encryptedValue = encryptBlock(data);
        return database.updateChain(encryptedValue, patientId,VariableClass.STORE_DATA_COLLECTION);
    }

    @Override
    public <T> T convertJsonToJava(String jsonString, Class<T> obj) throws Exception {
        return extraFunctions.convertJsonToJava(jsonString,obj);
    }

    @Override
    public String convertJavaToJson(Object object) throws Exception {
        return extraFunctions.convertJavaToJson(object);
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
        ServerKeys serverKeys = database.getServerKey(VariableClass.STORE_KEYS);
        while (count != storeSubString.size()) {
            byte[] encryptedData =  extraFunctions.encryptData(storeSubString.get(count),
                    serverKeys.getPrivateKeyModules(), serverKeys.getPrivateKeyExpo());
            storeEncryptedValue.add(encryptedData);
            count++;
        }
        return storeEncryptedValue;
    }
}
