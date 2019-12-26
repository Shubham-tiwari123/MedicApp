package com.medical.client.service;

import com.medical.client.dao.Database;
import com.medical.client.entity.GetKeys;
import com.medical.client.entity.SetKeys;
import com.medical.client.utils.VariableClass;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;

public class RegisterUser implements RegisterUserInterface {

    private Database database = new Database();

    @Override
    public SetKeys generateKeys() throws Exception {
        System.out.println("generating keys");
        SetKeys setKeys = new SetKeys();
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
        return setKeys;
    }

    @Override
    public boolean storeKeys(GetKeys keys, SetKeys setKeys) throws Exception {
        return database.storeKeys(keys, VariableClass.STORE_KEYS, setKeys);
    }

    @Override
    public boolean verifyServerKey(GetKeys keys) throws Exception {
        System.out.println("verifying keys with hash");
        ExtraFunctions extraFunctions = new ExtraFunctions();
        String expoHash = extraFunctions.calculateHash(keys.getExpoValue());
        String modulesHash = extraFunctions.calculateHash(keys.getModulesValue());

        return expoHash.equals(keys.getExpoHashValue()) && modulesHash.equals(keys.getModulesHashValue());
    }

    @Override
    public ArrayList<byte[]> encryptKey(GetKeys keys, String encryptKey) throws Exception {
        System.out.println("Encrypting client keys using server public keys.....");
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
            byte[] encryptedData = extraFunctions.encryptData(storeSubString.get(count),
                    new BigInteger(keys.getModulesValue()), new BigInteger(keys.getExpoValue()));
            storeEncryptedValue.add(encryptedData);
            count++;
        }
        return storeEncryptedValue;
    }
}
