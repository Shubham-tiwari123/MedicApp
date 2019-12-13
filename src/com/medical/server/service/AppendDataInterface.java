package com.medical.server.service;

import com.medical.server.entity.SetKeys;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public interface AppendDataInterface {
    boolean verifyID(long patientID);
    String decryptData(ArrayList<byte[]> data, SetKeys getKeys); //verify sender
    boolean verifyData(String data) throws NoSuchAlgorithmException; //cal hash
    String getLastBlockHashDb(long patientID);
    String calCurrentBlockHash(String data) throws NoSuchAlgorithmException;
    String updateBlock(String lastBlockHash,String data) throws NoSuchAlgorithmException;
    ArrayList<byte[]> encryptBlock(String data);
    boolean appendBlockInChain(long patientId,String data,SetKeys keys);
}
