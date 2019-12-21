package com.medical.client.dao;

import com.medical.client.entity.GetKeys;
import com.medical.client.entity.SetKeys;
import com.medical.client.utils.VariableClass;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.Binary;

import java.util.*;

public class Database implements DatabaseInterface {

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
    public SetKeys getKeys() {
        return null;
    }

    @Override
    public boolean storeKeys(GetKeys keys,String collectionName,SetKeys setKeys) {
        if(createDbConn()){
            if(checkCollection(collectionName)){
                Document document = new Document("serverExpo",keys.getExpoValue())
                        .append("serverMod",keys.getModulesValue())
                        .append("clientPubMod", setKeys.getPublicKeyModules().toString())
                        .append("clientPubExpo", setKeys.getPublicKeyExpo().toString())
                        .append("clientPriExpo",setKeys.getPrivateKeyExpo().toString())
                        .append("clientPriMod",setKeys.getPrivateKeyModules());
                database.getCollection(collectionName).insertOne(document);
                return true;
            }
        }
        return false;
    }

}
