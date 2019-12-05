package com.medical.server.service;

import com.medical.server.entity.ClientBlock;

import java.math.BigInteger;
import java.util.List;

public interface AppendDataInterface {
    boolean verifyID(int patientID);
    String decryptData(List<byte[]> data, BigInteger modulus, BigInteger expo); //verify sender
    boolean verifyData(String data); //cal hash
    String getLastBlockHashDb(int patientID);
    String calCurrentBlockHash(String data);
    String updateBlock(String lastBlockHash);
    boolean appendBlockInChain(String data);
}
