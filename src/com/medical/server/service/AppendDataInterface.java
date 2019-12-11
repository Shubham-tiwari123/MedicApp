package com.medical.server.service;

import com.medical.server.entity.SetKeys;

import java.math.BigInteger;
import java.util.ArrayList;

public interface AppendDataInterface {
    boolean verifyID(int patientID);
    String decryptData(ArrayList<byte[]> data, SetKeys getKeys); //verify sender
    boolean verifyData(String data); //cal hash
    String getLastBlockHashDb(int patientID);
    String calCurrentBlockHash(String data);
    String updateBlock(String lastBlockHash,String data);
    boolean appendBlockInChain(int patientId,String data,SetKeys keys);
}
