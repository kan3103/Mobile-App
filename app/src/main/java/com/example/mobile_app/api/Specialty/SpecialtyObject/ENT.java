package com.example.mobile_app.api.Specialty.SpecialtyObject;

public class ENT implements SpecialtyInter {
    private String name;
    private String id;
    private int doctorNum;

    public ENT(String name, String id, int doctorNum) {
        this.name = name;
        this.id = id;
        this.doctorNum = doctorNum;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setDoctorNum(int doctorNum) {
        this.doctorNum = doctorNum;
    }

    @Override
    public int getDoctorNum() {
        return doctorNum;
    }
}
