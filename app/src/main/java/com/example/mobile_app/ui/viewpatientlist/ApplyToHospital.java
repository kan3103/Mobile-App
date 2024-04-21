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
import com.example.mobile_app.databinding.ApplyToHospitalBinding;

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
public class ApplyToHospital extends AppCompatActivity {
    public boolean isUpdated;
    String Appid = "mobileapp-fyjbw";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    MongoCollection<Document> mongoCollection;
    patientUser patient;
    ArrayList<patientUser> patientList = new ArrayList<>();
    private ApplyToHospitalBinding binding;
    private Button btn2;
    private EditText name, weight, height,dateIn, bloodPressure;
    private User user;
    //    public boolean isUpdated;
    private ProgressBar progressBar;
    private App app;
    //    patientUser patient;
    private userInterface userDoc;
    private patientUser userPatient;
    private MedRecord medRecord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ApplyToHospitalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        name = findViewById(R.id.board_name);
        weight = findViewById(R.id.board_weight);
        height = findViewById(R.id.board_height);
        dateIn = findViewById(R.id.board_dateIn);
        bloodPressure = findViewById(R.id.board_bloodPressure);
        btn2 = (Button) findViewById(R.id.board_xacnhan);
        Intent intent = getIntent();
        if (intent != null) {
            userDoc = (userInterface) intent.getSerializableExtra("userObj");
            patient = (patientUser) intent.getSerializableExtra("patientInformation");
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
                        Log.e("APP", "Failed to find documents with: ", task.getError());
                    }
                });
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fillPatientInform())
                updatePatientlist();
                Intent intent = new Intent(ApplyToHospital.this, MainActivity.class);
                intent.putExtra("userobject", (doctorUser) userDoc);
                startActivity(intent);
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

    private void updatePatientlist() {
        if(((doctorUser) userDoc).getPatientList()==null){
            ((doctorUser) userDoc).setPatientList(new ArrayList<>());
        }
        ((doctorUser) userDoc).getPatientList().add(patient);
        mongoCollection = mongoDatabase.getCollection("Doctor");
        Document filter = new Document().append("username",userDoc.getUsername());
        mongoCollection.findOne(filter).getAsync(result -> {
            if(result.isSuccess()){
                Document data = result.get();
                if(data.containsKey("patientList")){
                    ArrayList<Document> newdoc = (ArrayList<Document>) data.get("patientList");
                    Document document = new Document()
                            .append("username",patient.getUsername())
                            .append("dateIn",patient.getMedicalRecord().getRecords().get(patient.getMedicalRecord().getRecords().size()-1).getDate())
                            .append("name",patient.getName())
                            .append("symptoms",patient.getSymptoms())
                            .append("phoneNumber",patient.getPhoneNumber());
                    newdoc.add(document);
                    MongoCollection<Document> mongoCollection1 = mongoDatabase.getCollection("Doctor");
                    data.append("patientList",newdoc);
                    // Update the document in the collection
                    mongoCollection.updateOne(filter, data).getAsync(result1 -> {
                        if (result1.isSuccess()) {
                            // Document updated successfully
                            Log.v("Update", "Medical records updated successfully.");
//                        ((doctorUser) userDoc).getPatientList().add(patient);
                            finish();
                        } else {
                            // Error occurred while updating
                            Log.e("Update", "Failed to update medical records.", result1.getError());
                        }
                    });
                }
            }
        });
    }
    private boolean fillPatientInform() {
        //Inputs
        String patientName = name.getText().toString().trim();
        String Weight = weight.getText().toString().trim();
        String Height = height.getText().toString().trim();
        String DateIn = dateIn.getText().toString().trim();
        String BloodPressure = bloodPressure.getText().toString().trim();

//        progressBar.setVisibility(View.VISIBLE);
        if (patient!=null) {
            medRecord = patient.getMedicalRecord();
        }

        medRecord.addRecord(Weight,Height,((doctorUser) userDoc).getName(),"",DateIn,"","",BloodPressure,"");
        Log.v("oke",((doctorUser) userDoc).getName());
// Now you can safely call addRecord

        patient.setStatus(false);
        patient.setMedicalRecord(medRecord);

        ArrayList<Document> medical = new ArrayList<>();
        for(int i=0;i<patient.getMedicalRecord().getRecords().size();++i){
            Document add = new Document().append("dateIn",patient.getMedicalRecord().getRecords().get(i).getDate())
                    .append("height",patient.getMedicalRecord().getRecords().get(i).getHeight())
                    .append("weight",patient.getMedicalRecord().getRecords().get(i).getHeight())
                    .append("bloodPressure",patient.getMedicalRecord().getRecords().get(i).getBloodPressure())
                    .append("bloodType",patient.getBloodType())
                    .append("doctor",((doctorUser) userDoc).getName())
                    .append("dateOut","");
            Log.v("tes", String.valueOf(add));
            medical.add(add);
        }

        User user2 = app.currentUser();
        MongoClient mongoClient2 = user2.getMongoClient("mongodb-atlas");
        MongoDatabase mongoDatabase2 = mongoClient2.getDatabase("Hospital");
        MongoCollection<Document> mongoCollection2 = mongoDatabase2.getCollection("Patient");
        Document filter= new Document().append("username",patient.getUsername());
        Log.v("Update", patient.getUsername());
        mongoCollection2.findOne(filter).getAsync(result -> {
            if(result.isSuccess()){

                Document data = result.get();
                Log.v("Update", "Medical records updated successfully.");// Append medical records to the existing document
                data.append("medicalRecord", medical);
                // Update the document in the collection
                mongoCollection2.updateOne(filter, data).getAsync(result1 -> {
                    if (result1.isSuccess()) {
                        // Document updated successfully
//                        ((doctorUser) userDoc).getPatientList().add(patient);
                        mongoCollection = mongoDatabase.getCollection("Register");
                        // find ID of the patient following the name and get ID at local
                        Document queryFilter = new Document().append("username", patient.getUsername());
                        mongoCollection.deleteOne(queryFilter).getAsync(result2 -> {
                            if(result2.isSuccess()){}
                        });
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
        return true;
    }

}