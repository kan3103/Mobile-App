//import modules
package com.example.mobile_app.api.MedicalRecord;

import java.util.ArrayList;
import com.example.mobile_app.api.MedicalRecord.Pair;

public class MedRecord {
    // inner class - record class
    class Record {
        // attributes
        ArrayList<Pair<String, Boolean>> testResults;
        ArrayList<String> prescriptions;
        ArrayList<String> notes;
        ArrayList<String> diagnosis;
        ArrayList<String> medicalHistory;
        int bloodPressure;
        int heartRate;
        int temperature;
        double weight;
        double height;
        String doctor; // refer to doctor class
        String nurse; // refer to nurse class
        String dateIn;
        String dateOut;
        String room;
        String RevisionDate;

        // methods
        // constructor
        public Record(int bloodPressure, int heartRate, int temperature, double weight, double height, String doctor,
                String nurse, String dateIn, String dateOut, String room, String RevisionDate) {
            this.bloodPressure = bloodPressure;
            this.heartRate = heartRate;
            this.temperature = temperature;
            this.weight = weight;
            this.height = height;
            this.doctor = doctor;
            this.nurse = nurse;
            this.dateIn = dateIn;
            this.dateOut = dateOut;
            this.room = room;
            this.RevisionDate = RevisionDate;
            testResults = new ArrayList<Pair<String, Boolean>>();
            prescriptions = new ArrayList<String>();
            notes = new ArrayList<String>();
            diagnosis = new ArrayList<String>();
            medicalHistory = new ArrayList<String>();
        }

        // setters
        public void addTestResult(String testName, boolean result) {
            testResults.add(new Pair<String, Boolean>(testName, result));
        }

        public void addPrescription(String prescription) {
            prescriptions.add(prescription);
        }

        public void addNote(String note) {
            notes.add(note);
        }

        public void addDiagnosis(String diagnosis) {
            this.diagnosis.add(diagnosis);
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

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public void setDoctor(String doctor) {
            this.doctor = doctor;
        }

        public void setNurse(String nurse) {
            this.nurse = nurse;
        }

        public void setDateIn(String dateIn) {
            this.dateIn = dateIn;
        }

        public void setDateOut(String dateOut) {
            this.dateOut = dateOut;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public void setRevisionDate(String RevisionDate) {
            this.RevisionDate = RevisionDate;
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
    ArrayList<Record> records;

    // methods
    // constructor
    public MedRecord(String name, String dob, boolean Gender, String address, String phoneNumber, String ethnicity,
            String bloodType, String citizenId, String insuranceId) {
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
    public void addRecord(int bloodPressure, int heartRate, int temperature, double weight, double height,
            String doctor, String nurse, String dateIn, String dateOut, String room, String RevisionDate) {
        records.add(new Record(bloodPressure, heartRate, temperature, weight, height, doctor, nurse, dateIn, dateOut,
                room, RevisionDate));
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

    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
    }

    public void setInsuranceId(String insuranceId) {
        this.insuranceId = insuranceId;
    }

}