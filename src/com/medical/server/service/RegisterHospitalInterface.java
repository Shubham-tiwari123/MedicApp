package com.medical.server.service;

import com.medical.server.entity.HospitalDetails;
import com.medical.server.entity.SetKeys;

import java.util.ArrayList;

public interface RegisterHospitalInterface {

    boolean checkUserName(String details);
    boolean saveHospitalDetails(HospitalDetails details);
    SetKeys generateKey();
    boolean saveServerKey(SetKeys keys);
    SetKeys getServerKeys();
    String decryptKey(ArrayList<byte[]> encryptedData, SetKeys keys);
    boolean saveClientKey(String pubMod,String pubExpo,String username);
}
