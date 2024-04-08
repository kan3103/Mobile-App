package com.example.mobile_app.api.user.userObject;

import com.example.mobile_app.api.MedicalRecord.MedRecord;

public class patientUser implements userInterface{
    private String username;
    private String password;
    private MedRecord medicalRecord;
    public patientUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
    public void setUserPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
    public void setMedicalRecord(MedRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }
    public MedRecord getMedicalRecord() {
        return medicalRecord;
    }
}
