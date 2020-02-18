package com.medical.server.service;

import com.medical.server.entity.ServerKeys;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public interface ExtraFunctionsInterface {

    String calculateHash(String data) throws NoSuchAlgorithmException;

    byte[] encryptData(String data, BigInteger modulus, BigInteger expo) throws Exception;

    String decryptData(byte[] data, BigInteger modulus, BigInteger expo) throws Exception;

    <T> T convertJsonToJava(String jsonString, Class<T> obj) throws Exception;

    String convertJavaToJson(Object object) throws Exception;

    String convertEncryptedData(ArrayList<byte[]> data, ServerKeys serverKeys) throws Exception;
}
