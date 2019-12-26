package com.medical.client.service;

import com.medical.client.entity.GetKeys;
import com.medical.client.entity.SetKeys;

import java.util.ArrayList;

public interface RegisterUserInterface {

    SetKeys generateKeys() throws Exception;

    boolean storeKeys(GetKeys keys, SetKeys setKeys) throws Exception;

    boolean verifyServerKey(GetKeys keys) throws Exception;

    ArrayList<byte[]> encryptKey(GetKeys keys, String encryptKey) throws Exception;
}
