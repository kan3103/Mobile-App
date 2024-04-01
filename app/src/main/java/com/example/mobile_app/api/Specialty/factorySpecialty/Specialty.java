package factorySpecialty;

import specialty.SpecialtyInter;


public abstract class Specialty {
    public abstract SpecialtyInter createSpecialty(String name, String id, int doctorNum);
}
