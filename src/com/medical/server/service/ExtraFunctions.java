package com.medical.server.service;

import java.math.BigInteger;

public class ExtraFunctions implements ExtraFunctionsInterface {

    public String calculateHash(String data) {
        return data;
    }

    public byte[] encryptData(String data, BigInteger modulus, BigInteger expo) {
        return new byte[0];
    }

    public String decryptData(byte[] data, BigInteger modulus, BigInteger expo) {
        return null;
    }

    public <T> T convertJsonToJava(String jsonString, Class<T> obj) {
        return null;
    }

    public String convertJavaToJson(Object object) {
        return null;
    }

    public String generateKey() {
        return null;
    }

    public String sendKeyClient(BigInteger modulus, BigInteger expo) {
        return null;
    }

    public String saveKeysDb(BigInteger modulus, BigInteger expo) {
        return null;
    }
}
