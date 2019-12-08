package com.medical.server.service;

import com.medical.server.entity.SetKeys;
import java.math.BigInteger;
import java.util.List;

public interface ExtraFunctionsInterface {
    String calculateHash(String data);

    byte[] encryptData(String data, BigInteger modulus, BigInteger expo);

    String decryptData(byte[] data, BigInteger modulus, BigInteger expo);

    <T> T convertJsonToJava(String jsonString, Class<T> obj);

    String convertJavaToJson(Object object);

    public SetKeys getServerKeyFromFile();

    public List<String> convertEncryptedData(List<byte[]> data, SetKeys getKeys);

    void generateKey();

    String sendKeyClient(BigInteger modulus, BigInteger expo);

    String saveKeysDb(BigInteger modulus, BigInteger expo);
}
