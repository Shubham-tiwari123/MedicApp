package com.medical.client.service;

import com.medical.client.dao.Database;
import com.medical.client.entity.ClientKeys;
import com.medical.client.entity.ServerKeys;
import com.medical.client.utils.VariableClass;
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
    public String prepareKeysToSend() throws Exception {
        System.out.println("Sending client keys to server");
        ClientKeys keys = database.getClientKeys2(VariableClass.STORE_KEYS);   //specify collection name
        if (keys == null){
            keys = generateKeys();
            database.storeClientKeys(keys,VariableClass.STORE_KEYS);
        }
        /*ClientKeys keys = new ClientKeys();
        keys.setPublicKeyExpo(new BigInteger("65537"));
        keys.setPublicKeyModules(new BigInteger("4214777907657253113000721125232472745151580275285647620895837240116389083817917878012365856944153092973868671924564389124521775170086968208302639999087780442035711202814704935797100667846856044291609671826055542255797138350990715705296927176437820167719797414475944743179643064967001415309590173778978994981565503683209815493080469926827684603043186212402541461555174760220150526056039715051302004523664386280764941286651600519216673399923121960013752645423519522577190537942240232410245814356233811170598121245760218272339690452606653687518633839603065406334786701945064057361092780039613315761236874042648446366066611886311596584361240596079231203346311092661161817519019821952137525442766196358670410972718999957615697381937635782535051583752394240837340159209718878221683849030276452271788676950559026334485190010556958833951179653367985903385866522528207314896706713176659703852530064861393257095252259354783203190648803"));
*/
        String pubModHash = calculateHash(keys.getPublicKeyModules().toString());
        String pubExpoHash = calculateHash(keys.getPublicKeyExpo().toString());
        String signature = "signature";
        String sigHash = calculateHash(signature);

        JSONObject object = new JSONObject();
        object.put("modValue", keys.getPublicKeyModules().toString());
        object.put("modHash", pubModHash);
        object.put("expoValue", keys.getPublicKeyExpo().toString());
        object.put("expoHash", pubExpoHash);
        object.put("signature", signature);
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
        return database.storeServerKeys(keys, VariableClass.STORE_KEYS);
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
        return database.deleteServerKeys(VariableClass.STORE_KEYS);
    }
}
