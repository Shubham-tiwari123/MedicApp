package com.medical.server.dao;

import com.medical.server.entity.HospitalDetails;
import com.medical.server.entity.SetKeys;
import com.medical.server.utils.VariableClass;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.*;

public class DatabaseHospital implements DatabaseHospitalInterface{

    private static MongoClient client;
    private static MongoDatabase database;
    private static MongoIterable<String> iterable;
    private static MongoCollection collection;
    private static Set<String> colName;

    @Override
    public boolean createDbConn() {
        try {
            client = new MongoClient(VariableClass.IP_ADDRESS, VariableClass.PORT_NUMBER);
            database = client.getDatabase(VariableClass.DATABASE_NAME);
            return true;
        } catch (Exception e) {
            System.out.println("exception in: "+getClass()+"  "+ e);
            return false;
        }
    }

    @Override
    public boolean checkCollection(String collectionName) {
        System.out.println("Checking if collection exists or not.....");
        iterable = database.listCollectionNames();
        colName = new TreeSet<String>();
        for (String col : iterable) {
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

    @Override
    public boolean verifyUsername(String userName,String collectionName) {
        if(createDbConn()){
            if(checkCollection(collectionName)){
                System.out.println("verifying");
                collection = database.getCollection(collectionName);
                List<Document> user = (List<Document>) collection.find(new Document("userName", userName)).
                        into(new ArrayList<Document>());
                System.out.println("User present:"+user.size());
                return user.size() == 0;
            }
        }
        return false;
    }

    @Override
    public boolean registerHospital(String collectionName, HospitalDetails details) {
        if(createDbConn()){
            if(checkCollection(collectionName)){
                System.out.println("registering hospital");
                Document document = new Document("userName",details.getUserName())
                        .append("password", details.getPassword())
                        .append("hospitalName",details.getHospitalName())
                        .append("hospitalAddress",details.getHospitalAddress())
                        .append("state",details.getState())
                        .append("city",details.getCity());
                database.getCollection(collectionName).insertOne(document);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean storeClientKeys(SetKeys keys, String collectionName, String userName){
        if(createDbConn()){
            if(checkCollection(collectionName)){
                System.out.println("Storing keys");
                Document document = new Document("userName", userName)
                        .append("publicKeyModules", keys.getPublicKeyModules().toString())
                        .append("publicKeyExpo", keys.getPublicKeyExpo().toString())
                        .append("privateKeyModules", keys.getPrivateKeyModules().toString())
                        .append("privateKeyExpo", keys.getPrivateKeyExpo().toString());
                try {
                    database.getCollection(collectionName).insertOne(document);
                    return true;
                }catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean deleteHospital(String userName, String collectionName) {
        if(createDbConn()){
            if(checkCollection(collectionName)){
                System.out.println("deleting database");
                collection = database.getCollection(collectionName);
                DeleteResult result = collection.deleteOne(new Document("userName",userName));
                if(result.getDeletedCount()==0)
                    return false;
                else
                    return true;
            }
        }
        return false;
    }
}
