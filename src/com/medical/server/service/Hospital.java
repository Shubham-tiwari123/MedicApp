package com.medical.server.service;

import com.medical.server.dao.Database;
import com.medical.server.entity.HospitalDetails;
import com.medical.server.utils.VariableClass;

import java.util.List;

public class Hospital implements HospitalInterface {

    private final Database database = new Database();

    @Override
    public boolean checkUserName(String userName) throws Exception{
        System.out.println("Checking user name:"+getClass());
        return database.verifyHospital(userName, VariableClass.REGISTER_HEALTH_CARE);
    }

    @Override
    public boolean saveHospitalDetails(HospitalDetails details) throws Exception{
        System.out.println("saving hospital details (file name):"+getClass());
        return database.registerHospital(VariableClass.REGISTER_HEALTH_CARE,details);
    }

    @Override
    public boolean loginHospital(String userName, String password) throws Exception {
        return database.checkLoginCredentials(userName,password,VariableClass.REGISTER_HEALTH_CARE);
    }

    @Override
    public List<String> getAllHospitals() throws Exception {
        return database.getAllHospitals(VariableClass.REGISTER_HEALTH_CARE);
    }

    @Override
    public HospitalDetails getHospitalDetails(String email) throws Exception {
        return database.getHospitalDetails(email,VariableClass.REGISTER_HEALTH_CARE);
    }

    @Override
    public boolean activateHospital(String email) throws Exception {
        return database.activateHospital(email,VariableClass.REGISTER_HEALTH_CARE);
    }

    @Override
    public boolean deactivateHospital(String email) throws Exception {
        return database.deactivateHospital(email,VariableClass.REGISTER_HEALTH_CARE);
    }

}
