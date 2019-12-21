package com.medical.client.service;

import com.medical.client.entity.SetKeys;
import com.medical.client.utils.JSONUtil;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;

public class ExtraFunctions implements ExtraFunctionsInterface {

    @Override
    public String calculateHash(String value) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHashValue = digest.digest(value.getBytes(StandardCharsets.UTF_8));
        StringBuilder hashValue = new StringBuilder(2 * encodedHashValue.length);
        for (byte b : encodedHashValue) {
            hashValue.append(b);
        }
        return hashValue.toString();
    }

    @Override
    public byte[] encryptData(String data, BigInteger modulus, BigInteger expo) {
        byte[] dataToEncrypt = data.getBytes(StandardCharsets.UTF_8);
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

    @Override
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
            value = new String(decryptedData,StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println(e);
        }
        return value;
    }

    @Override
    public <T> T convertJsonToJava(String jsonString, Class<T> obj) {
        return JSONUtil.convertJsonToJava(jsonString,obj);
    }

    @Override
    public String convertJavaToJson(Object object) {
        return JSONUtil.convertJavaToJson(object);
    }

}
