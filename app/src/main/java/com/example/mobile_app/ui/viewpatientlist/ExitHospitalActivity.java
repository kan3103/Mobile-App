package com.example.mobile_app.ui.viewpatientlist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_app.LoginActivity;
import com.example.mobile_app.MainActivity;
import com.example.mobile_app.R;
import com.example.mobile_app.api.user.userObject.doctorUser;
import com.example.mobile_app.api.user.userObject.patientUser;
import com.example.mobile_app.api.user.userObject.userInterface;
import com.example.mobile_app.databinding.ExitHospitalFormBinding;
import com.example.mobile_app.ui.media_record.Record_Data;
import com.example.mobile_app.ui.register.RegisterActivity;
import com.example.mobile_app.api.MedicalRecord.MedRecord;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.options.UpdateOptions;

public class ExitHospitalActivity extends AppCompatActivity {
    public boolean isUpdated;
    String Appid = "mobileapp-fyjbw";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    MongoCollection<Document> mongoCollection;
    patientUser patient;
    ArrayList<patientUser> patientList = new ArrayList<>();
    private ExitHospitalFormBinding binding;
    private Button btn2;
    private EditText name, id, dateOut, nurse;
    private User user;
    //    public boolean isUpdated;
    private ProgressBar progressBar;
    private App app;
    //    patientUser patient;
    private userInterface userdoctor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ExitHospitalFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        name = findViewById(R.id.board_name);
        id = findViewById(R.id.board_id);
        dateOut = findViewById(R.id.board_dateOut);
        nurse = findViewById(R.id.board_nurse);
        btn2 = (Button) findViewById(R.id.board_xacnhan);
        Intent intent = getIntent();
        if (intent != null) {
            userdoctor = (userInterface) intent.getSerializableExtra("userobject");
            if (userdoctor != null) {
                Log.v("test", ((doctorUser) userdoctor).getName());
            }

        }
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


        System.out.println("Register");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            patient = (patientUser) extras.getSerializable("patientInfor");
//            System.out.println(patient.toString());
//            System.out.println(extras);
        }

        id.setText(patient.getId());
        id.setFocusable(false);
        name.setText(patient.getName());
        name.setFocusable(false);

        app.loginAsync(credentials, new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                user = app.currentUser();
                mongoClient = user.getMongoClient("mongodb-atlas");
                mongoDatabase = mongoClient.getDatabase("Hospital");
                mongoCollection = mongoDatabase.getCollection("Patient");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillPatientInform();
            }
        });

    }

    private void fillPatientInform() {
        //Inputs
        String patientName = name.getText().toString().trim();
        String ID = id.getText().toString().trim();
        String DateOut = dateOut.getText().toString().trim();
        String Nurse = nurse.getText().toString().trim();

//        progressBar.setVisibility(View.VISIBLE);
        String doctorName = ((doctorUser) userdoctor).getName();

        Record_Data record_data = new Record_Data(doctorName, Nurse, DateOut);
        record_data.setDate_out(DateOut);
        record_data.setNurse(Nurse);
        record_data.setDoctor(doctorName);

//        MedRecord newMedRecord = new MedRecord(patientName, "", "", "", "", "", "", patient.getId());
        User user2 = app.currentUser();
        MongoClient mongoClient2 = user2.getMongoClient("mongodb-atlas");
        MongoDatabase mongoDatabase2 = mongoClient2.getDatabase("Hospital");
        MongoCollection<Document> mongoCollection2 = mongoDatabase2.getCollection("Record");

//        MedRecord[] med = new MedRecord[1];
        List<MedRecord> med = new ArrayList<>();
        med.add(new MedRecord(patientName, "", "", "", "", "", "", patient.getId()));
        Document f = new Document().append("id_patient", "10");

        mongoCollection2.findOne(f).getAsync(result -> {
            if (result.isSuccess()) {
                Toast.makeText(getApplicationContext(), "Found", Toast.LENGTH_LONG).show();
                Document resultData = result.get();
                Log.v("Data Success", resultData.toString());

                if (resultData.containsKey("record")) {
                    ArrayList<Document> arrList = (ArrayList<Document>) resultData.get("record");
                    String[] patientInform = new String[5];
                    assert arrList != null;
                    for (Document document : arrList) {
                        patientInform[0] = document.getString("date_in");
                        patientInform[1] = document.getString("date_out");
                        patientInform[2] = document.getString("doctor");
                        patientInform[3] = document.getString("nurse");
                        patientInform[4] = document.getString("Diagnose");
                        med.get(0).addRecord(patientInform[2], patientInform[1], patientInform[3]);
                        System.out.println(med.get(0).getRecords().get(0).getNurse());
                        System.out.println(med.get(0).getRecords().size());
                        System.out.println("med[0].getRecords().size()");
                    }
//                            med[0] = new MedRecord(weight, height, doctor, nurse, date, RevisionDate, specialty);
//                            System.out.println(med[0].toString());
                } else {
                    Log.v("Data Success", "Document does not contain an 'arr' field");
                }
            } else {
                Toast.makeText(getApplicationContext(), "Not Found", Toast.LENGTH_LONG).show();
                Log.v("Data Error", result.getError().toString());
            }
        });

        System.out.print("med[0].getRecords().size(): ");
        System.out.println(med.get(0).getRecords().size());
        med.get(0).addRecord(doctorName, DateOut, Nurse);

        System.out.print("med[0].getRecords(): ");
        System.out.println(med.get(0).getRecords().size());
        System.out.println(med.get(0).getRecords().get(0).getNurse());
        patient.setStatus(true);

        for (patientUser patient : ((doctorUser) userdoctor).getPatientList()) {
            if (patient.getId().equals(ID)) {
                patient.setStatus(true);
                break;
            }
        }

        Log.v("updating", "updating");
// Get the current user and the MongoDB client
        User user1 = app.currentUser();
        MongoClient mongoClient1 = user1.getMongoClient("mongodb-atlas");

// Get the "Hospital" database and the "Doctor" collection
        MongoDatabase mongoDatabase1 = mongoClient1.getDatabase("Hospital");
        MongoCollection<Document> mongoCollection1 = mongoDatabase1.getCollection("Doctor");

// Create a filter to find the doctor with the specific patient in their patientList
        Document filterDoc = new Document("id", ((doctorUser) userdoctor).getID());

// Create the updated patient information
        Document updatedPatient1 = new Document().append("$set", new Document()
                .append("patientList", ((doctorUser) userdoctor).getPatientList()));
        for (patientUser patient : ((doctorUser) userdoctor).getPatientList()) {
            System.out.println(patient.toString());

        }
// Create the update operation
//        Document updateDoc = new Document("$set", updatedPatient1);

// Perform the update operation
        mongoCollection1.updateOne(filterDoc, updatedPatient1).getAsync(result1 -> {
            if (result1.isSuccess()) {
//                Intent intent = new Intent(ExitHospitalActivity.this, ViewPatientsList.class);
//                startActivity(intent);

                long numModified = result1.get().getModifiedCount();
                if (numModified == 1) {
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                    Log.v("Update", "Successfully updated document");
                } else {
                    Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_LONG).show();
                    Log.v("Update", "Inserted new document");
                }
            } else {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                Log.v("Update", "Failed to update document: " + result1.getError().toString());
            }
        });


        Log.v("updating", "updating");
        Document filter = new Document().append("id", patient.getId());
        Document updatedPatient = new Document()
                .append("medicalRecord", med.get(0).getRecords())
                .append("status", patient.isStatus());
        Document update = new Document().append("$set", updatedPatient);

        mongoCollection.updateOne(filter, update).getAsync(result -> {
            if (result.isSuccess()) {
                Intent intent = new Intent(ExitHospitalActivity.this, ViewPatientsList.class);
                startActivity(intent);

                long numModified = result.get().getModifiedCount();
                if (numModified == 1) {
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                    Log.v("Update", "Successfully updated document");
                } else {
                    Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_LONG).show();
                    Log.v("Update", "Inserted new document");
                }
            } else {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                Log.v("Update", "Failed to update document: " + result.getError().toString());
            }
        });


