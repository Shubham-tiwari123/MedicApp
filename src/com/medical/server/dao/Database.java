package com.medical.server.dao;

import com.medical.server.entity.SetKeys;
import com.medical.server.entity.StoreServerKeys;
import com.medical.server.utils.VariableClass;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import java.math.BigInteger;
import java.util.*;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.Binary;

public class Database implements DatabaseInterface {

    private static MongoClient client;
    private static MongoDatabase database;
    private static MongoIterable<String> iterable;
    private static MongoCollection collection;
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
        System.out.println("collection exists");
        return true;
    }

    @Override
    public boolean verifyPatientIdDB(long patientId, String collectionName) throws Exception {
        if (createDbConn()) {
            if (checkCollection(collectionName)) {
                System.out.println("verifying id db...");
                collection = database.getCollection(collectionName);
                List<Document> user = (List<Document>) collection.find(new Document("patient_id",
                        patientId)).
                        into(new ArrayList<Document>());
                return user.size() == 0;
            }
        }
        return false;
    }

    @Override
    public boolean saveGenesisBlockDB(String collectionName, ArrayList<byte[]> data, long patientID)
            throws Exception {
        if (createDbConn()) {
            if (checkCollection(collectionName)) {
                System.out.println("saving genesis block db");
                Document document = new Document("patient_id", patientID)
                        .append("block", Arrays.asList(data));
                database.getCollection(collectionName).insertOne(document);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateChain(ArrayList<byte[]> data, long patientId, String collectionName)
            throws Exception {
        if (createDbConn()) {
            if (checkCollection(collectionName)) {
                System.out.println("appending block in chain db...");
                collection = database.getCollection(collectionName);
                Bson filter = Filters.eq("patient_id", patientId);
                UpdateResult result = collection.updateOne(filter,
                        Updates.addToSet("block", data));
                return result.getMatchedCount() == 1;
            }
        }
        return false;
    }

    @Override
    public SetKeys getServerKey(String collectionName) throws Exception {
        System.out.println("getting server keys db...");
        DatabaseHospital databaseHospital = new DatabaseHospital();
        return databaseHospital.getServerKeys(collectionName);
    }

    @Override
    public ArrayList<ArrayList<byte[]>> getSpecificData(long patientID, String collectionName)
            throws Exception {
        ArrayList<ArrayList<byte[]>> returnValue = new ArrayList<ArrayList<byte[]>>();
        if (createDbConn()) {
            if (checkCollection(collectionName)) {
                System.out.println("getting patient data db....");
                collection = database.getCollection(collectionName);
                List<Document> list = (List<Document>) collection.find(new Document("patient_id", patientID)).
                        into(new ArrayList<Document>());
                System.out.println("list:" + list);
                for (Document doc : list) {
                    ArrayList<ArrayList<Binary>> blocks = (ArrayList<ArrayList<Binary>>) doc.get("block");
                    System.out.println(blocks.size());
                    for (ArrayList<Binary> blockList : blocks) {
                        ArrayList<byte[]> subList = new ArrayList<byte[]>();
                        for (Binary blockPart : blockList) {
                            subList.add(blockPart.getData());
                        }
                        returnValue.add(subList);
                    }
                }
            }
        }
        System.out.println("size:" + returnValue.size());
        return returnValue;
    }

    @Override
    public SetKeys getClientKeys(String hospital, String collectionName) throws Exception {
        SetKeys keys = new SetKeys();
        if (createDbConn()) {
            if (checkCollection(collectionName)) {
                MongoCollection<Document> collection = database.getCollection(collectionName);
                System.out.println("getting client keys from db");
                List<Document> list = collection.find(new Document("userName", hospital)).into(new ArrayList<Document>());

                System.out.println("list:" + list.size());
                if (!list.isEmpty()) {
                    for (Document val : list) {
                        String publicKeyModules = val.getString("publicKeyModules");
                        String publicKeyExpo = val.getString("publicKeyExpo");

                        System.out.println("setting server keys");
                        keys.setPublicKeyModules(new BigInteger(publicKeyModules));
                        keys.setPublicKeyExpo(new BigInteger(publicKeyExpo));
                    }
                    return keys;
                }
                return null;
            }
            return null;
        }
        return null;
    }

    @Override
    public boolean getServerPrivateKeys(String collectionName) throws Exception {
        if (createDbConn()) {
            if (checkCollection(collectionName)) {
                MongoCollection<Document> collection = database.getCollection(collectionName);
                System.out.println("getting server private keys from db...");
                List<Document> list = collection.find(new Document("keys", "serverKeys"))
                        .into(new ArrayList<Document>());

                System.out.println("list:" + list.size());
                if (!list.isEmpty()) {
                    for (Document val : list) {
                        String publicKeyModules = val.getString("privateKeyModules");
                        String publicKeyExpo = val.getString("privateKeyExpo");

                        System.out.println("setting server keys");
                        StoreServerKeys.setPrivateKeyExpo(new BigInteger(publicKeyExpo));
                        StoreServerKeys.setPrivateKeyModules(new BigInteger(publicKeyModules));
                    }
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }
}
