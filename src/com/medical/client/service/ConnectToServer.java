package com.medical.client.service;

import com.medical.client.dao.Database;
import com.medical.client.entity.ClientKeys;
import com.medical.client.entity.ServerKeys;
import com.medical.client.utils.ConstantClass;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Random;

public class ConnectToServer implements ConnectToServerInterface {

    public static Socket socket;
    private static DataOutputStream out;
    private ExtraFunctions extraFunctions = new ExtraFunctions();
    private Database database = new Database();

    @Override
    public boolean openSocket(String address, int port) throws Exception {
        socket = new Socket(address, port);
        System.out.println("connected");
        out = new DataOutputStream(socket.getOutputStream());
        return true;
    }

    @Override
    public String readServerData() throws Exception {
        DataInputStream inputStream = new DataInputStream(new BufferedInputStream
                (socket.getInputStream()));
        return inputStream.readUTF();
    }

    @Override
    public boolean sendData(String data) throws Exception {
        if (socket.isConnected()) {
            out.writeUTF(data);
            return true;
        }
        return false;
    }

    @Override
    public void closeConnections() throws Exception {
        out.close();
        socket.close();
    }

    @Override
    public String verifyNetwork() throws Exception {
        System.out.println("Verifying network....");
        Random random = new Random();
        int data = 1000 + random.nextInt(5000);
        String hashData = calculateHash(String.valueOf(data));
        JSONObject object = new JSONObject();
        object.put("data", data);
        object.put("hash", hashData);
        return object.toString();
    }

    @Override
    public String prepareKeysToSend(String hospitalSignature) throws Exception {
        System.out.println("Sending client keys to server");
        ClientKeys keys = database.getClientKeys(ConstantClass.STORE_KEYS);   //specify collection name
        if (keys == null){
            keys = generateKeys();
            database.storeClientKeys(keys, ConstantClass.STORE_KEYS);
        }
        String pubModHash = calculateHash(keys.getPublicKeyModules().toString());
        String pubExpoHash = calculateHash(keys.getPublicKeyExpo().toString());
        String sigHash = calculateHash(hospitalSignature);

        JSONObject object = new JSONObject();
        object.put("modValue", keys.getPublicKeyModules().toString());
        object.put("modHash", pubModHash);
        object.put("expoValue", keys.getPublicKeyExpo().toString());
        object.put("expoHash", pubExpoHash);
        object.put("signature", hospitalSignature);
        object.put("sigHash", sigHash);

        return object.toString();
    }

    @Override
    public boolean verifyServerKeys(String serverKeys) throws Exception {
        System.out.println("Verifying keys");
        Object object = new JSONParser().parse(serverKeys);
        JSONObject jsonObject = (JSONObject) object;
        String modHash = (String) jsonObject.get("modHashS");
        String expoHash = (String) jsonObject.get("expoHashS");
        String modValue = (String) jsonObject.get("modValueS");
        String expoValue = (String) jsonObject.get("expoValueS");

        return modHash.equals(calculateHash(modValue)) && expoHash.equals(calculateHash(expoValue));
    }

    @Override
    public boolean storeServerKeys(String serverKeys) throws Exception {
        System.out.println("Storing server keys");
        Object object = new JSONParser().parse(serverKeys);
        JSONObject jsonObject = (JSONObject) object;
        String modValue = (String) jsonObject.get("modValueS");
        String expoValue = (String) jsonObject.get("expoValueS");
        ServerKeys keys = new ServerKeys();
        keys.setPublicKeyModules(new BigInteger(modValue));
        keys.setPublicKeyExpo(new BigInteger(expoValue));
        return database.storeServerKeys(keys, ConstantClass.STORE_KEYS);
        //return true;
    }

    @Override
    public String calculateHash(String data) throws Exception {
        return extraFunctions.calculateHash(data);
    }

    @Override
    public ClientKeys generateKeys() throws Exception {
        System.out.println("generating keys");
        ClientKeys clientKeys = new ClientKeys();
        KeyPairGenerator keyPair = KeyPairGenerator.getInstance("RSA");
        keyPair.initialize(3072);//3072
        KeyPair pair = keyPair.generateKeyPair();
        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();
        // Generating key-pair
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec rsaPublicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
        RSAPrivateKeySpec rsaPrivateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
        //Setting public keys
        clientKeys.setPublicKeyExpo(rsaPublicKeySpec.getPublicExponent());
        clientKeys.setPublicKeyModules(rsaPublicKeySpec.getModulus());
        //Setting private keys
        clientKeys.setPrivateKeyExpo(rsaPrivateKeySpec.getPrivateExponent());
        clientKeys.setPrivateKeyModules(rsaPrivateKeySpec.getModulus());
        return clientKeys;
    }

    @Override
    public boolean deleteServerKeys() throws Exception {
        return database.deleteServerKeys(ConstantClass.STORE_KEYS);
    }
}
