//import modules
package com.example.mobile_app.api.MedicalRecord;

//import com.example.mobile_app.api.Doctor.DoctorObject.DoctorInter;
import com.example.mobile_app.api.user.userObject.doctorUser;

import android.util.Pair;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.mobile_app.ui.media_record.Record_Data;
public class MedRecord implements Serializable {
    public void addRecord(Record_Data recordData) {
    }

    // inner class - record class
    public class Record implements Serializable {
        // attributes
        String testResults;
        ArrayList<String> prescriptions;
        String notes;
        String diagnosis;
        String specialty;
        ArrayList<String> medicalHistory;
        int bloodPressure;
        int heartRate;
        int temperature;
        String weight;
        String height;
        String doctor;
        String nurse;
        String date;
        String RevisionDate;


        // methods
        // constructor
        public Record(String weight, String height, String doctor,
                String nurse, String date, String RevisionDate,String specialty) {
            this.weight = weight;
            this.height = height;
            this.doctor = doctor;
            this.nurse = nurse;
            this.date = date;
            this.RevisionDate = RevisionDate;
            testResults = "";
            prescriptions = new ArrayList<String>();
            notes = "";
            diagnosis = "";
            this.specialty = specialty;
            medicalHistory = new ArrayList<String>();
        }

        public Record(String doctor, String date, String nurse) {
            this.doctor = doctor;
            this.nurse = nurse;
            this.date = date;
        }

        // setters
        public void addTestResult(String testName, boolean result) {
            testResults = testName;
        }
        public void setSpecialty(String specialty){
            this.specialty = specialty;
        }
        public void addPrescription(String prescription) {
            prescriptions.add(prescription);
        }

        public void addNote(String note) {
            notes = note;
        }

        public void setDiagnosis(String diagnosis) {
            this.diagnosis = diagnosis;
        }

        public void addMedicalHistory(String medicalHistory) {
            this.medicalHistory.add(medicalHistory);
        }

        public void setBloodPressure(int bloodPressure) {
            this.bloodPressure = bloodPressure;
        }

        public void setHeartRate(int heartRate) {
            this.heartRate = heartRate;
        }

        public void setTemperature(int temperature) {
            this.temperature = temperature;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public void setDoctor(String doctor) {
            this.doctor = doctor;
        }

        public void setNurse(String nurse) {
            this.nurse = nurse;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setRevisionDate(String RevisionDate) {
            this.RevisionDate = RevisionDate;
        }

        //getters
        public String getTestResults() {
            return testResults;
        }

        public String getSpecialty() {
            return specialty;
        }

        public ArrayList<String> getPrescriptions() {
            return prescriptions;
        }

        public String  getNotes() {
            return notes;
        }

        public String getDiagnosis() {
            return diagnosis;
        }

        public ArrayList<String> getMedicalHistory() {
            return medicalHistory;
        }

        public int getBloodPressure() {
            return bloodPressure;
        }

        public int getHeartRate() {
            return heartRate;
        }

        public int getTemperature() {
            return temperature;
        }

        public String  getWeight() {
            return weight;
        }

        public String getHeight() {
            return height;
        }

        public String getDoctor() {
            return doctor;
        }

        public String getNurse() {
            return nurse;
        }

        public String getDate() {
            return date;
        }
        public String getRevisionDate() {
            return RevisionDate;
        }

    }
    // attributes
    String name;
    String dob;
    boolean Gender;
    String address;
    String phoneNumber;
    String ethnicity;
    String bloodType;
    String citizenId;
    String insuranceId;
    ArrayList<MedRecord.Record> records;


    // methods
    // constructor
    public MedRecord(String name, String dob, String address, String phoneNumber, String ethnicity, String bloodType, String citizenId, String insuranceId) {
        this.name = name;
        this.dob = dob;
        this.Gender = Gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.ethnicity = ethnicity;
        this.bloodType = bloodType;
        this.citizenId = citizenId;
        this.insuranceId = insuranceId;
        records = new ArrayList<Record>();
    }

    // setters
    public void addRecord(String weight, String height,
            String doctor, String nurse, String date, String RevisionDate,String specialty) {
        records.add(new Record(weight, height, doctor, nurse, date, RevisionDate,specialty));
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setGender(boolean Gender) {
        this.Gender = Gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
    }

    public void setInsuranceId(String insuranceId) {
        this.insuranceId = insuranceId;
    }

    public ArrayList<MedRecord.Record> getRecords() {
        return records;
    }

    public void addRecord(String doctor, String date_out, String nurse) {
        Record newRecord = new Record(doctor, date_out, nurse);
        newRecord.setDoctor(doctor);
        newRecord.setDate(date_out);
        newRecord.setNurse(nurse);
        // set other fields of newRecord based on the fields of record
        records.add(newRecord);
    }
}

