package com.medical.server.requestAPI;

import com.medical.server.entity.ClientSideBlock;
import com.medical.server.entity.ClientSideBlockHash;
import com.medical.server.entity.SetKeys;
import com.medical.server.responseAPI.AppendBlockResAPI;
import com.medical.server.service.AppendData;
import com.medical.server.service.CreateAccount;
import com.medical.server.service.ExtraFunctions;
import com.medical.server.utils.VariableClass;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;

@WebServlet(name = "AppendBlockReqAPI", urlPatterns = {"/appendRecord"})
public class AppendBlockReqAPI extends HttpServlet {

    private static ArrayList<byte[]> dataFromClient(long patientId) throws NoSuchAlgorithmException {
        ExtraFunctions extraFunctions = new ExtraFunctions();
        ClientSideBlockHash block = new ClientSideBlockHash();
        block.setPatientId(patientId);
        block.setDate(Date.valueOf(LocalDate.now()));
        block.setTime(Time.valueOf("20:17:36"));
        block.setDoctorName("Mr Ram");
        block.setHospitalName("medic center");
        block.setSpecialistType("Surgeon");
        block.setPrescription("mlnfnlnoi  bdnigt gfifnjg rgieningr sirggr rgrgjgjogrgr geigjsofnfrirgj" +
                "lngngrg gignrgrklvjrprjr rgipoojr pojwoif fofnln hpoojffjr goioirijfdnfgrirnwfpwfwlln" +
                "sninfnvg ivoienreo  vsosd rlnfefnfw wrgrsnf efe gnacn ofn ubn; jfdkdureaw");
        System.out.println("block:"+extraFunctions.convertJavaToJson(block));
        String calHash = extraFunctions.calculateHash(extraFunctions.convertJavaToJson(block));

        ClientSideBlock sideBlock = new ClientSideBlock();
        sideBlock.setPatientId(block.getPatientId());
        sideBlock.setDate(block.getDate());
        sideBlock.setTime(block.getTime());
        sideBlock.setDoctorName(block.getDoctorName());
        sideBlock.setHospitalName(block.getHospitalName());
        sideBlock.setSpecialistType(block.getSpecialistType());
        sideBlock.setPrescription(block.getPrescription());
        sideBlock.setCurrentBlockHash(calHash);
        String encryptString = extraFunctions.convertJavaToJson(sideBlock);
        System.out.println("en:"+encryptString);
        CreateAccount createAccount = new CreateAccount();
        ArrayList<byte[]> encryptedData = createAccount.encryptBlock(encryptString);
        System.out.println("length:"+encryptedData.size());
        return encryptedData;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        AppendBlockResAPI resAPI = new AppendBlockResAPI();
        AppendData appendData = new AppendData();
        SetKeys keys = new SetKeys();
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        int statusCode;
        String line;
        while((line = reader.readLine())!= null) {
            buffer.append(line);
        }
        String data = buffer.toString();
        JSONParser parser = new JSONParser();
        try {
            JSONObject jSONObject = (JSONObject) parser.parse(data);

            long patientId = (Long) jSONObject.get("patientId");
            //ArrayList<String> encryptedValues = (ArrayList<String>) jSONObject.get("encryptedValues");
            System.out.println("id:-"+patientId);
            ArrayList<byte[]> encryptedData = dataFromClient(patientId);

            //Start with server function
            if(appendData.verifyID(patientId)){
                System.out.println("patient exist");
                String decryptString = appendData.decryptData(encryptedData,keys);
                System.out.println("dec:"+decryptString);
                //verify data
                if(appendData.verifyData(decryptString)) {
                    System.out.println("equal");
                    String lastBlockHash = appendData.getLastBlockHashDb(patientId);
                    String updatedBlock = appendData.updateBlock(lastBlockHash,decryptString);
                    System.out.println("updatedblock:\n"+updatedBlock);

                    if(appendData.appendBlockInChain(patientId,updatedBlock,keys)) {
                        statusCode = VariableClass.SUCCESSFUL;
                        System.out.println("data saved");
                    }
                    else {
                        statusCode=VariableClass.FAILED;
                        System.out.println("not saved");
                    }
                }
                else{
                    statusCode= VariableClass.BAD_REQUEST;
                    System.out.println("not equal");
                }
            }
            else {
                statusCode = VariableClass.BAD_REQUEST;
                System.out.println("no record");
            }
            resAPI.setStatusCode(statusCode,response);
        } catch (Exception e) {
            e.printStackTrace();
            resAPI.setStatusCode(VariableClass.BAD_REQUEST,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        AppendBlockResAPI resAPI = new AppendBlockResAPI();
        resAPI.setStatusCode(VariableClass.BAD_REQUEST,response);
    }
}
