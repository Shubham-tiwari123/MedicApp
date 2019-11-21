package com.medical;

import com.medical.block.BlockFunction;
import com.medical.block.BlockStructure;
import com.medical.block.GenesisBlock;
import com.medical.util.JSONUtil;
import com.medical.block.RSAEncryptDecrypt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Time;
import java.util.LinkedList;

public class Main {

    static void convertJsonToJava(String value){
        BlockStructure blockStructure = new BlockStructure();
        BlockStructure result = JSONUtil.convertJsonToJava(value, BlockStructure.class);
        System.out.println("\nPrinting values:");
        System.out.println(result.getPatientId()+","+result.getDate()+","+result.getTime()+","+
                result.getHospitalName()+","+result.getDoctorName()+","+result.getSpecialistType()+","+
                result.getPrescription());
    }

    public static void main(String[] args) {
        BlockFunction blockFunction = new BlockFunction();
        System.out.println("Welcome to medic");
        Main main = new Main();
        for(int i=0;i<4;i++) {
            if (blockFunction.getChain().isEmpty()) {
                BlockFunction.createChain(blockFunction.createFirstBlock());
            } else {
                BlockFunction.createChain(blockFunction.createNewBlock());
            }
        }
        //blockFunction.printBlock();
        int i=0;
        //while (i!=blockFunction.getChain().size()){
            Object block = blockFunction.getChain().get(2);
            blockFunction.encryptBlock(block);
            i++;
        //}
        /*String value =JSONUtil.convertJavaToJson(chain.get(0));
        System.out.println("original value:\n"+value);
        RSAEncryptDecrypt.generateKey();
        byte[] encryptedData = RSAEncryptDecrypt.encryptData(value,SetKeys.getPublicKeyModules(),
                SetKeys.getPublicKeyExpo());
        System.out.println("e:"+encryptedData);
        //Test for badPadding
        //BigInteger bigInteger = SetKeys.getPrivateKeyModules();
        //BigInteger bigInteger1 = bigInteger.add(BigInteger.valueOf(100));
        RSAEncryptDecrypt.decryptData(encryptedData,SetKeys.getPrivateKeyModules(),
                SetKeys.getPrivateKeyExpo());*/
    }
}
