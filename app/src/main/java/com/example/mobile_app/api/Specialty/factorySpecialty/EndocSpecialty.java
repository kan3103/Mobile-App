package Specialty.factorySpecialty;

import Specialty.SpecialtyObject.Endocrinology;
import Specialty.SpecialtyObject.SpecialtyInter;

public class EndocSpecialty extends Specialty {
    @Override
    public SpecialtyInter createSpecialty(String name, String id, int doctorNum) {
        return new Endocrinology(name, id, doctorNum);
    }
}
