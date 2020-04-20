package com.medical.server.service;

import com.medical.server.entity.HospitalDetails;

import java.util.List;

public interface HospitalInterface {

    boolean checkUserName(String details) throws Exception;

    boolean saveHospitalDetails(HospitalDetails details) throws Exception;

    boolean loginHospital(String userName,String password) throws Exception;

    List<String> getAllHospitals() throws Exception;

    HospitalDetails getHospitalDetails(String email) throws Exception;

}
