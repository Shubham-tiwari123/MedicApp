package com.medical.server.service;

import java.math.BigInteger;
import java.util.List;

public interface AppendDataInterface {
    boolean verifyID(int patientID);
    String decryptData(List<byte[]> data, BigInteger modulus, BigInteger expo); //verify sender
    boolean verifyData(String data); //cal hash
    String getLastBlockHashDb(int patientID);
    String insertLastBlockHash(String data,String lastBlockHash);
    String calCurrentBlockHash(String data);
    String updateCurrentBlockHash(String currBlockHash);
    boolean appendBlockInChain(String data);
}
