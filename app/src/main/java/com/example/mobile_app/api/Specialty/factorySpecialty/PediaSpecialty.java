package com.example.mobile_app.api.Specialty.factorySpecialty;

import com.example.mobile_app.api.Specialty.SpecialtyObject.Pediatrics;
import com.example.mobile_app.api.Specialty.SpecialtyObject.SpecialtyInter;

public class PediaSpecialty extends Specialty {
    @Override
    public SpecialtyInter createSpecialty(String name, String id, int doctorNum) {
        return new Pediatrics(name, id, doctorNum);
    }
}
