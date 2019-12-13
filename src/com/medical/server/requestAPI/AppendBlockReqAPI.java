package com.medical.server.requestAPI;

import com.medical.server.entity.ClientSideBlock;
import com.medical.server.entity.SetKeys;
import com.medical.server.responseAPI.AppendBlockResAPI;
import com.medical.server.service.AppendData;
import com.medical.server.service.CreateAccount;
import com.medical.server.service.ExtraFunctions;
import com.medical.server.utils.VariableClass;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@WebServlet(name = "AppendBlockReqAPI", urlPatterns = {"/appendRecord"})
public class AppendBlockReqAPI extends HttpServlet {

    private static ArrayList<byte[]> dataFromClient(long patientId){
        ClientSideBlock block = new ClientSideBlock();
        block.setPatientId(patientId);
        block.setDate(Date.valueOf(LocalDate.now()));
        block.setTime(Time.valueOf("20:17:36"));
        block.setDoctorName("Mr Ram");
        block.setHospitalName("medic center");
        block.setSpecialistType("Surgeon");
        block.setPrescription("mlnfnlnoi  bdnigt gfifnjg rgieningr sirggr rgrgjgjogrgr geigjsofnfrirgj" +
                "lngngrg gignrgrklvjrprjr rgipoojr pojwoif fofnln hpoojffjr goioirijfdnfgrirnwfpwfwlln" +
                "sninfnvg ivoienreo  vsosd rlnfefnfw wrgrsnf efe gnacn ofn ubn; jfdkdureaw");
        //block.setCurrentBlockHash("-10882-37-30-42-96-265669123-10843931101-77-119477105-78120-64-25-77-14-784824-3113-7");
        block.setCurrentBlockHash("-10882-37-30-42-96-265669123-10843931101-77-119477105-78120-64-25-77-14-784824-3113-7");
        String encryptString = "{\"patientId\":1234,\"date\":1576089000000,\"time\":\"20:17:36\",\"hospitalName\":\"medic center\"," +
                "\"doctorName\":\"Mr Ram\",\"specialistType\":\"Surgeon\",\"prescription\":\"mlnfnlnoi  bdnigt gfifnjg rgieningr sirggr " +
                "rgrgjgjogrgr geigjsofnfrirgjlngngrg gignrgrklvjrprjr rgipoojr pojwoif fofnln hpoojffjr goioirijfdnfgrirnwfpwfwllnsninfnvg " +
                "ivoienreo  vsosd rlnfefnfw wrgrsnf efe gnacn ofn ubn; jfdkdureaw\",\"currentBlockHash\":\"-10882-37-30-42-96-265669123-108439" +
                "31101-77-119477105-78120-64-25-77-14-784824-3113-7\"}";

        System.out.println("en:"+encryptString);
        CreateAccount createAccount = new CreateAccount();
        ArrayList<byte[]> encryptedData = createAccount.encryptBlock(encryptString);
        return encryptedData;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AppendBlockResAPI resAPI = new AppendBlockResAPI();
        AppendData appendData = new AppendData();
        SetKeys keys = new SetKeys();
        ExtraFunctions extraFunctions = new ExtraFunctions();
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();

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
            ArrayList<byte[]> encryptedData = dataFromClient(1234);

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

                    if(appendData.appendBlockInChain(patientId,updatedBlock,keys))
                        System.out.println("data saved");
                    else
                        System.out.println("not saved");
                }
                else
                    System.out.println("not equal");
            }
            else
                System.out.println("no record");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
