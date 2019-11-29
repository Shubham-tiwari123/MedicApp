package com.medical.server.service;

import java.util.ArrayList;

public class Services {

    private ArrayList<byte[]> encryptBlock(String value) {
        int count = 0;
        int start = 0, end = 0;
        String substring;
        ArrayList<String> storeSubString = new ArrayList<String>();
        ArrayList<byte[]> storeEncryptedValue = new ArrayList<byte[]>();
        while (count <= value.length()) {
            if (value.length() - end > 250) {
                end = start + 251;
                substring = value.substring(start, end);
                storeSubString.add(substring);
                start = start + 251;
            } else {
                start = end;
                end = value.length();
                substring = value.substring(start, end);
                storeSubString.add(substring);
            }
            count = count + 250;
        }
        count = 0;
        while (count != storeSubString.size()) {
            byte[] encryptedData = RSAEncryptDecrypt.encryptData(storeSubString.get(count),
                    SetKeys.getPublicKeyModules(), SetKeys.getPublicKeyExpo());
            storeEncryptedValue.add(encryptedData);
            count++;
        }
        return storeEncryptedValue;
    }

}
