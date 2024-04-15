package com.example.mobile_app.api.Specialty.factorySpecialty;

import com.example.mobile_app.api.Specialty.SpecialtyObject.ENT;
import com.example.mobile_app.api.Specialty.SpecialtyObject.SpecialtyInter;

public class ENTSpecialty extends Specialty {
    @Override
    public SpecialtyInter createSpecialty(String name, String id, int doctorNum) {
        return new ENT(name, id, doctorNum);
    }
}
