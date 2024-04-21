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
    private EditText name, testResult, dateOut, nurse;
    private User user;
    //    public boolean isUpdated;
    private ProgressBar progressBar;
    private App app;
    //    patientUser patient;
    private userInterface userdoctor;
    private MedRecord medRecord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ExitHospitalFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        name = findViewById(R.id.board_name);
        testResult = findViewById(R.id.board_id);
        dateOut = findViewById(R.id.board_dateOut);
        nurse = findViewById(R.id.board_nurse);
        btn2 = (Button) findViewById(R.id.board_xacnhan);

        Intent intent = getIntent();
        if (intent != null) {
            userdoctor = (userInterface) intent.getSerializableExtra("userobject");
            if (userdoctor != null) {
                Log.v("test", ((doctorUser) userdoctor).getName());
            }
            patient = (patientUser) intent.getSerializableExtra("patientInfor");

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

        app.loginAsync(credentials, new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                user = app.currentUser();
                mongoClient = user.getMongoClient("mongodb-atlas");
                mongoDatabase = mongoClient.getDatabase("Hospital");
                mongoCollection = mongoDatabase.getCollection("Patient");
                // find ID of the patient following the name and get ID at local
                Document queryFilter = new Document().append("username", patient.getUsername());
                mongoCollection.findOne(queryFilter).getAsync(task -> {
                    if (task.isSuccess()) {
                        Document resultData = task.get();
                        if (resultData != null) {
                            patient.setMedicalRecord(getMediarecord(resultData));
                            patient.setId(resultData.getString("id"));
                            name.setText(patient.getName());
                            name.setFocusable(false);
                        }
                    } else {
                        Log.v("APP", "Failed to find documents with: ", task.getError());
                    }
                });
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillPatientInform();
                updateList(patient.getUsername());
                Intent intent1 =new Intent(ExitHospitalActivity.this,MainActivity.class);

            }
        });

    }
    public MedRecord getMediarecord(Document data) {
        MedRecord medRecord = new MedRecord(data.getString("name"), "", "", "", "", "", "","");
        if (data.containsKey("medicalRecord")) {
            ArrayList<Document> arrList = (ArrayList<Document>) data.get("medicalRecord");

            if(arrList==null) {
                medRecord.setRecords(null);
                return medRecord;};
            for (int i = 0; i < arrList.size(); ++i) {
                Document secondElement = arrList.get(i);
                medRecord.addRecord(secondElement.containsKey("weight") ? secondElement.getString("weight") : "", secondElement.containsKey("height") ? secondElement.getString("height") : "",
                        secondElement.containsKey("doctor") ? secondElement.getString("doctor") : "", secondElement.containsKey("nurse") ? secondElement.getString("nurse") : "", secondElement.containsKey("dateIn") ? secondElement.getString("dateIn") : "",
                        secondElement.containsKey("reDate") ? secondElement.getString("reDate") : "", secondElement.containsKey("specialty") ? secondElement.getString("specialty") : "", secondElement.containsKey("bloodPressure") ? secondElement.getString("bloodPressure") : ""
                , secondElement.containsKey("testResults") ? secondElement.getString("testResults") : "");
            }
        }
        return medRecord;
    }
    private void updateList(String username){
        for(int i=0;i<((doctorUser) userdoctor).getPatientList().size();++i){
            if(((doctorUser) userdoctor).getPatientList().get(i).getUsername().equals(username)){
                ((doctorUser) userdoctor).getPatientList().remove(i);
            };
            MongoCollection<Document> mongoCollection1= mongoDatabase.getCollection("Doctor");
            Document filter = new Document().append("username",userdoctor.getUsername());
            mongoCollection1.findOne(filter).getAsync(result -> {
                if(result.isSuccess()){
                    Document data= result.get();
                    if(data.containsKey("patientList")){
                        ArrayList<Document> listpatient =(ArrayList<Document>) data.get("patientList");
                        for(Document run : listpatient){
                            String name=run.getString("username");
                            Log.v("tét",username);
                            if(username.equals(name)){
                                listpatient.remove(run);
                                data.append("patientList",listpatient);
                                MongoCollection<Document> mongoCollection2 = mongoDatabase.getCollection("Doctor");
                                mongoCollection2.updateOne(filter,data).getAsync(result1 -> {
                                    if(result1.isSuccess()){
                                        Log.v("hoàn thành","okeeee");
                                    }

                                });

                            }
                        };

                    }
                }
            });
        }
    }
    private void fillPatientInform() {
        //Inputs
        String patientName = name.getText().toString().trim();
        String TestResult = testResult.getText().toString().trim();
        String DateOut = dateOut.getText().toString().trim();
        String Nurse = nurse.getText().toString().trim();

//        progressBar.setVisibility(View.VISIBLE);
        String doctorName = ((doctorUser) userdoctor).getName();
        if (patient!=null) {
            medRecord = patient.getMedicalRecord();
        }

        // Get Record at size() - 1
        Log.v("oke", String.valueOf(medRecord.getRecords().size()));
        MedRecord.Record record = medRecord.getRecords().get(medRecord.getRecords().size() - 1);



        String Weight = record.getWeight();
        String Height = record.getHeight();
        String DateIn = record.getDate();
//        String BloodPressure = record.getBloodPressure();

        // Delete this Record
        medRecord.getRecords().remove(medRecord.getRecords().size() - 1);

        // Add new Record
        medRecord.addRecord(Weight, Height, doctorName, Nurse, DateIn, DateOut, ((doctorUser)userdoctor).getSpecialty(),"BloodPressure",TestResult);

        patient.setStatus(true);
        patient.setMedicalRecord(medRecord);


        ArrayList<Document> medical = new ArrayList<>();
        for(int i=0;i<patient.getMedicalRecord().getRecords().size();++i){
            Document add = new Document()
                    .append("weight", patient.getMedicalRecord().getRecords().get(i).getWeight())
                    .append("height",  patient.getMedicalRecord().getRecords().get(i).getHeight())
                    .append("bloodPressure", "")
                    .append("specialty",  patient.getMedicalRecord().getRecords().get(i).getSpecialty())
                    .append("dateIn", patient.getMedicalRecord().getRecords().get(i).getDate())
                    .append("nurse", patient.getMedicalRecord().getRecords().get(i).getNurse())
                    .append("testResult", patient.getMedicalRecord().getRecords().get(i).getTestResults())
                    .append("bloodType",patient.getBloodType())
                    .append("doctor",((doctorUser) userdoctor).getName())
                    .append("dateOut", patient.getMedicalRecord().getRecords().get(i).getDateout());
            Log.v("tes", String.valueOf(add));
            medical.add(add);
        }

        // Delete this patient from patientList of Doctor
        // Get the patientList of the doctor
         patientList = ((doctorUser) userdoctor).getPatientList();

        // Find the index of the patient in the patientList
        int patientIndex = patientList.indexOf(patient);

        // If the patient is in the patientList, remove it
        if (patientIndex != -1) {
            patientList.remove(patientIndex);
        }
        System.out.println(patientList.size());

        // Update the patientList of the doctor
        ((doctorUser) userdoctor).setPatientList(patientList);

        User user2 = app.currentUser();
        MongoClient mongoClient2 = user2.getMongoClient("mongodb-atlas");
        MongoDatabase mongoDatabase2 = mongoClient2.getDatabase("Hospital");
        MongoCollection<Document> mongoCollection2 = mongoDatabase2.getCollection("Patient");
        Document filter= new Document().append("username",patient.getUsername());
        mongoCollection2.findOne(filter).getAsync(result -> {
            if(result.isSuccess()){
                Document data = result.get();
                data.append("medicalRecord", medical); // Append medical records to the existing document
                // Update the document in the collection
                mongoCollection2.updateOne(filter, data).getAsync(result1 -> {
                    if (result1.isSuccess()) {
                        // Document updated successfully
                        Log.v("Update", "Medical records updated successfully.");
//
                    } else {
                        // Error occurred while updating
                        Log.e("Update", "Failed to update medical records.", result1.getError());
                    }
                });
            } else {
                // Error occurred while fetching the document
                Log.e("Fetch", "Failed to fetch patient document.", result.getError());
            }
        });

    }
}