package com.medical.server.dao;

import com.medical.server.entity.ClientKeys;
import com.medical.server.entity.HospitalDetails;
import com.medical.server.entity.PatientRecord;
import com.medical.server.entity.ServerKeys;
import com.medical.server.service.ExtraFunctions;
import com.medical.server.utils.VariableClass;
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
import java.math.BigInteger;
import java.util.*;

public class Database implements DatabaseInterface {

    private static MongoClient client;
    private static MongoDatabase database;
    private static MongoIterable<String> iterable;
    private static MongoCollection collection;
    private static Set<String> colName;
    ExtraFunctions extraFunctions = new ExtraFunctions();

    @Override
    public boolean createDbConn() throws Exception {
        client = new MongoClient(VariableClass.IP_ADDRESS, VariableClass.PORT_NUMBER);
        database = client.getDatabase(VariableClass.DATABASE_NAME);
        return true;
    }

    @Override
    public boolean checkCollection(String collectionName) throws Exception {
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
        boolean status = false;
        if (createDbConn()) {
            if (checkCollection(collectionName)) {
                System.out.println("verifying id db...");
                collection = database.getCollection(collectionName);
                List<Document> user = (List<Document>) collection.find(new Document("patientID",
                        patientId)).into(new ArrayList<Document>());
                if (!user.isEmpty()){
                    for (Document doc:user){
                        status = doc.getBoolean("active");
                    }
                }
                return status && user.size() == 1;
            }
            return false;
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
    public ServerKeys getServerKey(String collectionName) throws Exception {
        System.out.println("getting server keys db...");
        ServerKeys keys = new ServerKeys();
        if (createDbConn()) {
            if (checkCollection(collectionName)) {
                collection = database.getCollection(collectionName);
                List<Document> list = (List<Document>) collection.find(new Document("keys", "serverKeys")).
                        into(new ArrayList<Document>());
                if (!list.isEmpty()) {
                    for (Document val : list) {
                        System.out.println("getting server keys if");
                        String publicKeyModules = val.getString("publicKeyModules");
                        String publicKeyExpo = val.getString("publicKeyExpo");
                        String privateKeyModules = val.getString("privateKeyModules");
                        String privateKeyExpo = val.getString("privateKeyExpo");

                        System.out.println("setting server keys");
                        keys.setPrivateKeyExpo(new BigInteger(privateKeyExpo));
                        keys.setPrivateKeyModules(new BigInteger(privateKeyModules));
                        keys.setPublicKeyExpo(new BigInteger(publicKeyExpo));
                        keys.setPublicKeyModules(new BigInteger(publicKeyModules));
                    }
                    return keys;
                }/*else{
                    keys.setPrivateKeyExpo(VariableClass.serverPriExpo);
                    keys.setPrivateKeyModules(VariableClass.serverPriMod);
                    keys.setPublicKeyExpo(VariableClass.serverPubExpo);
                    keys.setPublicKeyModules(VariableClass.serverPubMod);
                    return keys;
                }*/
                return null;
            }
            return null;
        }
        return null;
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
    public boolean verifyHospital(String userName, String collectionName) throws Exception {
        if (createDbConn()) {
            if (checkCollection(collectionName)) {
                System.out.println("verifying");
                collection = database.getCollection(collectionName);
                List<Document> user = (List<Document>) collection.find(new Document("userName", userName)).
                        into(new ArrayList<Document>());
                System.out.println("User present:" + user.size());
                return user.size() == 0;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean registerHospital(String collectionName, HospitalDetails details) throws Exception {
        if (createDbConn()) {
            if (checkCollection(collectionName)) {
                System.out.println("registering hospital");
                Document document = new Document("userName", details.getUserName())
                        .append("password", details.getPassword())
                        .append("hospitalName", details.getHospitalName())
                        .append("hospitalAddress", details.getHospitalAddress())
                        .append("state", details.getState())
                        .append("city", details.getCity())
                        .append("phoneNumber",details.getPhoneNumber())
                        .append("active",true);
                database.getCollection(collectionName).insertOne(document);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean registerPatient(String collectionName, PatientRecord record,String hospitalEmail)
            throws Exception {
        if (createDbConn()) {
            if (checkCollection(collectionName)) {
                System.out.println("registering hospital");
                Document document = new Document("patientID", record.getPatientID())
                        .append("name", record.getName())
                        .append("age", record.getAge())
                        .append("address", record.getAddress())
                        .append("phoneNumber", record.getPhoneNumber())
                        .append("gender", record.getGender())
                        .append("hospitalEmail",hospitalEmail)
                        .append("active",true);
                database.getCollection(collectionName).insertOne(document);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean checkLoginCredentials(String userName, String password,String collectionName) throws Exception {
        String pass = null;
        boolean activeStatus = false;
        if(createDbConn()){
            if(checkCollection(collectionName)){
                MongoCollection<Document> collection = database.getCollection(collectionName);
                System.out.println("getting client keys from db");
                List<Document> list = collection.find(new Document("userName", userName))
                        .into(new ArrayList<Document>());
                System.out.println("list:" + list.size());
                if (!list.isEmpty()) {
                    for (Document val : list) {
                        pass = val.getString("password");
                        activeStatus = val.getBoolean("active");
                    }
                    return activeStatus && password.equals(pass);
                }
                return false;
            }
            return false;
        }
        return false;
    }

    @Override
    public List<String> getAllHospitals(String collectionName) throws Exception {
        List<String> result = new ArrayList<>();
        if(createDbConn()){
            if(checkCollection(collectionName)){
                MongoCollection<Document> collection = database.getCollection(collectionName);
                System.out.println("getting client keys from db");
                List<Document> list = collection.find().into(new ArrayList<Document>());
                for(Document val: list){
                    System.out.println("value:"+val);
                    HospitalDetails hospitalDetails = new HospitalDetails();
                    hospitalDetails.setHospitalName(val.getString("hospitalName"));
                    hospitalDetails.setCity(val.getString("city"));
                    hospitalDetails.setState(val.getString("state"));
                    hospitalDetails.setHospitalAddress("null");
                    hospitalDetails.setPassword("null");
                    hospitalDetails.setPhoneNumber("null");
                    hospitalDetails.setUserName(val.getString("userName"));
                    hospitalDetails.setActive(val.getBoolean("active"));
                    String jsonString = extraFunctions.convertJavaToJson(hospitalDetails);
                    result.add(jsonString);
                }
            }
        }
        return result;
    }

    @Override
    public List<String> getAllPatients(String collectionName) throws Exception {
        List<String> result = new ArrayList<>();
        if(createDbConn()){
            if(checkCollection(collectionName)){
                MongoCollection<Document> collection = database.getCollection(collectionName);
                System.out.println("getting client keys from db");
                List<Document> list = collection.find().into(new ArrayList<Document>());
                for(Document val: list){
                    System.out.println("value:"+val);
                    PatientRecord patientRecord = new PatientRecord();
                    patientRecord.setPatientID(val.getLong("patientID"));
                    patientRecord.setName(val.getString("name"));
                    patientRecord.setPhoneNumber(val.getLong("phoneNumber"));
                    patientRecord.setAddress("null");
                    patientRecord.setAge(0);
                    patientRecord.setGender("null");
                    String jsonString = extraFunctions.convertJavaToJson(patientRecord);
                    result.add(jsonString);
                }
            }
        }
        return result;

    }

    @Override
    public HospitalDetails getHospitalDetails(String email, String collectionName) throws Exception {
        HospitalDetails hospitalDetails = null;
        if(createDbConn()){
            if(checkCollection(collectionName)){
                MongoCollection<Document> collection = database.getCollection(collectionName);
                System.out.println("getting client keys from db");
                List<Document> list = collection.find(new Document("userName", email)).into(new ArrayList<Document>());
                for(Document val: list){
                    System.out.println("value:"+val);
                    hospitalDetails = new HospitalDetails();
                    hospitalDetails.setHospitalName(val.getString("hospitalName"));
                    hospitalDetails.setCity(val.getString("city"));
                    hospitalDetails.setState(val.getString("state"));
                    hospitalDetails.setHospitalAddress("null");
                    hospitalDetails.setPassword("null");
                    hospitalDetails.setPhoneNumber("null");
                    hospitalDetails.setUserName(val.getString("userName"));
                }
            }
        }
        return hospitalDetails;
    }

    @Override
    public ClientKeys getClientKeys(String hospital, String collectionName) throws Exception {
        ClientKeys keys = new ClientKeys();
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

                        System.out.println("setting client keys");
                        keys.setClientPubKeyMod(new BigInteger(publicKeyModules));
                        keys.setClientPubKeyExpo(new BigInteger(publicKeyExpo));
                    }
                    return keys;
                }
                /*else{
                    keys.setClientPubKeyExpo(VariableClass.clientPubExpo);
                    keys.setClientPubKeyMod(VariableClass.clientPubMod);
                    return keys;
                }*/
                return null;
            }
            return null;
        }
        return null;
    }

    @Override
    public boolean storeServerKeys(ServerKeys keys, String collectionName) throws Exception {
        if (createDbConn()) {
            if (checkCollection(collectionName)) {
                collection = database.getCollection(collectionName);
                List<Document> user = (List<Document>) collection.find(new Document("keys", "serverKeys")).
                        into(new ArrayList<Document>());
                if (user.isEmpty()) {
                    Document document = new Document("keys", "serverKeys")
                            .append("publicKeyModules", keys.getPublicKeyModules().toString())
                            .append("publicKeyExpo", keys.getPublicKeyExpo().toString())
                            .append("privateKeyModules", keys.getPrivateKeyModules().toString())
                            .append("privateKeyExpo", keys.getPrivateKeyExpo().toString());
                    database.getCollection(collectionName).insertOne(document);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean storeClientKeys(ClientKeys keys, String collectionName, String signature)
            throws Exception {
        if (createDbConn()) {
            if (checkCollection(collectionName)) {
                System.out.println("Storing keys");
                Document document = new Document("userName", signature)
                        .append("publicKeyModules", keys.getClientPubKeyMod().toString())
                        .append("publicKeyExpo", keys.getClientPubKeyExpo().toString());
                database.getCollection(collectionName).insertOne(document);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean activateHospital(String email, String collectionName) throws Exception {
        if (createDbConn()) {
            if (checkCollection(collectionName)) {
                System.out.println("Activating hospital db...");
                collection = database.getCollection(collectionName);
                Bson filter = Filters.eq("userName", email);
                UpdateResult result = collection.updateOne(filter,
                        Updates.set("active", true));
                return result.getMatchedCount() == 1;
            }
        }
        return false;
    }

    @Override
    public boolean deactivateHospital(String email, String collectionName) throws Exception {
        if (createDbConn()) {
            if (checkCollection(collectionName)) {
                System.out.println("Deactivating hospital db...");
                collection = database.getCollection(collectionName);
                Bson filter = Filters.eq("userName", email);
                UpdateResult result = collection.updateOne(filter,
                        Updates.set("active", false));
                return result.getMatchedCount() == 1;
            }
        }
        return false;
    }

    @Override
    public boolean activatePatient(String email, String collectionName) throws Exception {
        return false;
    }

    @Override
    public boolean deactivatePatient(String email, String collectionName) throws Exception {
        return false;
    }
}
