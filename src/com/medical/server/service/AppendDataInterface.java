package com.medical.server.service;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public interface AppendDataInterface {

    boolean verifyID(long patientID) throws Exception;

    String decryptData(ArrayList<byte[]> data) throws Exception; //verify sender

    boolean verifyData(String data) throws Exception; //cal hash

    String getLastBlockHashDb(long patientID) throws Exception;

    String calCurrentBlockHash(String data) throws Exception;

    String updateBlock(String lastBlockHash, String data) throws Exception;

    ArrayList<byte[]> encryptBlock(String data) throws Exception;

    boolean getServerKeys() throws Exception;

    boolean appendBlockInChain(long patientId, String data) throws Exception;
}
