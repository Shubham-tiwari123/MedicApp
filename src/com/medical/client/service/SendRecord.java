package com.medical.client.service;

import com.medical.client.dao.Database;
import com.medical.client.entity.ClientKeys;
import com.medical.client.entity.MedicBlock;
import com.medical.client.utils.VariableClass;
import java.util.ArrayList;

public class SendRecord implements SendRecordInterface {
    private ExtraFunctions extraFunctions = new ExtraFunctions();

    @Override
    public String calBlockHash(String data) throws Exception {
        return extraFunctions.calculateHash(data);
    }

    @Override
    public String prepareBlock(MedicBlock block) throws Exception {
        String jsonString = extraFunctions.convertJavaToJson(block);
        String hash = calBlockHash(jsonString);
        block.setCurrentBlockHash(hash);
        return extraFunctions.convertJavaToJson(block);
    }

    @Override
    public ArrayList<byte[]> encryptBlock(String data) throws Exception {
        ClientKeys keys = getKeysFromDatabase();
        System.out.println("encrypting block....");
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
        while (count != storeSubString.size()) {
            byte[] encryptedData = extraFunctions.encryptData(storeSubString.get(count),
                    keys.getPrivateKeyModules(), keys.getPrivateKeyExpo());
            storeEncryptedValue.add(encryptedData);
            count++;
        }
        return storeEncryptedValue;
    }

    @Override
    public ClientKeys getKeysFromDatabase() throws Exception {
        Database database = new Database();
        return database.getClientKeys2(VariableClass.STORE_KEYS);
    }
}
