package factoryDoc;

import doctor.DoctorInter;
import doctor.CardioDoctor;
import doctor.EndocDoctor;
import doctor.OGDoctor;
import doctor.PediaDoctor;
import doctor.ENTDoctor;
import doctor.NeuroDoctor;
import java.util.Vector;

public class FactoryDoctor {
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