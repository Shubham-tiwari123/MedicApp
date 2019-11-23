package com.medical.database;

import com.mongodb.*;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.*;

public class ConnectDB {
    private static MongoClient client;
    private static MongoDatabase database;
    private static MongoIterable<String> collection;
    private static Set<String> colName;

    public void insertData(int id,String collectionName,String data,String creationDate){
        //String json = JSONUtil.convertJavaToJson(object);
        Document document = new Document("patientId",id)
                .append("record", Arrays.asList(
                        new Document("Date",creationDate)
                                .append("data",data)));
        database.getCollection(collectionName).insertOne(document);
        System.out.println("inserted");
    }

    public List<String> getDataOfID(int patientId,String collectionName){
        MongoCollection<Document> collection1 = database.getCollection(collectionName);
        BasicDBObject query= new BasicDBObject();
        query.append("patientId",patientId);
        List<Document> list = (List<Document>) collection1.find(query).into( new ArrayList<>());
        List<String> result = new LinkedList();
        String jsonString = null;
        int size = 0;
        for(Document value:list){
            List<Document> store = (List<Document>) value.get("record");
            size = store.size();
            for(Document val:store){
                jsonString = val.get("data").toString();
                result.add(jsonString);
            }
        }
        return result;
    }
    
    public boolean checkId(int patientId,String collectionName){
        MongoCollection<Document> collection1 = database.getCollection(collectionName);
        BasicDBObject query= new BasicDBObject();
        query.append("patientId",patientId);
        MongoCursor<Document> d = collection1.find(query).iterator();
        if(d.hasNext())
            return true;
        return false;
    }

    public void createCollection(String collectionName) {
        System.out.println("Creating new collection");
        database.createCollection(collectionName);
    }

    public boolean checkCollection(String collectionName) {
        System.out.println("Checking if collection exists or not.....");
        collection = database.listCollectionNames();
        colName = new TreeSet<>();
        for (String col : collection) {
            colName.add(col);
        }
        if (!colName.contains(collectionName)) {
            System.out.println("collection does not exists:");
            return false;
        }
        System.out.println("exists");
        return true;
    }

    public boolean connectToMongo(){
        try {
            client = new MongoClient("127.0.0.1", 27017);
            database = client.getDatabase("medic");
            System.out.println("connection done");
            System.out.println("Database created:-" + database.getName());
            return true;
        } catch (Exception e) {
            System.out.println("exception:" + e);
            return false;
        }
    }
}
