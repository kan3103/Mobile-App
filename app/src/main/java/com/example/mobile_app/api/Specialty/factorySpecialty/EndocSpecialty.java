package com.example.mobile_app.api.Specialty.factorySpecialty;

import com.example.mobile_app.api.Specialty.SpecialtyObject.Endocrinology;
import com.example.mobile_app.api.Specialty.SpecialtyObject.SpecialtyInter;

public class EndocSpecialty extends Specialty {
    @Override
    public SpecialtyInter createSpecialty(String name, String id, int doctorNum) {
        return new Endocrinology(name, id, doctorNum);
    }
}
