package Specialty.factorySpecialty;

import Specialty.SpecialtyObject.SpecialtyInter;


public abstract class Specialty {
    public abstract SpecialtyInter createSpecialty(String name, String id, int doctorNum);
}
