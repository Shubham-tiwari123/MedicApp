package com.medical.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medical.server.entity.ClientKeys;
import com.medical.server.entity.ServerKeys;
import com.medical.server.utils.VariableClass;

import javax.crypto.Cipher;
import java.io.IOException;
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
    public byte[] encryptData(String data, BigInteger modulus, BigInteger expo) throws Exception{
        byte[] dataToEncrypt = data.getBytes(StandardCharsets.UTF_8);
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
    public String decryptData(byte[] data, BigInteger modulus, BigInteger expo) throws Exception{
        byte[] decryptedData;
        String value = null;
            RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(modulus, expo);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = factory.generatePrivate(rsaPrivateKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            decryptedData = cipher.doFinal(data);
            value = new String(decryptedData,StandardCharsets.UTF_8);
        return value;
    }

    @Override
    public String convertEncryptedData(ArrayList<byte[]> data, ServerKeys serverKeys) throws Exception{
        StringBuilder builder = new StringBuilder();
        for (byte[] byteValue : data) {
            String val = decryptData(byteValue, serverKeys.getPublicKeyModules(),
                    serverKeys.getPublicKeyExpo());
            builder.append(val);
        }
        return builder.toString();
    }

    @Override
    public <T> T convertJsonToJava(String jsonString, Class<T> obj) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        T result=null;
        try {
            result = mapper.readValue(jsonString,obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String convertJavaToJson(Object object) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = null;
        try {
            jsonResult = mapper.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    // extra function
    public ArrayList<byte[]> encryptBlock(String data) throws Exception {

        ClientKeys clientKeys = new ClientKeys();
        clientKeys.setClientPubKeyExpo(VariableClass.clientPriExpo);
        clientKeys.setClientPubKeyMod(VariableClass.clientPriMod);
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
            byte[] encryptedData = encryptData(storeSubString.get(count),
                    clientKeys.getClientPubKeyMod(), clientKeys.getClientPubKeyExpo());
            storeEncryptedValue.add(encryptedData);
            count++;
        }
        return storeEncryptedValue;
    }
}
