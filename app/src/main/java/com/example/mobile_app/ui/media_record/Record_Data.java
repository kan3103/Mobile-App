package com.example.mobile_app.ui.media_record;

import com.example.mobile_app.api.user.userObject.userInterface;

public class Record_Data {
    private userInterface user;
    private String date;
    private String Doctor;
    private String Nurse;
    private String Dia;
    private String Blood;
    private String Height;
    private String Weight;
    private String Re_ex;
    private String Test_result;
    private String Note;
    private String Relatives;
    private String Department;
    private String Health_record;
    private String date_in;
    private String date_out;


    public String getDate_in() {
        return date_in;
    }

    public String getDate_out() {
        return date_out;
    }

    public void setUser(userInterface user) {
        this.user = user;
    }

    public userInterface getUser() {
        return user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoctor() {
        return Doctor;
    }

    public void setDoctor(String doctor) {
        Doctor = doctor;
    }

    public String getNurse() {
        return Nurse;
    }

    public void setNurse(String nurse) {
        Nurse = nurse;
    }

    public String getDia() {
        return Dia;
    }

    public void setDia(String dia) {
        Dia = dia;
    }

    public String getBlood() {
        return Blood;
    }

    public void setBlood(String blood) {
        Blood = blood;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getRe_ex() {
        return Re_ex;
    }

    public void setRe_ex(String re_ex) {
        Re_ex = re_ex;
    }

    public String getTest_result() {
        return Test_result;
    }

    public void setTest_result(String test_result) {
        Test_result = test_result;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getRelatives() {
        return Relatives;
    }

    public void setRelatives(String relatives) {
        Relatives = relatives;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getHealth_record() {
        return Health_record;
    }

    public void setHealth_record(String health_record) {
        Health_record = health_record;
    }
}
