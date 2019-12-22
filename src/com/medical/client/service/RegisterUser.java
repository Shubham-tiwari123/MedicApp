package com.medical.client.service;

import com.medical.client.dao.Database;
import com.medical.client.entity.GetKeys;
import com.medical.client.entity.HospitalDetails;
import com.medical.client.entity.SetKeys;
import com.medical.client.utils.JSONUtil;
import com.medical.client.utils.VariableClass;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RegisterUser implements RegisterUserInterface{

    Database database = new Database();

    @Override
    public SetKeys generateKeys() {
        System.out.println("generating keys");
        SetKeys setKeys = new SetKeys();
        try {
            KeyPairGenerator keyPair = KeyPairGenerator.getInstance("RSA");
            keyPair.initialize(3072);//3072
            KeyPair pair = keyPair.generateKeyPair();
            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();
            // Generating key-pair
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec rsaPublicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
            RSAPrivateKeySpec rsaPrivateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
            //Setting public keys
            setKeys.setPublicKeyExpo(rsaPublicKeySpec.getPublicExponent());
            setKeys.setPublicKeyModules(rsaPublicKeySpec.getModulus());
            //Setting private keys
            setKeys.setPrivateKeyExpo(rsaPrivateKeySpec.getPrivateExponent());
            setKeys.setPrivateKeyModules(rsaPrivateKeySpec.getModulus());
        } catch (Exception e) {
            System.out.println(e);
        }
        return setKeys;
    }

    @Override
    public boolean storeKeys(GetKeys keys,SetKeys setKeys) {
        return database.storeKeys(keys, VariableClass.STORE_KEYS,setKeys);
    }

    @Override
    public boolean verifyServerKey(GetKeys keys) {
        System.out.println("verifying keys with hash");
        ExtraFunctions extraFunctions = new ExtraFunctions();
        try {
            String expoHash = extraFunctions.calculateHash(keys.getExpoValue());
            String modulesHash = extraFunctions.calculateHash(keys.getModulesValue());

            return expoHash.equals(keys.getExpoHashValue()) && modulesHash.equals(keys.getModulesHashValue());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<byte[]> encryptKey(GetKeys keys, String encryptKey) {
        ExtraFunctions extraFunctions = new ExtraFunctions();
        int count = 0;
        int start = 0, end = 0;
        String substring;
        ArrayList<String> storeSubString = new ArrayList<String>();
        ArrayList<byte[]> storeEncryptedValue = new ArrayList<byte[]>();
        while (count <= encryptKey.length()) {
            if (encryptKey.length() - end > 250) {
                end = start + 251;
                substring = encryptKey.substring(start, end);
                storeSubString.add(substring);
                start = start + 251;
            } else {
                start = end;
                end = encryptKey.length();
                substring = encryptKey.substring(start, end);
                storeSubString.add(substring);
            }
            count = count + 250;
        }
        count = 0;
        while (count != storeSubString.size()) {
            byte[] encryptedData =  extraFunctions.encryptData(storeSubString.get(count),
                    new BigInteger(keys.getModulesValue()),new BigInteger(keys.getExpoValue()));
            storeEncryptedValue.add(encryptedData);
            count++;
        }
        return storeEncryptedValue;
    }
}
