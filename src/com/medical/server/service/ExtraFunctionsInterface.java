package com.medical.server.service;

import com.medical.server.entity.SetKeys;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public interface ExtraFunctionsInterface {
    String calculateHash(String data) throws NoSuchAlgorithmException;

    byte[] encryptData(String data, BigInteger modulus, BigInteger expo);

    String decryptData(byte[] data, BigInteger modulus, BigInteger expo);

    <T> T convertJsonToJava(String jsonString, Class<T> obj);

    String convertJavaToJson(Object object);

    SetKeys getServerKey();

    String convertEncryptedData(ArrayList<byte[]> data, SetKeys getKeys);
}
