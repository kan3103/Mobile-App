package com.example.mobile_app.api.Doctor.DoctorObject;

import java.util.Vector;
import user.userObject.userInterface;

public interface DoctorInter extends userInterface {
    void setID(String id);
    String getID();
    void setName(String name);
    String getName();
    void setAddress(String address);
    String getAddress();
    void setSpecialty(String specialty);
    String getSpecialty();
    void setExperience(int experience);
    int getExperience();
    void setPhoneNum(String phoneNum);
    String getPhoneNum();
    void setCitizenId(String citizenId);
    String getCitizenId();
    void setPatientNum(int patientNum);
    int getPatientNum();
    void setPatientList(Vector<String> patientList);
    Vector<String> getPatientList();
}
