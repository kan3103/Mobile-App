package com.example.mobile_app.ui.viewpatientlist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_app.Data.Doctor;
import com.example.mobile_app.R;
import com.example.mobile_app.api.user.userObject.adminUser;
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

public class ViewPatientsList extends AppCompatActivity {
    PatientAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<patientUser> patientList = new ArrayList<>();
    String Appid = "mobileapp-fyjbw";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    MongoCollection<Document> mongoCollection;
    boolean isUpdated;
    private App app;
    private User user;
    private userInterface userdoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_list);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // Start MongoDB service
        Intent intent = getIntent();
        if(intent != null) {
            userdoctor = (userInterface) intent.getSerializableExtra("userobject");
            if(userdoctor!=null){
                Log.v("test",((doctorUser)userdoctor).getName());
            }
            patientList = ((doctorUser) userdoctor).getPatientList();
        }
        if(patientList!=null) {
            adapter = new PatientAdapter(patientList, ViewPatientsList.this, new PatientAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(patientUser patient) {
//                                    System.out.println(patient.getName());
                    Intent intent = new Intent(ViewPatientsList.this, ExitHospitalActivity.class);
                    intent.putExtra("userobject",(doctorUser) userdoctor);
                    startActivity(intent);
                }
            });

//                            System.out.println(patientList);

            recyclerView.setLayoutManager(new LinearLayoutManager(ViewPatientsList.this));
            recyclerView.setAdapter(adapter);
        }
//        Realm.init(getApplicationContext());
//
//        app = new App(new AppConfiguration.Builder(Appid).build());
//        Credentials credentials = Credentials.emailPassword("khanglytronVN@KL.com", "123456");
//
//        app.getEmailPassword().registerUserAsync("khanglytronVN@KL.com", "123456", it -> {
//            if (it.isSuccess()) {
//                Log.v("User", "Successfully registered user");
//            } else {
//                Log.v("User", "Failed to register user");
//            }
//        });
//
//        app.loginAsync(credentials, new App.Callback<User>() {
//            @Override
//            public void onResult(App.Result<User> r) {
//                user = app.currentUser();
//                mongoClient = user.getMongoClient("mongodb-atlas");
//                mongoDatabase = mongoClient.getDatabase("Hospital");
//                mongoCollection = mongoDatabase.getCollection("Doctor");
//
//                Document queryFilter = new Document().append("patientList", new Document("$exists", true));
//                mongoCollection.findOne(queryFilter).getAsync(result -> {
//                    if (result.isSuccess()) {
//                        Toast.makeText(getApplicationContext(), "Found", Toast.LENGTH_LONG).show();
//                        Document resultData = result.get();
//                        Log.v("Data Success", resultData.toString());
//                        if (resultData.containsKey("patientList")) {
//                            ArrayList<Document> arrList = (ArrayList<Document>) resultData.get("patientList");
//                            for (Document patient : arrList) {
////                                System.out.println(patient);
//
////                                String patientDoc = patient.toJson();
////                                System.out.println(patientDoc);
//
//                                String name = patient.getString("name");
//                                String age = patient.getString("age");
//                                String phoneNumber = patient.getString("phoneNumber");
//                                boolean isUpdated = patient.getBoolean("status").booleanValue();
//                                String id = patient.getString("id");
//
//                                System.out.println(patient);
//                                System.out.println(patient.getBoolean("status"));
////                                boolean isUpdated = isUpdatedObject != null ? isUpdatedObject : false;//                                Kiem tra field boolean updated trong patient
//                                if (isUpdated == false) {
//                                    patientList.add(new patientUser(name, age, phoneNumber, isUpdated, id));
//                                }
//
//                            }
//                            adapter = new PatientAdapter(patientList, ViewPatientsList.this, new PatientAdapter.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(patientUser patient) {
////                                    System.out.println(patient.getName());
//                                    Intent intent = new Intent(ViewPatientsList.this, ExitHospitalActivity.class);
//                                    intent.putExtra("patientInfor", (Serializable) patient);
//                                    startActivity(intent);
//                                }
//                            });
//
////                            System.out.println(patientList);
//
//                            recyclerView.setLayoutManager(new LinearLayoutManager(ViewPatientsList.this));
//                            recyclerView.setAdapter(adapter);
//
//                        } else {
//                            Log.v("Data Success", "Document does not contain an 'arr' field");
//                        }
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Not Found", Toast.LENGTH_LONG).show();
//                        Log.v("Data Error", result.getError().toString());
//                    }
//                });
////                System.out.println(patientList);
//            }
//
//        });
//
//        System.out.println("Load list of patients");
    }


    private void getPatientFromDB() {
        // Find all data in array

        Document queryFilter = new Document().append("arr", new Document("$exists", true));
        mongoCollection.findOne(queryFilter).getAsync(result -> {
            if (result.isSuccess()) {
                Toast.makeText(getApplicationContext(), "Found", Toast.LENGTH_LONG).show();
                Document resultData = result.get();
                Log.v("Data Success", resultData.toString());
                if (resultData.containsKey("arr")) {
                    ArrayList<Document> arrList = (ArrayList<Document>) resultData.get("arr");
                    for (Document patient : arrList) {
                        System.out.println(patient);
                    }
//                    textView.setText(arrList.toString());
                } else {
                    Log.v("Data Success", "Document does not contain an 'arr' field");
                }
            } else {
                Toast.makeText(getApplicationContext(), "Not Found", Toast.LENGTH_LONG).show();
                Log.v("Data Error", result.getError().toString());
            }
        });
    }
}
