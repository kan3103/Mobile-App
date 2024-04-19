package com.example.mobile_app.ui.viewpatientlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mobile_app.Data.Doctor;
import com.example.mobile_app.R;
import com.example.mobile_app.api.user.userObject.doctorUser;
import com.example.mobile_app.api.user.userObject.patientUser;
import com.example.mobile_app.api.user.userObject.userInterface;

import org.bson.Document;

import java.io.Serializable;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.MongoCursor;

public class ViewDoctorsList extends AppCompatActivity {
    CustomAdapter adapter;
    PatientAdapter adapter1;
    RecyclerView recyclerView;
    ArrayList<Doctor> doctorList = new ArrayList<>();
    ArrayList<patientUser> patientList = new ArrayList<>();
    private userInterface userdoctor;
    boolean isUpdated;
    String Appid = "mobileapp-fyjbw";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    MongoCollection<Document> mongoCollection;
    private App app;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctors_list);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Intent intent = getIntent();
        // Start MongoDB service
        Realm.init(getApplicationContext());

        app = new App(new AppConfiguration.Builder(Appid).build());
        Credentials credentials = Credentials.emailPassword("khanglytronVN@KL.com", "123456");

        app.getEmailPassword().registerUserAsync("khanglytronVN@KL.com", "123456", it -> {
            if (it.isSuccess()) {
                Log.v("User", "Successfully registered user");
            } else {
                Log.v("User", "Failed to register user");
            }
        });
        app.loginAsync(credentials, new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> r) {
                user = app.currentUser();
                mongoClient = user.getMongoClient("mongodb-atlas");
                mongoDatabase = mongoClient.getDatabase("Hospital");
                mongoCollection = mongoDatabase.getCollection("Register");
                mongoCollection.find().iterator().getAsync(task -> {
                    if (task.isSuccess()) {
                        MongoCursor<Document> results = task.get();
                        while (results.hasNext()) {
                            Document currentDocument = results.next();

                            String username = currentDocument.getString("username");
                            String symptoms = currentDocument.getString("symptoms");
                            String name = currentDocument.getString("name");


                            // Create a new patientUser object and add it to the patientList
                            patientList.add((patientUser) new patientUser(username, symptoms, name));
                            System.out.println(patientList.get(0).toString());
                        }

                        // Set up the RecyclerView with the patientList
                        if (patientList != null)
                        {
                            adapter1 = new PatientAdapter(patientList, ViewDoctorsList.this, new PatientAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(patientUser patient) {
                                    Intent intent = new Intent(ViewDoctorsList.this, ApplyToHospital.class);
                                    intent.putExtra("patientInfor", (Serializable) patient);
                                    startActivity(intent);
                                }
                            });
                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(ViewDoctorsList.this));
                        recyclerView.setAdapter(adapter1);
                    } else {
                        Log.e("APP", "Failed to find documents with: ", task.getError());
                    }
                });
            }



//            Intent intent = getIntent();
//            if (intent != null) {
//                userdoctor = (userInterface) intent.getSerializableExtra("userobject");
//                if (userdoctor != null) {
//                    Log.v("test", ((doctorUser) userdoctor).getName());
//                }
//                patientList = ((doctorUser) userdoctor).getPatientList();
//            }
//            if (patientList != null) {
////            System.out.println(patientList.get(0).toString());
//                adapter = new PatientAdapter(patientList, ViewDoctorsList.this, new PatientAdapter.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(patientUser patient) {
//                        Intent intent = new Intent(ViewDoctorsList.this, ApplyToHospital.class);
//                        intent.putExtra("userobject", (doctorUser) userdoctor);
//                        intent.putExtra("patientInfor", (Serializable) patient);
//                        startActivity(intent);
//                    }
//                });
////            adapter = new PatientAdapter(patientList, ViewPatientsList.this, patient -> {
//////                                    System.out.println(patient.getName());
//
////                startActivity(intent1);
////            });
//                recyclerView.setLayoutManager(new LinearLayoutManager(ViewDoctorsList.this));
//                recyclerView.setAdapter(adapter);
//            }
        });
    }
}



