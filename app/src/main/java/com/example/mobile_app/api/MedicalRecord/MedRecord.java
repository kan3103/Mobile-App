//import modules
package com.example.mobile_app.api.MedicalRecord;

import com.example.mobile_app.api.Doctor.DoctorObject.DoctorInter;
import com.example.mobile_app.api.user.userObject.doctorUser;

import android.util.Pair;

import java.util.ArrayList;

public class MedRecord {
    // inner class - record class
    public class Record {
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
        doctorUser doctor;
        doctorUser nurse;
        String date;
        String room;
        String RevisionDate;

        // methods
        // constructor
        public Record(int bloodPressure, int heartRate, int temperature, double weight, double height, doctorUser doctor,
                doctorUser nurse, String date, String room, String RevisionDate) {
            this.bloodPressure = bloodPressure;
            this.heartRate = heartRate;
            this.temperature = temperature;
            this.weight = weight;
            this.height = height;
            this.doctor = doctor;
            this.nurse = nurse;
            this.date = date;
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

        public void setDoctor(doctorUser doctor) {
            this.doctor = doctor;
        }

        public void setNurse(doctorUser nurse) {
            this.nurse = nurse;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public void setRevisionDate(String RevisionDate) {
            this.RevisionDate = RevisionDate;
        }

        //getters
        public ArrayList<Pair<String, Boolean>> getTestResults() {
            return testResults;
        }

        public ArrayList<String> getPrescriptions() {
            return prescriptions;
        }

        public ArrayList<String> getNotes() {
            return notes;
        }

        public ArrayList<String> getDiagnosis() {
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

        public double getWeight() {
            return weight;
        }

        public double getHeight() {
            return height;
        }

        public doctorUser getDoctor() {
            return doctor;
        }

        public doctorUser getNurse() {
            return nurse;
        }

        public String getDate() {
            return date;
        }

        public String getRoom() {
            return room;
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
    ArrayList<Record> records;

    // methods
    // constructor
    MedRecord(String name, String dob, boolean Gender, String address, String phoneNumber, String ethnicity, String bloodType, String citizenId, String insuranceId) {
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
            doctorUser doctor, doctorUser nurse, String date, String room, String RevisionDate) {
        records.add(new Record(bloodPressure, heartRate, temperature, weight, height, doctor, nurse, date, room, RevisionDate));
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