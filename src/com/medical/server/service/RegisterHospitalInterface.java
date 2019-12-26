package com.medical.server.service;

import com.medical.server.entity.HospitalDetails;
import com.medical.server.entity.SetKeys;

import java.util.ArrayList;

public interface RegisterHospitalInterface {

    boolean checkUserName(String details) throws Exception;

    boolean saveHospitalDetails(HospitalDetails details) throws Exception;

    SetKeys generateKey() throws Exception;

    boolean saveServerKey(SetKeys keys) throws Exception;

    SetKeys getServerKeys() throws Exception;

    String decryptKey(ArrayList<byte[]> encryptedData, SetKeys keys) throws Exception;

    boolean saveClientKey(String pubMod, String pubExpo, String username) throws Exception;
}
