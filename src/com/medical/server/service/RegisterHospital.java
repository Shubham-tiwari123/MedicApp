package com.medical.server.service;

import com.medical.server.dao.DatabaseHospital;
import com.medical.server.entity.HospitalDetails;
import com.medical.server.entity.SetKeys;
import com.medical.server.utils.VariableClass;

import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class RegisterHospital implements RegisterHospitalInterface{

    DatabaseHospital databaseHospital = new DatabaseHospital();

    @Override
    public boolean checkUserName(HospitalDetails details) {
        return databaseHospital.verifyUsername(details.getUserName(), VariableClass.REGISTER_HEALTH_CARE);
    }

    @Override
    public boolean saveHospitalDetails(HospitalDetails details) {
        return databaseHospital.registerHospital(VariableClass.REGISTER_HEALTH_CARE,details);
    }

    @Override
    public SetKeys generateKey() {
        System.out.println("generating keys");
        SetKeys setKeys = new SetKeys();
        try {
            KeyPairGenerator keyPair = KeyPairGenerator.getInstance("RSA");
            keyPair.initialize(3072);//3072
            KeyPair pair = keyPair.generateKeyPair();
            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();
            // Generating key-pair
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec rsaPublicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
            RSAPrivateKeySpec rsaPrivateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
            //Setting public keys
            setKeys.setPublicKeyExpo(rsaPublicKeySpec.getPublicExponent());
            setKeys.setPublicKeyModules(rsaPublicKeySpec.getModulus());
            //Setting private keys
            setKeys.setPrivateKeyExpo(rsaPrivateKeySpec.getPrivateExponent());
            setKeys.setPrivateKeyModules(rsaPrivateKeySpec.getModulus());
        } catch (Exception e) {
            System.out.println(e);
        }
        return setKeys;
    }

    @Override
    public boolean saveServerKey(SetKeys keys){
        return databaseHospital.storeServerKey(keys,VariableClass.STORE_KEYS);
    }

    @Override
    public SetKeys getServerKeys(){
        return databaseHospital.getServerKeys(VariableClass.STORE_KEYS);
    }

    @Override
    public boolean saveClientKey(String pubMod,String pubExpo,String username){
        return false;
    }
}
