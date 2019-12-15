package com.medical.server.utils;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import java.util.ArrayList;

@JsonPropertyOrder(value = {
        "encryptedData"
} )
public class SerializeChain implements Serializable {
    ArrayList<ArrayList<byte[]>> encryptedData;

    public ArrayList<ArrayList<byte[]>> getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(ArrayList<ArrayList<byte[]>> encryptedData) {
        this.encryptedData = encryptedData;
    }
}