//        User user1 = app.currentUser();
//        MongoClient mongoClient1 = user1.getMongoClient("mongodb-atlas");
//        MongoDatabase mongoDatabase1 = mongoClient1.getDatabase("Hospital");
//        MongoCollection<Document> mongoCollection1 = mongoDatabase1.getCollection("Doctor");

//        Document queryFilter = new Document().append("patientList", new Document("$exists", true));
//        String[] idOfPatientObjInDoc = new String[1];
//        String[] patientDocument = {"", ""};
//        StringBuilder[] positionOfPatientStr = new StringBuilder[]{new StringBuilder(), new StringBuilder()};
//
//        mongoCollection1.findOne(queryFilter).getAsync(result -> {
//            if (result.isSuccess()) {
//                Toast.makeText(getApplicationContext(), "Found", Toast.LENGTH_LONG).show();
//                Document resultData = result.get();
//                Log.v("Data Success", resultData.toString());
//                if (resultData.containsKey("patientList")) {
//                    ArrayList<Document> arrList = (ArrayList<Document>) resultData.get("patientList");
//                    for (Document patient : arrList) {
//                        String patientId = patient.getString("id");
//                        System.out.println(patientId);
//                        if (Objects.equals(patientId, ID)) {
////                            Compare id of element in document with ID of current selected patient
//                            idOfPatientObjInDoc[0] = String.valueOf(arrList.indexOf(patient));
//                            positionOfPatientStr[0].append("patientList.").append(idOfPatientObjInDoc[0]).append(".status");
//                            positionOfPatientStr[1].append("patientList.").append(idOfPatientObjInDoc[0]).append(".id");
//                            patientDocument[0] += positionOfPatientStr[0].toString();
//                            patientDocument[1] += positionOfPatientStr[1].toString();
//
//
//                            break;
//                        }
//                    }
//                    System.out.println(patientDocument[0]);
//                    System.out.println(patientDocument[1]);
//                    Document filterDoc = new Document().append("id", 1);
//                    Document updateDoc = new Document().append(patientDocument[0], true).append(patientDocument[1], ID);;
//                    Document update1 = new Document().append("$set", updateDoc);
//                    mongoCollection1.updateOne(filterDoc, update1).getAsync(result1 -> {
//                        if (result1.isSuccess()) {
//                            Intent intent = new Intent(ExitHospitalActivity.this, ViewPatientsList.class);
//                            startActivity(intent);
//
//                            long numModified = result1.get().getModifiedCount();
//                            if (numModified == 1) {
//                                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
//                                Log.v("Update", "Successfully updated document");
//                            } else {
//                                Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_LONG).show();
//                                Log.v("Update", "Inserted new document");
//                            }
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
//                            Log.v("Update", "Failed to update document: " + result1.getError().toString());
//                        }
//                    });
//                } else {
//                    Log.v("Data Success", "Document does not contain an 'arr' field");
//                }
//            } else {
//                Toast.makeText(getApplicationContext(), "Not Found", Toast.LENGTH_LONG).show();
//                Log.v("Data Error", result.getError().toString());
//            }
//        });


//        System.out.println(patientDocument[0]);
//        System.out.println("positionOfPatientStr.toString()");


    }
}