package com.example.mobile_app.api.Specialty.factorySpecialty;

import com.example.mobile_app.api.Specialty.SpecialtyObject.SpecialtyInter;


public abstract class Specialty {
    public abstract SpecialtyInter createSpecialty(String name, String id, int doctorNum);
}
