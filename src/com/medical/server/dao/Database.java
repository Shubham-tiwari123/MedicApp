package com.medical.server.dao;

import com.medical.server.entity.SetKeys;
import com.medical.server.utils.VariableClass;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

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
        try{
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
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean verifyPatientIdDB(long patientId,String collectionName) {
        if(createDbConn()){
            if(checkCollection(collectionName)){
                try {
                    System.out.println("checking in database");
                    collection = database.getCollection(collectionName);
                    List<Document> user = (List<Document>) collection.find(new Document("patient_id", patientId)).
                            into(new ArrayList<Document>());
                    System.out.println("List size:" + user.size());
                    return user.size() == 0;
                }catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean saveGenesisBlockDB(String collectionName, ArrayList<byte[]> data, long patientID) {
        if(createDbConn()){
            if(checkCollection(collectionName)){
                try {
                    Document document = new Document("patient_id", patientID)
                            .append("block", Arrays.asList(data));
                    database.getCollection(collectionName).insertOne(document);
                    return true;
                }catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean updateChain(ArrayList<byte[]> data, long patientId,String collectionName) {
        if(createDbConn()){
            if(checkCollection(collectionName)){
                try {
                    collection = database.getCollection(collectionName);
                    Bson filter = Filters.eq("patient_id", patientId);
                    UpdateResult result = collection.updateOne(filter,
                            Updates.addToSet("block", data));
                    return result.getMatchedCount() == 1;
                }catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public SetKeys getServerKey(String collectionName) {
        try {
            DatabaseHospital databaseHospital = new DatabaseHospital();
            return databaseHospital.getServerKeys(collectionName);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<ArrayList<byte[]>>  getSpecificData(long patientID,String collectionName) {
        try {
            ArrayList<ArrayList<byte[]>> returnValue = new ArrayList<ArrayList<byte[]>>();
            if (createDbConn()) {
                if (checkCollection(collectionName)) {
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
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
