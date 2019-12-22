package com.medical.client.service;

import com.medical.client.entity.ClientSideBlock;
import com.medical.client.entity.ClientSideBlockHash;
import com.medical.client.entity.ServerKeys;
import com.medical.client.entity.SetKeys;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public interface SendRecordInterface {

    String calBlockHash(String data) throws NoSuchAlgorithmException;
    String prepareBlock(ClientSideBlockHash block, String hashValue);
    ArrayList<byte[]> encryptBlock(String encryptString);
    ServerKeys getKeysFromDatabase();
}
