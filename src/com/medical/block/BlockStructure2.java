package com.medical.block;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

// specifying the order in which JSON will be generated.
@JsonPropertyOrder(value = {
        "patientId","date","time","hospitalName","doctorName","specialistType","prescription",
        "previousBlockHash","currentBlockHash"
})
public class BlockStructure2 implements Serializable {
    private int patientId;
    private Date date;
    private Time time;
    private String hospitalName;
    private String doctorName;
    private String specialistType;
    private String prescription;
    private String previousBlockHash;
    private String currentBlockHash;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSpecialistType() {
        return specialistType;
    }

    public void setSpecialistType(String specialistType) {
        this.specialistType = specialistType;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getCurrentBlockHash() {
        return currentBlockHash;
    }

    public void setCurrentBlockHash(String currentBlockHash) {
        this.currentBlockHash = currentBlockHash;
    }

    public String getPrevBlockHash() {
        return previousBlockHash;
    }

    public void setPrevBlockHash(String previousBlockHash) {
        this.previousBlockHash = previousBlockHash;
    }
}

