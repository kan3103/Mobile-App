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
import com.example.mobile_app.databinding.ExitHospitalFormBinding;
import com.example.mobile_app.ui.dashboard.DashboardFragment;
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
    private EditText name, id, dateIn, bloodPressure;
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
        id = findViewById(R.id.board_id);
        dateIn = findViewById(R.id.board_dateIn);
        bloodPressure = findViewById(R.id.board_bloodPressure);
        btn2 = (Button) findViewById(R.id.board_xacnhan);
        Intent intent = getIntent();
        if (intent != null) {
            userDoc = (userInterface) intent.getSerializableExtra("userObj");
            if (userDoc != null) {
                Log.v("test", ((doctorUser) userDoc).getName());
            }
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


        System.out.println("Register");



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
                            id.setText(patient.getId());
                            id.setFocusable(false);
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
//                Intent intent = new Intent(ApplyToHospital.this, ViewPatientsList.class);
//                startActivity(intent);
//                System.out.println(((doctorUser) userDoc).getName());
                fillPatientInform();
            }
        });

    }
    public MedRecord getMediarecord(Document data) {
        MedRecord medRecord = new MedRecord(data.getString("name"), "", "", "", "", "", "", "");
        if (data.containsKey("medicalRecord")) {
            ArrayList<Document> arrList = (ArrayList<Document>) data.get("medicalRecord");

            if(arrList==null) {
                medRecord.setRecords(null);
                return medRecord;};
            for (int i = 0; i < arrList.size(); ++i) {
                Document secondElement = arrList.get(i);
                medRecord.addRecord(secondElement.containsKey("weight") ? secondElement.getString("weight") : "", secondElement.containsKey("height") ? secondElement.getString("height") : "",
                        secondElement.containsKey("doctor") ? secondElement.getString("doctor") : "", secondElement.containsKey("nurse") ? secondElement.getString("nurse") : "", secondElement.containsKey("dateIn") ? secondElement.getString("dateIn") : "",
                        secondElement.containsKey("reDate") ? secondElement.getString("reDate") : "", secondElement.containsKey("specialty") ? secondElement.getString("specialty") : "");
            }
        }
        return medRecord;
    }

    private void fillPatientInform() {
        //Inputs
        String patientName = name.getText().toString().trim();
        String ID = id.getText().toString().trim();
        String DateIn = dateIn.getText().toString().trim();
        String BloodPressure = bloodPressure.getText().toString().trim();

//        progressBar.setVisibility(View.VISIBLE);
        if (patient!=null) {
            medRecord = patient.getMedicalRecord();
        }

        medRecord.addRecord("","",((doctorUser) userDoc).getName(),"",DateIn,"","");
        Log.v("oke",((doctorUser) userDoc).getName());
// Now you can safely call addRecord

        patient.setStatus(true);
        patient.setMedicalRecord(medRecord);

        ArrayList<Document> medical = new ArrayList<>();
        for(int i=0;i<patient.getMedicalRecord().getRecords().size();++i){
            Document add = new Document().append("dateIn",patient.getMedicalRecord().getRecords().get(i).getDate())
                    .append("weight","")
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
        mongoCollection2.findOne(filter).getAsync(result -> {
            if(result.isSuccess()){
                Document data = result.get();
                data.append("medicalRecord", medical); // Append medical records to the existing document
                // Update the document in the collection
                mongoCollection2.updateOne(filter, data).getAsync(result1 -> {
                    if (result1.isSuccess()) {
                        // Document updated successfully
                        Log.v("Update", "Medical records updated successfully.");
                        mongoCollection = mongoDatabase.getCollection("Register");
                        // find ID of the patient following the name and get ID at local
                        Document queryFilter = new Document().append("username", patient.getUsername());
                        mongoCollection.deleteOne(queryFilter).getAsync(result2 -> {
                            if(result2.isSuccess())
                                finish();
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

//        MedRecord[] med = new MedRecord[1];
//        List<MedRecord> med = new ArrayList<>();
//        med.add(new MedRecord(patientName, "", "", "", "", "", "", patient.getId()));
//        Document f = new Document().append("id_patient", "10");
//
//        mongoCollection2.findOne(f).getAsync(result -> {
//            if (result.isSuccess()) {
//                Toast.makeText(getApplicationContext(), "Found", Toast.LENGTH_LONG).show();
//                Document resultData = result.get();
//                Log.v("Data Success", resultData.toString());
//
//                if (resultData.containsKey("record")) {
//                    ArrayList<Document> arrList = (ArrayList<Document>) resultData.get("record");
//                    String[] patientInform = new String[5];
//                    assert arrList != null;
//                    for (Document document : arrList) {
//                        patientInform[0] = document.getString("date_in");
//                        patientInform[1] = document.getString("date_out");
//                        patientInform[2] = document.getString("doctor");
//                        patientInform[3] = document.getString("nurse");
//                        patientInform[4] = document.getString("Diagnose");
//                        med.get(0).addRecord(patientInform[2], patientInform[1], patientInform[3]);
//                        System.out.println(med.get(0).getRecords().get(0).getNurse());
//                        System.out.println(med.get(0).getRecords().size());
//                        System.out.println("med[0].getRecords().size()");
//                    }
////                            med[0] = new MedRecord(weight, height, doctor, nurse, date, RevisionDate, specialty);
////                            System.out.println(med[0].toString());
//                } else {
//                    Log.v("Data Success", "Document does not contain an 'arr' field");
//                }
//            } else {
//                Toast.makeText(getApplicationContext(), "Not Found", Toast.LENGTH_LONG).show();
//                Log.v("Data Error", result.getError().toString());
//            }
//        });
//
//        System.out.print("med[0].getRecords().size(): ");
//        System.out.println(med.get(0).getRecords().size());
//        med.get(0).addRecord(doctorName, DateIn, BloodPressure);
//
//        System.out.print("med[0].getRecords(): ");
//        System.out.println(med.get(0).getRecords().size());
//        System.out.println(med.get(0).getRecords().get(0).getNurse());
//        patient.setStatus(true);
//
//        for (patientUser patient : ((doctorUser) userDoc).getPatientList()) {
//            if (patient.getId().equals(ID)) {
//                patient.setStatus(true);
//                break;
//            }
//        }

}

}