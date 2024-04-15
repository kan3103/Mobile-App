package com.example.mobile_app.api.user.userObject;

import com.example.mobile_app.api.MedicalRecord.MedRecord;

import java.io.Serializable;

public class patientUser implements userInterface, Serializable {
    private String username;
    private String password;
    private String typeuser;
    private MedRecord medicalRecord;
    private String id;
    private String sex;
    private String nationality;
    private String birth;

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public patientUser(String username, String password) {
        this.username = username;
        this.password = password;
        this.typeuser = "Patient";
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
    public String getTypeuser(){return typeuser; };
}
