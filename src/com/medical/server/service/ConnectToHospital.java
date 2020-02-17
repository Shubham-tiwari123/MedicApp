package com.medical.server.service;

import com.medical.server.dao.Database;
import com.medical.server.entity.ClientKeys;
import com.medical.server.entity.ServerKeys;
import com.medical.server.utils.VariableClass;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class ConnectToHospital implements ConnectToHospitalInterface {
    public static Socket socket = null;
    private static DataOutputStream out = null;
    private ExtraFunctions extraFunction = new ExtraFunctions();
    private Database database = new Database();
    public static ServerSocket server;

    @Override
    public boolean connectUsingSocket(int port) throws Exception {
        server = new ServerSocket(port);
        System.out.println("Socket started....");
        socket= server.accept();
        out = new DataOutputStream(socket.getOutputStream());
        System.out.println("Waiting for client");
        return true;
    }

    @Override
    public String readClientData() throws Exception {
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
    public boolean verifyNetwork(String data) throws Exception {
        Object object = new JSONParser().parse(data);
        JSONObject jsonObject = (JSONObject) object;
        long num = (long) jsonObject.get("data");
        String hash = (String) jsonObject.get("hash");
        String calHash = calculateHash(String.valueOf(num));
        return calHash.equals(hash);
    }

    @Override
    public String getServerKeys() throws Exception {
        ServerKeys keys = database.getServerKey(VariableClass.STORE_KEYS);
        if(keys==null){
            keys = generateKeys();
            if(!database.storeServerKeys(keys,VariableClass.STORE_KEYS))
                return null;
        }
        String pubModHash = calculateHash(keys.getPublicKeyModules().toString());
        String pubExpoHash = calculateHash(keys.getPublicKeyExpo().toString());
        JSONObject object = new JSONObject();
        object.put("modValueS", keys.getPublicKeyModules().toString());
        object.put("modHashS", pubModHash);
        object.put("expoValueS", keys.getPublicKeyExpo().toString());
        object.put("expoHashS", pubExpoHash);
        return object.toString();
    }

    @Override
    public boolean verifyClientKeys(String clientKeys) throws Exception {
        System.out.println("Verifying client keys send by server");
        Object object = new JSONParser().parse(clientKeys);
        JSONObject jsonObject = (JSONObject) object;
        String modHash = (String) jsonObject.get("modHash");
        String expoHash = (String) jsonObject.get("expoHash");
        String modValue = (String) jsonObject.get("modValue");
        String expoValue = (String) jsonObject.get("expoValue");
        String clientSignature = (String) jsonObject.get("signature");
        String signatureHash = (String) jsonObject.get("sigHash");

        return modHash.equals(calculateHash(modValue)) && expoHash.equals(calculateHash(expoValue))
                && signatureHash.equals(calculateHash(clientSignature));
    }

    @Override
    public boolean storeClientKeys(String clientKeys) throws Exception {
        Object object = new JSONParser().parse(clientKeys);
        JSONObject jsonObject = (JSONObject) object;

        String clientSignature = (String) jsonObject.get("signature");
        String modValue = (String) jsonObject.get("modValue");
        String expoValue = (String) jsonObject.get("expoValue");
        ClientKeys keys = new ClientKeys();

        keys.setClientPubKeyExpo(new BigInteger(expoValue));
        keys.setClientPubKeyMod(new BigInteger(modValue));

        return database.storeClientKeys(keys,VariableClass.STORE_KEYS,clientSignature);
    }

    @Override
    public String calculateHash(String data) throws Exception {
        return extraFunction.calculateHash(data);
    }

    @Override
    public ServerKeys generateKeys() throws Exception {
        System.out.println("generating keys: "+getClass());
        ServerKeys serverKeys = new ServerKeys();
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
        serverKeys.setPublicKeyExpo(rsaPublicKeySpec.getPublicExponent());
        serverKeys.setPublicKeyModules(rsaPublicKeySpec.getModulus());
        //Setting private keys
        serverKeys.setPrivateKeyExpo(rsaPrivateKeySpec.getPrivateExponent());
        serverKeys.setPrivateKeyModules(rsaPrivateKeySpec.getModulus());
        return serverKeys;
    }
}
