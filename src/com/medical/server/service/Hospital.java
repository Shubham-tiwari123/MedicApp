package com.medical.server.service;

import com.medical.server.dao.DatabaseHospital;
import com.medical.server.entity.HospitalDetails;
import com.medical.server.utils.VariableClass;

public class Hospital implements HospitalInterface {

    private DatabaseHospital databaseHospital = new DatabaseHospital();

    @Override
    public boolean checkUserName(String details) throws Exception{
        System.out.println("verify if username exists/not exists (file name):"+getClass());
        return databaseHospital.verifyUsername(details, VariableClass.REGISTER_HEALTH_CARE);
    }

    @Override
    public boolean saveHospitalDetails(HospitalDetails details) throws Exception{
        System.out.println("saving hospital details (file name):"+getClass());
        return databaseHospital.registerHospital(VariableClass.REGISTER_HEALTH_CARE,details);
    }

}
