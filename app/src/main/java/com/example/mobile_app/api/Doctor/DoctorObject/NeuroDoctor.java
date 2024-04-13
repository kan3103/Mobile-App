package com.example.mobile_app.api.Doctor.DoctorObject;

import java.util.Vector;

public class NeuroDoctor implements DoctorInter {
    protected String Username;
    protected String Password;
    protected String ID;
    protected String Name;
    protected String Specialty;
    protected String typeuser;
    protected String Address;
    protected String PhoneNum;
    protected String citizenID;
    protected int Experience;
    protected int PatientNum;
    protected Vector<String> PatientList;

    public NeuroDoctor(String username, String password  ,String id, String name, String specialty, String address, String phoneNum, String citizenId, int experience, int patientNum, Vector<String> patientList) {
        this.Username = username;
        this.Password = password;
        this.ID = id;
        this.Name = name;
        this.Specialty = specialty;
        this.Address = address;
        this.PhoneNum = phoneNum;
        this.citizenID = citizenId;
        this.Experience = experience;
        this.PatientNum = patientNum;
        this.PatientList = patientList;
    }

    @Override
    public void setID(String id) {
        ID = id;
    }
    public String getID() {
        return ID;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getName() {
        return Name;
    }
    public void setAddress(String address) {
        Address = address;
    }
    public String getAddress() {
        return Address;
    }
    public void setSpecialty(String specialty) {
        Specialty = specialty;
    }
    public String getSpecialty() {
        return Specialty;
    }
    public void setExperience(int experience) {
        Experience = experience;
    }
    public int getExperience() {
        return Experience;
    }
    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }
    public String getPhoneNum() {
        return PhoneNum;
    }
    public void setCitizenId(String citizenId) {
        citizenID = citizenId;
    }
    public String getCitizenId() {
        return citizenID;
    }
    public void setPatientNum(int patientNum) {
        PatientNum = patientNum;
    }
    public int getPatientNum() {
        return PatientNum;
    }
    public void setPatientList(Vector<String> patientList) {
        PatientList = patientList;
    }
    public Vector<String> getPatientList() {
        return PatientList;
    }

    @Override
    public void setUsername(String username) {
        Username = username;
    }

    @Override
    public String getUsername() {
        return Username;
    }
    public void setUserPassword(String password) {
        this.Password = password;
    }
    public String getPassword() {
        return Password;
    }
    public String getTypeuser(){return typeuser; };
}
