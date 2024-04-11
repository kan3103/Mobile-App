package com.example.mobile_app.api.Specialty.factorySpecialty;

import com.example.mobile_app.api.Specialty.SpecialtyObject.OG;
import com.example.mobile_app.api.Specialty.SpecialtyObject.SpecialtyInter;

public class OGSpecialty extends Specialty {
    @Override
    public SpecialtyInter createSpecialty(String name, String id, int doctorNum) {
        return new OG(name, id, doctorNum);
    }
}
