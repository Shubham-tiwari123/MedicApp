package com.medical.block;

import com.medical.util.JSONUtil;
import org.bson.types.Binary;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedList;

public class BlockFunction {
    private static LinkedList<Object> chain = new LinkedList<>();
    private static GenesisBlock genesisBlock;

    public LinkedList<Object> getChain() {
        return chain;
    }

    public void setChain(LinkedList<Object> chain) {
        BlockFunction.chain = chain;
    }

    public GenesisBlock createFirstBlock() {
        genesisBlock = new GenesisBlock();
        //System.out.println("Creating first block");
        try {
            genesisBlock.setId(12345);
            genesisBlock.setCreationDate(Date.valueOf("2019-11-13"));
            genesisBlock.setCreationTime(Time.valueOf("23:06:42"));
            genesisBlock.setCompanyName("medics");
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

    public BlockStructure createNewBlock() {
        BlockStructure newBlock = new BlockStructure();
        //System.out.println("creating new block");
        try {
            newBlock.setPatientId(123);
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
            newBlock.setPrevBlockHash(lastBlockHash());
            String jsonString = JSONUtil.convertJavaToJson(newBlock);
            //System.out.println("Calculating hash.......");
            String hashValueOfBlock = calculateHash(jsonString);
            newBlock.setCurrentBlockHash(hashValueOfBlock);
        } catch (Exception e) {
            System.out.println(e);
        }
        return newBlock;
    }

    private String lastBlockHash() {
        String prevHashVal;
        if (chain.size() == 1) {
            genesisBlock = (GenesisBlock) chain.get(0);
            prevHashVal = genesisBlock.getCurrentBlockHash();
        } else {
            BlockStructure block = (BlockStructure) chain.get(chain.size() - 1);
            prevHashVal = block.getCurrentBlockHash();
        }
        return prevHashVal;
    }

    public static void createChain(Object object) {
        chain.add(object);
    }

    private String calculateHash(String value) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHashValue = digest.digest(value.getBytes(StandardCharsets.UTF_8));
        StringBuilder hashValue = new StringBuilder(2 * encodedHashValue.length);
        for (byte b : encodedHashValue) {
            hashValue.append(b);
        }
        return hashValue.toString();
    }

    public void printBlock() {
        System.out.println("list:" + chain);
        for (int i = 0; i < chain.size(); i++) {
            if (i == 0) {
                genesisBlock = (GenesisBlock) chain.get(0);
                StringBuilder builder = new StringBuilder(JSONUtil.convertJavaToJson(genesisBlock));
                builder.deleteCharAt(builder.length() - 1);
                builder.append(",\"currentBlockHash\":\"" + genesisBlock.getCurrentBlockHash() + "\"}");
                System.out.println(builder.toString());
            } else {
                BlockStructure getBlock = (BlockStructure) chain.get(i);
                StringBuilder builder = new StringBuilder(JSONUtil.convertJavaToJson(getBlock));
                builder.deleteCharAt(builder.length() - 1);
                builder.append(",\"currentBlockHash\":\"" + getBlock.getCurrentBlockHash() + "\"}");
                System.out.println(builder.toString());
            }
        }
    }

    public ArrayList<byte[]> encryptBlock(Object object) {
        StringBuilder builder;
        int count = 0;
        int start = 0, end = 0;
        String substring;
        ArrayList<String> storeSubString = new ArrayList<>();
        ArrayList<byte[]> storeEncryptedValue = new ArrayList<>();

        if (object.getClass().getSimpleName().equals("GenesisBlock")) {
            genesisBlock = (GenesisBlock) object;
            builder = new StringBuilder(JSONUtil.convertJavaToJson(genesisBlock));
            builder.deleteCharAt(builder.length() - 1);
            builder.append(",\"currentBlockHash\":\"" + genesisBlock.getCurrentBlockHash() + "\"}");
        } else {
            BlockStructure getBlock = (BlockStructure) object;
            builder = new StringBuilder(JSONUtil.convertJavaToJson(getBlock));
            builder.deleteCharAt(builder.length() - 1);
            builder.append(",\"currentBlockHash\":\"" + getBlock.getCurrentBlockHash() + "\"}");
        }

        String value = builder.toString();
        System.out.println("Original value" + value);
        RSAEncryptDecrypt.generateKey();

        while (count <= value.length()) {
            if (value.length() - end > 250) {
                end = start + 251;
                substring = value.substring(start, end);
                storeSubString.add(substring);
                start = start + 251;
            } else {
                start = end;
                end = value.length();
                substring = value.substring(start, end);
                storeSubString.add(substring);
            }
            count = count + 250;
        }
        count = 0;
        while (count != storeSubString.size()) {
            byte[] encryptedData = RSAEncryptDecrypt.encryptData(storeSubString.get(count),
                    SetKeys.getPublicKeyModules(), SetKeys.getPublicKeyExpo());
            storeEncryptedValue.add(encryptedData);
            count++;
        }
        /*count = 0;
        StringBuilder build = new StringBuilder();
        while (count != storeEncryptedValue.size()) {
            substring = RSAEncryptDecrypt.decryptData(storeEncryptedValue.get(count),
                    SetKeys.getPrivateKeyModules(), SetKeys.getPrivateKeyExpo());
            build.append(substring);
            count++;
        }
        System.out.println(build.toString());
        if (value.equals(build.toString()))
            System.out.println("equal");
        else
            System.out.println("not equal");*/
        return storeEncryptedValue;
    }
}
