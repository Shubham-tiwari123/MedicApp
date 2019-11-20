package com.medical;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Time;
import java.util.LinkedList;

public class Main {
    static LinkedList<Object> chain = new LinkedList<>();
    private static GenesisBlock genesisBlock;

    static void convertJsonToJava(String value){
        Block block = new Block();
        Block result = JSONUtil.convertJsonToJava(value,Block.class);
        System.out.println("\nPrinting values:");
        System.out.println(result.getPatientId()+","+result.getDate()+","+result.getTime()+","+
                result.getHospitalName()+","+result.getDoctorName()+","+result.getSpecialistType()+","+
                result.getPrescription());
    }

    public GenesisBlock createFirstBlock() {
        genesisBlock = new GenesisBlock();
        System.out.println("Creating first block");
        try {
            genesisBlock.setId(12345);
            genesisBlock.setCreationDate(Date.valueOf("2019-11-13"));
            genesisBlock.setCreationTime(Time.valueOf("23:06:42"));
            genesisBlock.setCompanyName("medics");
            genesisBlock.setPreviousBlockHash("shivamB48vishankC12divyaC14mehulC15");
            String value = JSONUtil.convertJavaToJson(genesisBlock);
            System.out.println("Calculating hash of 1st block.....");
            String hashValue = calculateHash(value);
            genesisBlock.setCurrentBlockHash(hashValue);
        }catch (Exception e){
            System.out.println(e);
        }
        return genesisBlock;
    }

    static String calculateHash(String value) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHashValue = digest.digest(value.getBytes(StandardCharsets.UTF_8));
        StringBuilder hashValue = new StringBuilder(2*encodedHashValue.length);
        for(byte b: encodedHashValue){
            hashValue.append(b);
        }
        return hashValue.toString();
    }

    public Block createBlock(){
        Block createNewBlock = new Block();
        System.out.println("creating new block");
        try {
            createNewBlock.setPatientId(123);
            createNewBlock.setDate(Date.valueOf("2019-11-13"));     //Use LocalDate.now() to save in mongodb
            createNewBlock.setTime(Time.valueOf("23:06:42"));       //Use LocalTime.now() to save in mongodb
            createNewBlock.setHospitalName("ABC");
            createNewBlock.setDoctorName("Hash");
            createNewBlock.setSpecialistType("Eye");
            createNewBlock.setPrescription("jwbjsjwufbwjjfbbkwnuifbniubwkcnbuAJBFCUFFBEJFAJAFAFB");
            createNewBlock.setPrevBlockHash(lastBlockHash());
            String jsonString = JSONUtil.convertJavaToJson(createNewBlock);
            System.out.println("Calculating hash.......");
            String hashValueOfBlock = calculateHash(jsonString);
            createNewBlock.setCurrentBlockHash(hashValueOfBlock);
        } catch (Exception e) {
            System.out.println(e);
        }
        return createNewBlock;
    }

    public String lastBlockHash(){
        String prevHashVal;
        if(chain.size()==1){
            genesisBlock = (GenesisBlock) chain.get(0);
            prevHashVal = genesisBlock.getCurrentBlockHash();
        }else{
            Block createNewBlock = (Block) chain.get(chain.size()-1);
            prevHashVal = createNewBlock.getCurrentBlockHash();
        }
        return prevHashVal;
    }

    public static void createChain(Object object){
        chain.add(object);
    }

    public static void main(String[] args) {
        System.out.println("Welcome to medic");
        Main main = new Main();
        for(int i=0;i<4;i++) {
            if (chain.isEmpty()) {
                createChain(main.createFirstBlock());
            } else {
                createChain(main.createBlock());
            }
        }
        System.out.println("list:"+chain);
        /*for (int i=0;i<chain.size();i++){
            if(i==0){
                genesisBlock = (GenesisBlock) chain.get(0);
                StringBuilder builder = new StringBuilder(JSONUtil.convertJavaToJson(genesisBlock));
                builder.deleteCharAt(builder.length()-1);
                builder.append(",\"currentBlockHash\":\""+genesisBlock.getCurrentBlockHash()+"\"}");
                System.out.println(builder.toString());
            }
            else{
                Block getBlock = (Block) chain.get(i);
                StringBuilder builder = new StringBuilder(JSONUtil.convertJavaToJson(getBlock));
                builder.deleteCharAt(builder.length()-1);
                builder.append(",\"currentBlockHash\":\""+getBlock.getCurrentBlockHash()+"\"}");
                System.out.println(builder.toString());
            }
        }*/
        /*String value =JSONUtil.convertJavaToJson(chain.get(0));
        System.out.println("original value:\n"+value);*/
        RSAEncryptDecrypt.generateKey();
        /*byte[] encryptedData = RSAEncryptDecrypt.encryptData(value,SetKeys.getPublicKeyModules(),
                SetKeys.getPublicKeyExpo());
        System.out.println("e:"+encryptedData);
        //Test for badPadding
        //BigInteger bigInteger = SetKeys.getPrivateKeyModules();
        //BigInteger bigInteger1 = bigInteger.add(BigInteger.valueOf(100));
        RSAEncryptDecrypt.decryptData(encryptedData,SetKeys.getPrivateKeyModules(),
                SetKeys.getPrivateKeyExpo());*/
    }
}
