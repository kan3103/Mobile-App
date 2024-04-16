package com.example.mobile_app.api.Patient.PatientObj;

import com.example.mobile_app.api.MedicalRecord.MedRecord;

public class Patient {
    private String name;
    private String sex;
    private String address;
    private String phoneNumber;
    private String id;
    private MedRecord medicalRecord;
    private String healthInsuranceID;
    private String BloodType;
    private String bloodType;
    private String occupation;
    private String citizenID;
    private String religion;
    private String birth;
    private String age;
    private boolean insuranceType;

    public Patient(String name, String sex, String address, String phoneNumber, String id, MedRecord medicalRecord, String healthInsuranceID, String bloodType, String occupation, String nationality, String religion, String birth, String age, boolean insuranceType) {
        this.name = name;
        this.sex = sex;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.medicalRecord = medicalRecord;
        this.healthInsuranceID = healthInsuranceID;
        this.bloodType = bloodType;
        this.occupation = occupation;
        this.citizenID = citizenID;
        this.religion = religion;
        this.birth = birth;
        this.age = age;
        this.insuranceType = insuranceType;
    }

    public Patient(String name, String phoneNumber, String age) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.age = age;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MedRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public String getHealthInsuranceID() {
        return healthInsuranceID;
    }

    public void setHealthInsuranceID(String healthInsuranceID) {
        this.healthInsuranceID = healthInsuranceID;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
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

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public boolean getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(boolean insuranceType) {
        this.insuranceType = insuranceType;
    }
}
