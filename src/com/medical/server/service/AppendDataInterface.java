package com.medical.server.service;

import java.math.BigInteger;
import java.util.ArrayList;

public interface AppendDataInterface {
    boolean verifyID(int patientID);
    String decryptData(ArrayList<byte[]> data, BigInteger modulus, BigInteger expo); //verify sender
    boolean verifyData(String data); //cal hash
    String getLastBlockHashDb(int patientID);
    String calCurrentBlockHash(String data);
    String updateBlock(String lastBlockHash,String data);
    boolean appendBlockInChain(String data);
}
