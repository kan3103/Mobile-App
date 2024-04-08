package com.example.mobile_app.api.Doctor.factoryDoc;

import com.example.mobile_app.api.Doctor.DoctorObject.CardioDoctor;
import com.example.mobile_app.api.Doctor.DoctorObject.DoctorInter;
import com.example.mobile_app.api.Doctor.DoctorObject.ENTDoctor;
import com.example.mobile_app.api.Doctor.DoctorObject.EndocDoctor;
import com.example.mobile_app.api.Doctor.DoctorObject.NeuroDoctor;
import com.example.mobile_app.api.Doctor.DoctorObject.OGDoctor;
import com.example.mobile_app.api.Doctor.DoctorObject.PediaDoctor;

import java.util.Vector;

public class FactoryDoctor {
    public DoctorInter createDoctor(String username, String password,String id, String name, String specialty, String address, String phoneNum, String citizenId, int experience, int patientNum, Vector<String> patientList) {
        {
            if (specialty.equals("Endocrinology")) {
                return new EndocDoctor(username, password, id, name, specialty, address, phoneNum, citizenId, experience, patientNum, patientList);
            } else if (specialty.equals("Cardiology")) {
                return new CardioDoctor(username, password, id, name, specialty, address, phoneNum, citizenId, experience, patientNum, patientList);
            } else if (specialty.equals("OG")) {
                return new OGDoctor(username, password, id, name, specialty, address, phoneNum, citizenId, experience, patientNum, patientList);
            } else if (specialty.equals("Pediatrics")) {
                return new PediaDoctor(username, password, id, name, specialty, address, phoneNum, citizenId, experience, patientNum, patientList);
            } else if (specialty.equals("ENT")) {
                return new ENTDoctor(username, password, id, name, specialty, address, phoneNum, citizenId, experience, patientNum, patientList);
            } else if (specialty.equals("Neurology")) {
                return new NeuroDoctor(username, password, id, name, specialty, address, phoneNum, citizenId, experience, patientNum, patientList);
            }
            // Add more types of doctors as needed
            else {
                System.out.println("Invalid doctor type");
            }
            return null;
        }
    }
}