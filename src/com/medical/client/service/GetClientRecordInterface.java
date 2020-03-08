package com.medical.client.service;

import com.medical.client.entity.ClientKeys;
import com.medical.client.entity.MedicBlock;

import java.util.ArrayList;
import java.util.List;

public interface GetClientRecordInterface {
    ClientKeys getKeysFromDatabase() throws Exception;
    List medicBlocks(ArrayList<ArrayList<byte[]>> encryptedData,ClientKeys clientKeys) throws Exception;
    boolean verifyChain(List medicBlocks) throws Exception;
}
