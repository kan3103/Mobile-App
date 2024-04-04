package com.example.mobile_app.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Toast;

import com.example.mobile_app.Data.Doctor;
import com.example.mobile_app.R;

import java.util.ArrayList;

public class ViewDoctorsList extends AppCompatActivity {
    CustomAdapter adapter;
    RecyclerView recyclerView;
    ArrayList <Doctor> doctorList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctors_list);

        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);

        Doctor doctor1 = new Doctor("Japan", "Alpha", "Beta", "a@abc.com", "Japan", "Alpha", "1", "Master", "Japan");
        Doctor doctor2 = new Doctor("Japan", "Alpha", "Beta", "a@abc.com", "Japan", "Alpha", "2", "Professional", "Japan");
        Doctor doctor3 = new Doctor("Japan", "Alpha", "Beta", "a@abc.com", "Japan", "Alpha", "3", "Doctor", "Japan");

        doctorList.add(doctor1);
        doctorList.add(doctor2);
        doctorList.add(doctor3);


        adapter = new CustomAdapter(doctorList, ViewDoctorsList.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewDoctorsList.this));
        recyclerView.setAdapter(adapter);
        System.out.println(recyclerView);
//        //Getting data:
//        DatabaseReference reference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://medi-consult-project-default-rtdb.firebaseio.com/");
//
//        reference.child("Doctors").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot data:snapshot.getChildren()){
//                    Doctor doctor=data.getValue(Doctor.class);
//                    if(!doctorList.contains(doctor)){
//                        doctorList.add(doctor);
//                    }
//                }
//                if(doctorList.isEmpty()){
//                    Toast.makeText(ViewDoctorsList.this, "Sorry,No doctors are availaible at this moment", Toast.LENGTH_LONG).show();
//                }
//                else {
//                    adapter = new CustomAdapter(doctorList, ViewDoctorsList.this);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(ViewDoctorsList.this));
//                    recyclerView.setAdapter(adapter);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(ViewDoctorsList.this,error.getCode(), Toast.LENGTH_LONG).show();
//            }
//        });

    }
}
