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
    public ArrayList<byte[]> encryptKey(GetKeys keys, SetKeys setKeys) {
        ExtraFunctions extraFunctions = new ExtraFunctions();

        byte[] encryptClientPubExpo = extraFunctions.encryptData(setKeys.getPublicKeyExpo().toString(),
                new BigInteger(keys.getModulesValue()),new BigInteger(keys.getExpoValue()));

        byte[] encryptClientPubMod = extraFunctions.encryptData(setKeys.getPublicKeyModules().toString(),
                new BigInteger(keys.getModulesValue()),new BigInteger(keys.getExpoValue()));
        ArrayList<byte[]> list = new ArrayList<>();
        list.add(encryptClientPubExpo);
        list.add(encryptClientPubMod);
        return list;
    }
}
