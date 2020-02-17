package com.medical.server.service;

import com.medical.server.entity.HospitalDetails;

public interface HospitalInterface {

    boolean checkUserName(String details) throws Exception;

    boolean saveHospitalDetails(HospitalDetails details) throws Exception;

}
