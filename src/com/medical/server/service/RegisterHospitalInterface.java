package com.medical.server.service;

import com.medical.server.entity.HospitalDetails;
import com.medical.server.entity.SetKeys;

public interface RegisterHospitalInterface {

    boolean checkUserName(HospitalDetails details);
    boolean saveHospitalDetails(HospitalDetails details);
    SetKeys generateKey();
    boolean saveServerKey(SetKeys keys);
    public SetKeys getServerKeys();
    boolean saveClientKey(String pubMod,String pubExpo,String username);
}
