package com.example.mobile_app.ui.media_record;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

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
    private TextView name,ID,Birth,sex,nationality;
    private ViewFlipper vs1;
    private userInterface user;
    private MedRecord medRecord;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_medicalrecord);
        vs1 = findViewById(R.id.record);
        name = findViewById(R.id.recordName);
        ID = findViewById(R.id.recordId);
        Birth = findViewById(R.id.recordBirth);
        sex = findViewById(R.id.recordSex);
        nationality = findViewById(R.id.recordcitizenid);
        btn_prev = findViewById(R.id.button_prev);
        btn_next = findViewById(R.id.button_next);
        Intent intent = getIntent();
        if(intent!=null){
            user = (patientUser) intent.getSerializableExtra("userobject");
        }
        if (user!=null) {
            name.setText("Name: "+user.getName());
            ID.setText("Username: "+((patientUser)user).getUsername());
            Birth.setText("Birthday: "+((patientUser)user).getBirth());
            sex.setText("Sex: "+((patientUser)user).getSex());
            nationality.setText("Citizend ID: "+((patientUser)user).getCitizenID());
            medRecord = ((patientUser) user).getMedicalRecord();
        }
        if(medRecord!=null){
            for(int i=0;i<medRecord.getRecords().size();++i){
                MedRecord.Record record = medRecord.getRecords().get(i);
                CustomCardListView adapter = new CustomCardListView(this);
                adapter.setDataList(record);
                adapter.setBackgroundColor(Color.parseColor("#87CEFA"));
                vs1.addView(adapter);

            Log.v("oke","okeeee");
            }}

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vs1.getDisplayedChild()==vs1.getChildCount()-1) return;
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
                if(vs1.getDisplayedChild()==0) return;
                Animation inAnimation = AnimationUtils.loadAnimation(RecordActivity.this, android.R.anim.slide_in_left);
                Animation outAnimation = AnimationUtils.loadAnimation(RecordActivity.this, android.R.anim.slide_out_right);
                vs1.setInAnimation(inAnimation);
                vs1.setOutAnimation(outAnimation);
                vs1.showPrevious();
            }
        });
    }
}