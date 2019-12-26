package com.medical.client.service;

import com.medical.client.utils.JSONUtil;
import javax.crypto.Cipher;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class ExtraFunctions implements ExtraFunctionsInterface {

    @Override
    public String calculateHash(String value) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHashValue = digest.digest(value.getBytes(StandardCharsets.UTF_8));
        StringBuilder hashValue = new StringBuilder(2 * encodedHashValue.length);
        for (byte b : encodedHashValue) {
            hashValue.append(b);
        }
        return hashValue.toString();
    }

    @Override
    public byte[] encryptData(String data, BigInteger modulus, BigInteger expo) throws Exception {
        byte[] dataToEncrypt = data.getBytes(StandardCharsets.UTF_8);
        System.out.println("data size:" + dataToEncrypt.length);
        byte[] encryptedData = null;
        RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, expo);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = factory.generatePublic(rsaPublicKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        encryptedData = cipher.doFinal(dataToEncrypt);
        return encryptedData;
    }

    @Override
    public String decryptData(byte[] data, BigInteger modulus, BigInteger expo) throws Exception {
        byte[] decryptedData;
        String value = null;
        RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(modulus, expo);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = factory.generatePrivate(rsaPrivateKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        decryptedData = cipher.doFinal(data);
        value = new String(decryptedData, StandardCharsets.UTF_8);
        return value;
    }

    @Override
    public <T> T convertJsonToJava(String jsonString, Class<T> obj) throws Exception {
        return JSONUtil.convertJsonToJava(jsonString, obj);
    }

    @Override
    public String convertJavaToJson(Object object) throws Exception {
        return JSONUtil.convertJavaToJson(object);
    }

}
