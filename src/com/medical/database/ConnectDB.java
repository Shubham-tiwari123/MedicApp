package com.medical.database;

import com.medical.block.BlockStructure;
import com.medical.block.GenesisBlock;
import com.medical.util.JSONUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.Binary;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.*;

public class ConnectDB {
    private static MongoClient client;
    private static MongoDatabase database;
    private static MongoIterable<String> collection;
    private static Set<String> colName;

    public void insertData(int id, String creationDate, ArrayList<byte[]> encryptedValues,
                           String collectionName,Object object){
        //String json = JSONUtil.convertJavaToJson(object);
        StringBuilder builder;
        if (object.getClass().getSimpleName().equals("GenesisBlock")) {
            GenesisBlock genesisBlock = (GenesisBlock) object;
            builder = new StringBuilder(JSONUtil.convertJavaToJson(genesisBlock));
            builder.deleteCharAt(builder.length() - 1);
            builder.append(",\"currentBlockHash\":\"" + genesisBlock.getCurrentBlockHash() + "\"}");
        } else {
            BlockStructure getBlock = (BlockStructure) object;
            builder = new StringBuilder(JSONUtil.convertJavaToJson(getBlock));
            builder.deleteCharAt(builder.length() - 1);
            builder.append(",\"currentBlockHash\":\"" + getBlock.getCurrentBlockHash() + "\"}");
        }
        String json = builder.toString();
        Document document = new Document("patientId",id)
                .append("record", Arrays.asList(
                        new Document("Date",creationDate)
                                .append("data",json)));
        database.getCollection(collectionName).insertOne(document);
        System.out.println("inserted");
    }

    public void getData(ArrayList<byte[]> storeEncryptedValue){

        MongoCollection<Document> collections = database
                .getCollection("storeData");
        List<Document> employees = (List<Document>) collections.find().into(
                new ArrayList<Document>());

        List<String> encrypted;
        String v;
        for (Document employee : employees) {
            List<Document> courses = (List<Document>) employee.get("record");
            for (Document course : courses) {
                String json = course.getString("data");
                System.out.println(json);
                /*for(int i=0;i<encrypted.size();i++){//[B@86be70a  [B@480bdb19
                    v = encrypted.get(i);//en:[B@37afeb11 //en:[B@515aebb0
                    System.out.println("en:"+ v.getBytes());
                }*/
            }

        }
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
