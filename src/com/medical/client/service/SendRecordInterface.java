package com.medical.client.service;

import com.medical.client.entity.ClientSideBlockHash;
import com.medical.client.entity.ServerKeys;
import java.util.ArrayList;

public interface SendRecordInterface {

    String calBlockHash(String data) throws Exception;

    String prepareBlock(ClientSideBlockHash block, String hashValue) throws Exception;

    ArrayList<byte[]> encryptBlock(String encryptString) throws Exception;

    ServerKeys getKeysFromDatabase() throws Exception;
}
