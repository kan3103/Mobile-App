package factoryDoc;
import doctor.DoctorInter;
import java.util.Vector;

public abstract class Doctor {
    public abstract DoctorInter createDoctor(String id, String name, String specialty, String address, String phoneNum, String citizenId, int experience, int patientNum, Vector<String> patientList);
}
