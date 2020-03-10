package com.medical.client.service;

import com.medical.client.dao.Database;
import com.medical.client.entity.ClientKeys;
import com.medical.client.entity.GenesisBlock;
import com.medical.client.entity.MedicBlock;
import com.medical.client.utils.ConstantClass;
import java.util.ArrayList;
import java.util.List;

public class GetClientRecord implements GetClientRecordInterface {

    @Override
    public ClientKeys getKeysFromDatabase() throws Exception {
        Database database = new Database();
        return database.getClientKeys(ConstantClass.STORE_KEYS);
    }

    @Override
    public List medicBlocks(ArrayList<ArrayList<byte[]>> encryptedData, ClientKeys clientKeys)
            throws Exception {
        int count=0;
        List medicBlocks = new ArrayList<>();
        ExtraFunctions extraFunctions = new ExtraFunctions();
        for (ArrayList<byte[]> val : encryptedData) {
            StringBuilder builder = new StringBuilder();

            for (byte[] encryptedVal : val) {
                String subString = extraFunctions.decryptData(encryptedVal,
                        clientKeys.getPrivateKeyModules(), clientKeys.getPrivateKeyExpo());
                builder.append(subString);
            }
            if(count==0){
                GenesisBlock genesisBlock = extraFunctions.convertJsonToJava(builder.toString(),
                        GenesisBlock.class);
                medicBlocks.add(genesisBlock);
                count++;
            }
            else {
                MedicBlock medicBlock = extraFunctions.convertJsonToJava(builder.toString(), MedicBlock.class);
                medicBlocks.add(medicBlock);
            }
            System.out.println("data:\n" + builder.toString());
        }
        return medicBlocks;
    }

    @Override
    public boolean verifyChain(List medicBlocks) throws Exception {
        if(medicBlocks.size()>1) {
            for (int i=medicBlocks.size()-1;i>=1;i--){
                if((i-1)==0){
                    System.out.println("iifffff");
                    GenesisBlock genesisBlock = (GenesisBlock) medicBlocks.get(i-1);
                    MedicBlock medicBlock = (MedicBlock) medicBlocks.get(i);
                    if (!(genesisBlock.getCurrentBlockHash()).equals(medicBlock.getPreviousBlockHash())) {
                        return false;
                    }
                }else {
                    System.out.println("else");
                    MedicBlock medicBlock1 = (MedicBlock) medicBlocks.get(i);
                    MedicBlock medicBlock2 = (MedicBlock) medicBlocks.get(i - 1);
                    if (!(medicBlock2.getCurrentBlockHash()).equals(medicBlock1.getPreviousBlockHash())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public List<String> getRecord(ArrayList<ArrayList<byte[]>> encryptedData, ClientKeys clientKeys) throws Exception {

        List<String> medicBlocks = new ArrayList<>();
        ExtraFunctions extraFunctions = new ExtraFunctions();
        for (ArrayList<byte[]> val : encryptedData) {
            StringBuilder builder = new StringBuilder();
            for (byte[] encryptedVal : val) {
                String subString = extraFunctions.decryptData(encryptedVal,
                        clientKeys.getPrivateKeyModules(), clientKeys.getPrivateKeyExpo());
                builder.append(subString);
            }
            System.out.println("data:\n" + builder.toString());

            medicBlocks.add(builder.toString());
        }
        return medicBlocks;
    }
}
