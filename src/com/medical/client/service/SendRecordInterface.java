package com.medical.client.service;

import com.medical.client.entity.ClientKeys;
import com.medical.client.entity.MedicBlock;
import com.medical.client.entity.ServerKeys;
import java.util.ArrayList;

public interface SendRecordInterface {

    String calBlockHash(String data) throws Exception;

    String prepareBlock(MedicBlock block) throws Exception;

    ArrayList<byte[]> encryptBlock(String encryptString) throws Exception;

    ClientKeys getKeysFromDatabase() throws Exception;
}
