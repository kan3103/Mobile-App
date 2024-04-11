package com.example.mobile_app.api.Specialty.factorySpecialty;

import com.example.mobile_app.api.Specialty.SpecialtyObject.Cardiology;
import com.example.mobile_app.api.Specialty.SpecialtyObject.SpecialtyInter;

public class CardioSpecialty extends Specialty {
    @Override
    public SpecialtyInter createSpecialty(String name, String id, int doctorNum) {
        return new Cardiology(name, id, doctorNum);
    }
}
