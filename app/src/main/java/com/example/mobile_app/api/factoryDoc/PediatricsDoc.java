package factoryDoc;

import doctor.PediaDoctor;
import doctor.DoctorInter;

import java.util.Vector;

public class PediatricsDoc extends Doctor{
    public DoctorInter createDoctor(String id, String name, String specialty, String address, String phoneNum, String citizenId, int experience, int patientNum, Vector<String> patientList){
        return new PediaDoctor(id, name, specialty, address, phoneNum, citizenId, experience, patientNum, patientList);
    }
}
