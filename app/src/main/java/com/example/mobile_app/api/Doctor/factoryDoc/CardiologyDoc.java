package factoryDoc;

import doctor.DoctorInter;
import doctor.CardioDoctor;

import java.util.Vector;

public class CardiologyDoc extends Doctor{
    public DoctorInter createDoctor(String id, String name, String specialty, String address, String phoneNum, String citizenId, int experience, int patientNum, Vector<String> patientList){
        return new CardioDoctor(id, name, specialty, address, phoneNum, citizenId, experience, patientNum, patientList);
    }
}
