package com.medical.server.service;

import java.math.BigInteger;

public interface ExtraFunctionsInterface {
    String calculateHash(String data);
    byte[] encryptData(String data, BigInteger modulus, BigInteger expo);
    String decryptData(byte[] data, BigInteger modulus,BigInteger expo);
    <T> T convertJsonToJava(String jsonString, Class<T> obj);
    String convertJavaToJson(Object object);
    String generateKey();
    String sendKeyClient(BigInteger modulus, BigInteger expo);
    String saveKeysDb(BigInteger modulus, BigInteger expo);
}
