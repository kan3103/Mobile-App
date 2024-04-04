package com.example.mobile_app.api.Doctor.factoryDoc;

import java.util.Vector;

import com.example.mobile_app.api.Doctor.DoctorObject.CardioDoctor;
import com.example.mobile_app.api.Doctor.DoctorObject.DoctorInter;
import com.example.mobile_app.api.Doctor.DoctorObject.ENTDoctor;
import com.example.mobile_app.api.Doctor.DoctorObject.EndocDoctor;
import com.example.mobile_app.api.Doctor.DoctorObject.NeuroDoctor;
import com.example.mobile_app.api.Doctor.DoctorObject.OGDoctor;
import com.example.mobile_app.api.Doctor.DoctorObject.PediaDoctor;

public class FactoryDoctor extends EndocDoctor {
    public FactoryDoctor(String id, String name, String specialty, String address, String phoneNum, String citizenId, int experience, int patientNum, Vector<String> patientList) {
        super(id, name, specialty, address, phoneNum, citizenId, experience, patientNum, patientList);
    }

    public DoctorInter createDoctor(String id, String name, String specialty, String address, String phoneNum, String citizenId, int experience, int patientNum, Vector<String> patientList) {
        {
            if (specialty.equals("Endocrinology")) {
                return new EndocDoctor(id, name, specialty, address, phoneNum, citizenId, experience, patientNum, patientList);
            } else if (specialty.equals("Cardiology")) {
                return new CardioDoctor(id, name, specialty, address, phoneNum, citizenId, experience, patientNum, patientList);
            } else if (specialty.equals("OG")) {
                return new OGDoctor(id, name, specialty, address, phoneNum, citizenId, experience, patientNum, patientList);
            } else if (specialty.equals("Pediatrics")) {
                return new PediaDoctor(id, name, specialty, address, phoneNum, citizenId, experience, patientNum, patientList);
            } else if (specialty.equals("ENT")) {
                return new ENTDoctor(id, name, specialty, address, phoneNum, citizenId, experience, patientNum, patientList);
            } else if (specialty.equals("Neurology")) {
                return new NeuroDoctor(id, name, specialty, address, phoneNum, citizenId, experience, patientNum, patientList);
            }
            // Add more types of doctors as needed
            else {
                System.out.println("Invalid doctor type");
            }
            return null;
        }
    }
}