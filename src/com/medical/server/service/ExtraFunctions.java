package com.medical.server.service;

import com.medical.server.entity.SetKeys;
import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class ExtraFunctions implements ExtraFunctionsInterface {

    public String calculateHash(String data) {
        return data;
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
            RSAPublicKeySpec rsaPublicKeySpec = keyFactory.getKeySpec(publicKey,RSAPublicKeySpec.class);
            RSAPrivateKeySpec rsaPrivateKeySpec = keyFactory.getKeySpec(privateKey,RSAPrivateKeySpec.class);
            //Setting public keys
            SetKeys.setPublicKeyExpo(rsaPublicKeySpec.getPublicExponent());
            SetKeys.setPublicKeyModules(rsaPublicKeySpec.getModulus());
            //Setting private keys
            SetKeys.setPrivateKeyExpo(rsaPrivateKeySpec.getPrivateExponent());
            SetKeys.setPrivateKeyModules(rsaPrivateKeySpec.getModulus());
        } catch (Exception e) {
            System.out.println(e);;
        }
    }

    public byte[] encryptData(String data, BigInteger modulus, BigInteger expo) {
        byte[] dataToEncrypt = data.getBytes();
        System.out.println("data size:"+dataToEncrypt.length);
        byte[] encryptedData = null;
        try{
            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus,expo);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = factory.generatePublic(rsaPublicKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE,publicKey);
            encryptedData = cipher.doFinal(dataToEncrypt);
        }catch (Exception e){
            System.out.println(e);
        }
        return encryptedData;
    }

    public String decryptData(byte[] data, BigInteger modulus, BigInteger expo) {
        byte[] decryptedData = null;
        String value = null;
        try{
            RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(modulus,expo);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = factory.generatePrivate(rsaPrivateKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE,privateKey);
            decryptedData = cipher.doFinal(data);
            value = new String(decryptedData);
        }catch (Exception e){
            System.out.println(e);
        }
        return value;
    }

    public <T> T convertJsonToJava(String jsonString, Class<T> obj) {
        return null;
    }

    public String convertJavaToJson(Object object) {
        return null;
    }

    public String sendKeyClient(BigInteger modulus, BigInteger expo) {
        return null;
    }

    public String saveKeysDb(BigInteger modulus, BigInteger expo) {
        return null;
    }
}
