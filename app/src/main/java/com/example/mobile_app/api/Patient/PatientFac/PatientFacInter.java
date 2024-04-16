package com.example.mobile_app.api.Patient.PatientFac;

import com.example.mobile_app.api.MedicalRecord.MedRecord;
import com.example.mobile_app.api.Patient.PatientObj.Patient;

public interface PatientFacInter {
    Patient createPatient(String name, String sex, String address, String phoneNumber, String id, MedRecord medicalRecord, String healthInsuranceID, String bloodType, String occupation, String citizenID, String religion, String birth, String age, boolean insuranceType);
}
