package com.example.mobile_app.api.Doctor.factoryDoc;

import com.example.mobile_app.api.Doctor.DoctorObject.DoctorInter;
import com.example.mobile_app.api.Doctor.DoctorObject.ENTDoctor;

import java.util.Vector;

public class ENTDoc extends Doctor {
    public DoctorInter createDoctor(String id, String name, String specialty, String address, String phoneNum, String citizenId, int experience, int patientNum, Vector<String> patientList){
        return new ENTDoctor(id, name, specialty, address, phoneNum, citizenId, experience, patientNum, patientList);
    }
}
