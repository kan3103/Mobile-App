package com.example.mobile_app.ui.home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.mobile_app.R;
import com.example.mobile_app.api.Specialty.SpecialtyObject.ENT;
import com.example.mobile_app.api.Specialty.SpecialtyObject.Neurology;
import com.example.mobile_app.api.Specialty.SpecialtyObject.Pediatrics;

public class HomeListActivity extends AppCompatActivity {
    private CardView Cardiology, Endocrinology,ENT, Neurology, Pediatrics, Obstetrics;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_list);
        Cardiology = findViewById(R.id.cardio);
        Endocrinology = findViewById(R.id.endoc);
        ENT = findViewById(R.id.ear);
        Neurology = findViewById(R.id.neur);
        Pediatrics = findViewById(R.id.pedia);
        Obstetrics = findViewById(R.id.obs);
    }
}
