package com.example.mobile_app.api.user.userObject;

import com.example.mobile_app.api.MedicalRecord.MedRecord;

import java.io.Serializable;

public class patientUser implements userInterface, Serializable {
    private String username;
    private String password;
    private String typeuser;
    private String symptoms;
    private MedRecord medicalRecord;
    private String id;
    private String age;
    private String name;
    private String citizenID;
    private String religion;
    private String sex;
    private String nationality;
    private String bloodType;
    private String phoneNumber;
    private String healthInsuranceID;
    private String occupation;
    private String address;
    private String birth;
    private boolean getStatus ;

    public boolean isStatus() {
        return getStatus;
    }
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
    public patientUser(String name, String age, String phoneNumber)
    {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        symptoms ="";
        this.typeuser = "Patient";
    }
    public patientUser(String name, String age, String phoneNumber,String symptoms)
    {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.symptoms = symptoms;
        this.typeuser = "Patient";
    }
    public patientUser(String name, String age, String phoneNumber, boolean getStatus)
    {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.getStatus = getStatus;
    }

    public String getSymptoms() {
        return symptoms;
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
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCitizenID() {
        return citizenID;
    }
    public void setCitizenID(String citizenID) {
        this.citizenID = citizenID;
    }
    public String getReligion() {
        return religion;
    }
    public void setReligion(String religion) {
        this.religion = religion;
    }
    public String getBloodType() {
        return bloodType;
    }
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getHealthInsuranceID() {
        return healthInsuranceID;
    }
    public void setHealthInsuranceID(String healthInsuranceID) {
        this.healthInsuranceID = healthInsuranceID;
    }
    public String getOccupation() {
        return occupation;
    }
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
