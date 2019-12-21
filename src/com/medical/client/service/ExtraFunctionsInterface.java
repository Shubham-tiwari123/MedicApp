package com.medical.client.service;

import com.medical.client.entity.SetKeys;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public interface ExtraFunctionsInterface {

    String calculateHash(String data) throws NoSuchAlgorithmException;

    byte[] encryptData(String data, BigInteger modulus, BigInteger expo);

    String decryptData(byte[] data, BigInteger modulus, BigInteger expo);

    <T> T convertJsonToJava(String jsonString, Class<T> obj);

    String convertJavaToJson(Object object);
}
