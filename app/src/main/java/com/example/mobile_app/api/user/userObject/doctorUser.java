package com.example.mobile_app.api.user.userObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class doctorUser implements userInterface, Serializable {
    private String username;
    private String password;
    private String typeuser;
    protected String ID;
    protected String Name;
    protected String Specialty;
    protected String Address;
    protected String Sex;
    protected String PhoneNum;
    protected String citizenID;
    protected String Experience;
    protected String nationality;
    protected ArrayList<patientUser> PatientList;
    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    protected int PatientNum;

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }


    protected String birthday;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public doctorUser(String username, String password) {
        this.username = username;
        this.password = password;
        this.typeuser = "Doctor";
    }

    public doctorUser(String username, String password, String ID, String name, String specialty, String address, String phoneNum, String citizenID, String experience, int patientNum, ArrayList<patientUser> patientList) {
        this.username = username;
        this.password = password;
        this.typeuser = "Doctor";
        this.ID = ID;
        Name = name;
        Specialty = specialty;
        Address = address;
        PhoneNum = phoneNum;
        this.citizenID = citizenID;
        Experience = experience;
        PatientNum = patientNum;
        PatientList = patientList;
    }
    public  doctorUser(String username,String name,String sex,String experience){
        this.username=username;
        this.typeuser="Doctor";
        this.Name = name;
        this.Sex = sex;
        this.Experience=experience;
    }
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSpecialty() {
        return Specialty;
    }

    public void setSpecialty(String specialty) {
        Specialty = specialty;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }

    public String getCitizenID() {
        return citizenID;
    }

    public void setCitizenID(String citizenID) {
        this.citizenID = citizenID;
    }


    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public int getPatientNum() {
        return PatientNum;
    }

    public void setPatientNum(int patientNum) {
        PatientNum = patientNum;
    }

    public ArrayList<patientUser> getPatientList() {
        return PatientList;
    }

    public void setPatientList(ArrayList<patientUser> patientList) {
        PatientList = patientList;
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
    public String getTypeuser(){return typeuser; };
}
