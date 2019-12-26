package com.medical.client.dao;

import com.medical.client.entity.GetKeys;
import com.medical.client.entity.ServerKeys;
import com.medical.client.entity.SetKeys;
import com.medical.client.utils.VariableClass;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Database implements DatabaseInterface {

    private static MongoClient client;
    private static MongoDatabase database;
    private static MongoIterable<String> iterable;
    private static Set<String> colName;

    @Override
    public boolean createDbConn() throws Exception {
        client = new MongoClient(VariableClass.IP_ADDRESS, VariableClass.PORT_NUMBER);
        database = client.getDatabase(VariableClass.DATABASE_NAME);
        return true;
    }

    @Override
    public boolean checkCollection(String collectionName) throws Exception {
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
    public ServerKeys getServerKeys(String collectionName) throws Exception {
        ServerKeys keys = new ServerKeys();
        if (createDbConn()) {
            if (checkCollection(collectionName)) {
                MongoCollection<Document> collection = database.getCollection(collectionName);
                System.out.println("getting keys from db");
                List<Document> list = collection.find().into(new ArrayList<Document>());
                System.out.println("list:" + list.size());

                if (!list.isEmpty()) {
                    for (Document val : list) {
                        System.out.println("getting server keys if");
                        String publicKeyModules = val.getString("serverMod");
                        String publicKeyExpo = val.getString("serverExpo");

                        System.out.println("setting server keys");
                        keys.setPublicKeyExpo(new BigInteger(publicKeyExpo));
                        keys.setPublicKeyModules(new BigInteger(publicKeyModules));
                    }
                    return keys;
                } else {
                    return null;
                }

            }
        }
        return null;
    }

    @Override
    public boolean storeKeys(GetKeys keys, String collectionName, SetKeys setKeys) throws Exception {
        if (createDbConn()) {
            if (checkCollection(collectionName)) {
                System.out.println("saving client and server keys in db");
                Document document = new Document("serverExpo", keys.getExpoValue())
                        .append("serverMod", keys.getModulesValue())
                        .append("clientPubMod", setKeys.getPublicKeyModules().toString())
                        .append("clientPubExpo", setKeys.getPublicKeyExpo().toString())
                        .append("clientPriExpo", setKeys.getPrivateKeyExpo().toString())
                        .append("clientPriMod", setKeys.getPrivateKeyModules().toString());
                database.getCollection(collectionName).insertOne(document);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public SetKeys getClientKeys(String collectionName) throws Exception {
        SetKeys keys = new SetKeys();
        if (createDbConn()) {
            if (checkCollection(collectionName)) {
                MongoCollection<Document> collection = database.getCollection(collectionName);
                System.out.println("getting keys from db");
                List<Document> list = collection.find().into(new ArrayList<Document>());
                System.out.println("list:" + list.size());

                if (!list.isEmpty()) {
                    for (Document val : list) {
                        System.out.println("getting client keys if");
                        String privateKeyModules = val.getString("clientPriMod");
                        String privateKeyExpo = val.getString("clientPriExpo");

                        System.out.println("setting server keys");
                        keys.setPrivateKeyExpo(new BigInteger(privateKeyExpo));
                        keys.setPrivateKeyModules(new BigInteger(privateKeyModules));
                    }
                    return keys;
                }
                return null;
            }
            return null;
        }
        return null;
    }

}
