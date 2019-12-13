package com.medical.server.service;

import com.medical.server.entity.SetKeys;
import com.medical.server.utils.JSONUtil;
import com.medical.server.utils.VariableClass;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ExtraFunctions implements ExtraFunctionsInterface {

    public String calculateHash(String value) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHashValue = digest.digest(value.getBytes(StandardCharsets.UTF_8));
        StringBuilder hashValue = new StringBuilder(2 * encodedHashValue.length);
        for (byte b : encodedHashValue) {
            hashValue.append(b);
        }
        return hashValue.toString();
    }

    public void generateKey() {
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
            SetKeys setKeys = new SetKeys();
            setKeys.setPublicKeyExpo(rsaPublicKeySpec.getPublicExponent());
            setKeys.setPublicKeyModules(rsaPublicKeySpec.getModulus());
            //Setting private keys
            setKeys.setPrivateKeyExpo(rsaPrivateKeySpec.getPrivateExponent());
            setKeys.setPrivateKeyModules(rsaPrivateKeySpec.getModulus());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public byte[] encryptData(String data, BigInteger modulus, BigInteger expo) {
        byte[] dataToEncrypt = data.getBytes();
        System.out.println("data size:" + dataToEncrypt.length);
        byte[] encryptedData = null;
        try {
            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, expo);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = factory.generatePublic(rsaPublicKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encryptedData = cipher.doFinal(dataToEncrypt);
        } catch (Exception e) {
            System.out.println(e);
        }
        return encryptedData;
    }

    public String decryptData(byte[] data, BigInteger modulus, BigInteger expo) {
        byte[] decryptedData;
        String value = null;
        try {
            RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(modulus, expo);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = factory.generatePrivate(rsaPrivateKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            decryptedData = cipher.doFinal(data);
            value = new String(decryptedData);
        } catch (Exception e) {
            System.out.println(e);
        }
        return value;
    }

    public SetKeys getServerKeyFromFile() {
        SetKeys getKeys = new SetKeys();
        //Read file and get the keys and store it in getKey obj and return the obj
        return getKeys;
    }

    public String convertEncryptedData(ArrayList<byte[]> data, SetKeys getKeys) {
        StringBuilder builder = new StringBuilder();
        for (byte[] byteValue : data) {
            String val = decryptData(byteValue, VariableClass.priMod,VariableClass.priExpo);
            builder.append(val);
        }
        return builder.toString();
    }

    public <T> T convertJsonToJava(String jsonString, Class<T> obj) {
        return JSONUtil.convertJsonToJava(jsonString,obj);
    }

    public String convertJavaToJson(Object object) {
        return JSONUtil.convertJavaToJson(object);
    }

    public String sendKeyClient(BigInteger modulus, BigInteger expo) {
        return null;
    }

    public String saveKeysDb(BigInteger modulus, BigInteger expo) {
        return null;
    }
}
