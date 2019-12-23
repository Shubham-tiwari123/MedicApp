package com.medical.client.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class DeserializeChain implements Serializable {
    ArrayList<ArrayList<byte[]>> encryptedData;

    public ArrayList<ArrayList<byte[]>> getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(ArrayList<ArrayList<byte[]>> encryptedData) {
        this.encryptedData = encryptedData;
    }
}
