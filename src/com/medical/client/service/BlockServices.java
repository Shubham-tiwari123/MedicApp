package com.medical.client.service;

import com.medical.client.entity.BlockStructure;
import com.medical.client.entity.GenesisBlock;
import com.medical.client.utils.JSONUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Time;

public class BlockServices {

    public GenesisBlock createFirstBlock(int id) {
        GenesisBlock genesisBlock = new GenesisBlock();
        //System.out.println("Creating first block");
        try {
            genesisBlock.setId(id);
            genesisBlock.setCreationDate(Date.valueOf("2019-11-13"));
            genesisBlock.setCreationTime(Time.valueOf("23:06:42"));
            genesisBlock.setCompanyName("medic");
            genesisBlock.setPreviousBlockHash("shivamB48vishankC12divyaC14mehulC15");
            String value = JSONUtil.convertJavaToJson(genesisBlock);
            //System.out.println("Calculating hash of 1st block.....");
            String hashValue = calculateHash(value);
            genesisBlock.setCurrentBlockHash(hashValue);
        } catch (Exception e) {
            System.out.println(e);
        }
        return genesisBlock;
    }

    public BlockStructure createNewBlock(String lastBlockHash, int patientId) {
        BlockStructure newBlock = new BlockStructure();
        //System.out.println("creating new block");
        try {
            newBlock.setPatientId(patientId);
            newBlock.setDate(Date.valueOf("2019-11-13"));     //Use LocalDate.now() to save in mongodb
            newBlock.setTime(Time.valueOf("23:06:42"));       //Use LocalTime.now() to save in mongodb
            newBlock.setHospitalName("ABC");
            newBlock.setDoctorName("Hash");
            newBlock.setSpecialistType("Eye");
            newBlock.setPrescription("Number of characters words and sentences in a piece of text" +
                    "Top keywords used" +
                    "Readability score - how difficult is it to comprehend the passage." +
                    "Number of characters words and sentences in a piece of text" +
                    "Top keywords used" +
                    "Readability score - how difficult is it to comprehend the passage." +
                    "Top keywords");
            newBlock.setPrevBlockHash(lastBlockHash);
            String jsonString = JSONUtil.convertJavaToJson(newBlock);
            //System.out.println("Calculating hash.......");
            String hashValueOfBlock = calculateHash(jsonString);
            newBlock.setCurrentBlockHash(hashValueOfBlock);
        } catch (Exception e) {
            System.out.println(e);
        }
        return newBlock;
    }

    private String calculateHash(String value) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHashValue = digest.digest(value.getBytes("UTF-8"));
        StringBuilder hashValue = new StringBuilder(2 * encodedHashValue.length);
        for (byte b : encodedHashValue) {
            hashValue.append(b);
        }
        return hashValue.toString();
    }
}
