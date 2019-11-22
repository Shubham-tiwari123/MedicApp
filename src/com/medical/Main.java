package com.medical;

import com.medical.block.BlockFunction;
import com.medical.block.BlockStructure;
import com.medical.database.ConnectDB;
import com.medical.util.JSONUtil;
import org.bson.types.Binary;

import java.util.ArrayList;

public class Main {

    static void convertJsonToJava(String value) {
        BlockStructure blockStructure = new BlockStructure();
        BlockStructure result = JSONUtil.convertJsonToJava(value, BlockStructure.class);
        System.out.println("\nPrinting values:");
        System.out.println(result.getPatientId() + "," + result.getDate() + "," + result.getTime() + "," +
                result.getHospitalName() + "," + result.getDoctorName() + "," + result.getSpecialistType() + "," +
                result.getPrescription());
    }
    /*
    Start monogoDb server : Desktop/software/mongodb/bin/mongod --dbpath Desktop/software/mongodb-data
     */
    public static void main(String[] args) {
        BlockFunction blockFunction = new BlockFunction();
        ConnectDB connectDB = new ConnectDB();
        System.out.println("Welcome to medic");
        for (int i = 0; i < 4; i++) {
            if (blockFunction.getChain().isEmpty()) {
                BlockFunction.createChain(blockFunction.createFirstBlock());
            } else {
                BlockFunction.createChain(blockFunction.createNewBlock());
            }
        }
        int i = 0;
        ArrayList<byte[]> storeEncryptedValue = new ArrayList<>();
        //while (i != blockFunction.getChain().size()) {
            Object block = blockFunction.getChain().get(0);
            storeEncryptedValue=blockFunction.encryptBlock(block);
            i++;
        //}
        System.out.println("actual:-"+storeEncryptedValue.get(0)+"  "+storeEncryptedValue.get(1));
        if (connectDB.connectToMongo()) {
            if (!connectDB.checkCollection("storeData")) {
                connectDB.createCollection("storeData");
            }
            else {
                System.out.println("collection already exists");
                connectDB.insertData(12345,"2019-11-13",
                        storeEncryptedValue,"storeData",blockFunction.getChain().get(1));
                connectDB.getData(storeEncryptedValue);
            }
        }

    }
}
