package com.example.mobile_app.ui.media_record;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ViewSwitcher;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_app.R;
import com.example.mobile_app.api.MedicalRecord.MedRecord;
import com.example.mobile_app.api.user.userObject.patientUser;
import com.example.mobile_app.api.user.userObject.userInterface;

import java.util.ArrayList;

public class RecordActivity extends AppCompatActivity {
    private Button btn_next;
    private Button btn_prev;
    private ViewSwitcher vs1;
    private userInterface user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_medicalrecord);
        vs1 = findViewById(R.id.record);
        btn_prev = findViewById(R.id.button_prev);
        btn_next = findViewById(R.id.button_next);
        Intent intent = getIntent();
        if(intent!=null){
            user = (patientUser) intent.getSerializableExtra("userobject");
        }
        MedRecord medRecord = ((patientUser) user).getMedicalRecord();

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("ResourceType") Animation animation = AnimationUtils.loadAnimation(RecordActivity.this, R.xml.slide_in_right);
                @SuppressLint("ResourceType") Animation animation1 = AnimationUtils.loadAnimation(RecordActivity.this, R.xml.slide_out_left);
                vs1.setInAnimation(animation);
                vs1.setOutAnimation(animation1);
                vs1.showNext();
            }
        });
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation inAnimation = AnimationUtils.loadAnimation(RecordActivity.this, android.R.anim.slide_in_left);
                Animation outAnimation = AnimationUtils.loadAnimation(RecordActivity.this, android.R.anim.slide_out_right);
                vs1.setInAnimation(inAnimation);
                vs1.setOutAnimation(outAnimation);
                vs1.showPrevious();
            }
        });
    }
}
