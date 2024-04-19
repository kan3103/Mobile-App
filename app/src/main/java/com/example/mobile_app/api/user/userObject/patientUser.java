package com.example.mobile_app.api.user.userObject;

import com.example.mobile_app.api.MedicalRecord.MedRecord;

import java.io.Serializable;

public class patientUser implements userInterface, Serializable {
    private String username;
    private String password;
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
    private boolean status ;

    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status){this.status = status;}
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
    }
    public patientUser(String name, String age, String phoneNumber)
    {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        symptoms ="";
    }
    public patientUser(String name, String age, String phoneNumber,String symptoms)
    {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.symptoms = symptoms;
    }
    public patientUser(String name, String age, String phoneNumber, boolean status, String id)
    {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.id = id;
    }
    public  patientUser(String username,String name, String age,boolean status){
        this.username = username;
        this.name = name;
        this.age = age;
        this.status=status;
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
    public String getTypeuser(){return "Patient"; };
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

    @Override
    public String toString() {
        return "patientUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", symptoms='" + symptoms + '\'' +
                ", medicalRecord=" + medicalRecord +
                ", id='" + id + '\'' +
                ", age='" + age + '\'' +
                ", name='" + name + '\'' +
                ", citizenID='" + citizenID + '\'' +
                ", religion='" + religion + '\'' +
                ", sex='" + sex + '\'' +
                ", nationality='" + nationality + '\'' +
                ", bloodType='" + bloodType + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", healthInsuranceID='" + healthInsuranceID + '\'' +
                ", occupation='" + occupation + '\'' +
                ", address='" + address + '\'' +
                ", birth='" + birth + '\'' +
                ", status=" + status +
                '}';
    }
}