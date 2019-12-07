package com.medical.server.dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Database implements DatabaseInterface {

    private static MongoClient client;
    private static MongoDatabase database;
    private static MongoIterable<String> collection;
    private static Set<String> colName;

    public boolean createDbConn() {
        try {
            client = new MongoClient("127.0.0.1", 27017);
            database = client.getDatabase("medic_database");
            return true;
        } catch (Exception e) {
            System.out.println("exception:" + e);
            return false;
        }
    }

    public boolean checkCollection(String collectionName) {
            System.out.println("Checking if collection exists or not.....");
            collection = database.listCollectionNames();
            colName = new TreeSet<String>();
            for (String col : collection) {
                colName.add(col);
            }
            if (!colName.contains(collectionName)) {
                System.out.println("collection does not exists:");
                System.out.println("Creating new collection");
                database.createCollection(collectionName);
                return true;
            }
            System.out.println("exists");
            return true;
    }

    public boolean verifyPatientIdDB(int patientId,String collectionName) {
        if(createDbConn() && checkCollection(collectionName)){
            //Login to verify
            return true;
        }
        return false;
    }

    public boolean saveGenesisBlockDB(String collectionName, ArrayList<byte[]> data) {
        return false;
    }

    public boolean updateChain(String data) {
        return false;
    }

    public List getAllDataDB() {
        return null;
    }

    public List<String> getSpecificData(int patientID) {
        return null;
    }
}
