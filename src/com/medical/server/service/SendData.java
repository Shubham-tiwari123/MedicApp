package com.medical.server.service;

import com.medical.server.dao.Database;
import com.medical.server.entity.ClientKeys;
import com.medical.server.entity.ServerKeys;
import com.medical.server.utils.VariableClass;
import java.util.ArrayList;
import java.util.List;

public class SendData implements SendDataInterface {

    private Database database = new Database();
    private ExtraFunctions extraFunctions = new ExtraFunctions();

    @Override
    public boolean verifyHospital(String userName) throws Exception {
        return database.verifyHospital(userName, VariableClass.REGISTER_HEALTH_CARE);
    }

    @Override
    public boolean verifyID(long patientID) throws Exception{
        System.out.println("verify patientID (file name):"+getClass());
        return !database.verifyPatientIdDB(patientID, VariableClass.REGISTER_PATIENT);
    }

    @Override
    public List<String> getDataDB(long patientID) throws Exception{
        System.out.println("getting data from db (file name):"+getClass());
        ArrayList<ArrayList<byte[]>> getAllData= database.getSpecificData(patientID,
                VariableClass.STORE_DATA_COLLECTION);
        List<String> convertToString = new ArrayList<>();

        System.out.println("getting server public key from db for decryption (file name):"+getClass());
        ServerKeys keys = database.getServerKey(VariableClass.STORE_KEYS);

        System.out.println("Decrypting data...and converting to string (file name):"+getClass());
        for(ArrayList<byte[]> val:getAllData){
            StringBuilder builder = new StringBuilder();
            for (byte[] encryptedVal:val){
                String subString = extraFunctions.decryptData(encryptedVal,keys.getPublicKeyModules(),
                        keys.getPublicKeyExpo());
                builder.append(subString);
            }
            convertToString.add(builder.toString());
        }
        return convertToString;
    }

    @Override
    public ClientKeys getClientKeys(String hospitalUserName) throws Exception{
        System.out.println("getting hospital public key for encryption:"+getClass());
        return database.getClientKeys(hospitalUserName,VariableClass.STORE_KEYS);
    }

    //encrypt data using client public key
    @Override
    public ArrayList<ArrayList<byte[]>> encryptDataAgain(ClientKeys keys, List<String> data) throws Exception{
        System.out.println("encrypting data for sending(file name):"+getClass());
        ArrayList<ArrayList<byte[]>> encryptDataList = new ArrayList<>();
        for (String val : data) {
            ArrayList<byte[]> subList = encryptBlock(val,keys);
            encryptDataList.add(subList);
        }
        return encryptDataList;
    }

    private ArrayList<byte[]> encryptBlock(String data, ClientKeys keys) throws Exception{
        int count = 0;
        int start = 0, end = 0;
        String substring;
        ArrayList<String> storeSubString = new ArrayList<String>();
        ArrayList<byte[]> storeEncryptedValue = new ArrayList<byte[]>();
        while (count <= data.length()) {
            if (data.length() - end > 250) {
                end = start + 251;
                substring = data.substring(start, end);
                storeSubString.add(substring);
                start = start + 251;
            } else {
                start = end;
                end = data.length();
                substring = data.substring(start, end);
                storeSubString.add(substring);
            }
            count = count + 250;
        }
        count = 0;
        while (count != storeSubString.size()) {
            byte[] encryptedData =  extraFunctions.encryptData(storeSubString.get(count),
                    keys.getClientPubKeyMod(), keys.getClientPubKeyExpo());
            storeEncryptedValue.add(encryptedData);
            count++;
        }
        return storeEncryptedValue;
    }
}
