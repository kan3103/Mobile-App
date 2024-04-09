package com.example.mobile_app.api.Patient.PatientFac;

import com.example.mobile_app.api.MedicalRecord.MedRecord;
import com.example.mobile_app.api.Patient.PatientObj.Patient;

public interface PatientFacInter {
    Patient createPatient(String name, String sex, String address, String phoneNumber, String id, MedRecord medicalRecord, String healthInsuranceID, String bloodType, String occupation, String nationality, String religion, String birth, int age, boolean insuranceType);
}
