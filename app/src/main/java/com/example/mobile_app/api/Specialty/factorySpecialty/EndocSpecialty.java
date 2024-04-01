package factorySpecialty;

import specialty.SpecialtyInter;
import specialty.Endocrinology;

public class EndocSpecialty extends Specialty {
    @Override
    public SpecialtyInter createSpecialty(String name, String id, int doctorNum) {
        return new Endocrinology(name, id, doctorNum);
    }
}
