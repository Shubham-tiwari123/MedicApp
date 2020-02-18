package com.medical.server.requestAPI;

import com.medical.server.entity.MedicBlock;
import com.medical.server.entity.SerializeRecord;
import com.medical.server.service.ExtraFunctions;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@WebServlet(name = "Servlet" , urlPatterns = {"/servlet"})
public class Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ExtraFunctions extraFunctions = new ExtraFunctions();

        MedicBlock block = new MedicBlock();
        block.setPatientId(87016);
        block.setDate(Date.valueOf(LocalDate.now()));
        block.setTime(Time.valueOf(LocalTime.now()));
        block.setDoctorName("doctorName");
        block.setHospitalName("hospitalName");
        block.setSpecialistType("specialityType");
        block.setPrescription("prescription");
        block.setCurrentBlockHash("0");
        block.setPreviousBlockHash("0");

        try {
            String json1 = extraFunctions.convertJavaToJson(block);
            System.out.println("json: "+json1);
            String hash = extraFunctions.calculateHash(json1);
            block.setCurrentBlockHash(hash);
            String json2 = extraFunctions.convertJavaToJson(block);

            ArrayList<byte[]> encryptedData = extraFunctions.encryptBlock(json2);
            SerializeRecord serializeRecord = new SerializeRecord();
            serializeRecord.setEncryptedData(encryptedData);
            String data = extraFunctions.convertJavaToJson(serializeRecord);
            System.out.println("serializeRecord data:" + data);
            JSONObject object = new JSONObject();
            object.put("encrypted", data);
            object.put("hospitalUserName","s@gmail.com");
            object.put("patientId",87016);

            PrintWriter writer = response.getWriter();
            writer.println(object.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
