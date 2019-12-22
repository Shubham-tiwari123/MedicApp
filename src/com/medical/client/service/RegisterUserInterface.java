package com.medical.client.service;

import com.medical.client.entity.GetKeys;
import com.medical.client.entity.HospitalDetails;
import com.medical.client.entity.SetKeys;

import java.util.ArrayList;

public interface RegisterUserInterface {

    SetKeys generateKeys();
    boolean storeKeys(GetKeys keys,SetKeys setKeys);
    boolean verifyServerKey(GetKeys keys);
    ArrayList<byte[]> encryptKey(GetKeys keys, String encryptKey);
}
