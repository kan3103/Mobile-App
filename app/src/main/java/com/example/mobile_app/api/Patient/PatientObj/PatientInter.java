package com.example.mobile_app.api.Patient.PatientObj;

import com.example.mobile_app.api.MedicalRecord.MedRecord;

public interface PatientInter {
    void setName(String name);
    String getName();
    void setSex(String sex);
    String getSex();
    void setAddress(String address);
    String getAddress();
    void setPhoneNumber(String phoneNumber);
    String getPhoneNumber();
    void setId(String id);
    String getId();
    void setMedicalRecord(MedRecord medicalRecord);
    MedRecord getMedicalRecord();
    void setHealthInsuranceID(String healthInsuranceID);
    String getHealthInsuranceID();
    void setBloodType(String bloodType);
    String getBloodType();
    void setOccupation(String occupation);
    String getOccupation();
    void setcitizenID(String nationality);
    String getcitizenID();
    void setReligion(String religion);
    String getReligion();
    void setBirth(String birth);
    String getBirth();
    void setAge(int age);
    String getAge();
    void setInsuranceType(boolean insuranceType);
    boolean getInsuranceType();
}