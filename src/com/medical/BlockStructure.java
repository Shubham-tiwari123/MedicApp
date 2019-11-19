package com.medical;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

// specifying the order in which JSON will be generated.
@JsonPropertyOrder(value = {
        "patientId","date","time","hospitalName","doctorName","specialistType","prescription","prevBlockHashValue"
})
public class BlockStructure implements Serializable {
    private int patientId;
    private Date date;
    private Time time;
    private String hospitalName;
    private String doctorName;
    private String specialistType;
    private String prescription;
    private String prevBlockHashValue;
    @JsonIgnore         // ignoring property from converting into JSON
    private String currentBlockHashValue;


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

    @JsonIgnore
    public String getCurrentBlockHashValue() {
        return currentBlockHashValue;
    }
    @JsonIgnore
    public void setCurrentBlockHashValue(String currentBlockHashValue) {
        this.currentBlockHashValue = currentBlockHashValue;
    }
    public String getPrevBlockHashValue() {
        return prevBlockHashValue;
    }
    public void setPrevBlockHashValue(String prevBlockHashValue) {
        this.prevBlockHashValue = prevBlockHashValue;
    }
}
