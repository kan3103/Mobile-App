package Doctor.factoryDoc;

import Doctor.DoctorObject.DoctorInter;
import Doctor.DoctorObject.EndocDoctor;

import java.util.Vector;

public class EndocrinologyDoc extends Doctor {
    public DoctorInter createDoctor(String id, String name, String specialty, String address, String phoneNum, String citizenId, int experience, int patientNum, Vector<String> patientList){
        return new EndocDoctor(id, name, specialty, address, phoneNum, citizenId, experience, patientNum, patientList);
    }
}
