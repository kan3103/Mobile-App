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
import com.example.mobile_app.api.user.userObject.patientUser;

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

public class ViewPatientsList extends AppCompatActivity {
    PatientAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Doctor> doctorList = new ArrayList<>();

    ArrayList<patientUser> patientList = new ArrayList<>();

    String Appid = "mobileapp-fyjbw";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    MongoCollection<Document> mongoCollection;
    boolean isUpdated;
    private App app;
    private User user;
    private Button confirmBtn, cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_list);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            isUpdated = extras.getBoolean("isUpdated");
        }


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
                mongoCollection = mongoDatabase.getCollection("Doctor");

                Document queryFilter = new Document().append("patientList", new Document("$exists", true));
                mongoCollection.findOne(queryFilter).getAsync(result -> {
                    if (result.isSuccess()) {
                        Toast.makeText(getApplicationContext(), "Found", Toast.LENGTH_LONG).show();
                        Document resultData = result.get();
                        Log.v("Data Success", resultData.toString());
                        if (resultData.containsKey("patientList")) {
                            ArrayList<Document> arrList = (ArrayList<Document>) resultData.get("patientList");
                            for (Document patient : arrList) {
//                                System.out.println(patient);

//                                String patientDoc = patient.toJson();
//                                System.out.println(patientDoc);

                                String name = patient.getString("name");
                                String age = patient.getString("age");
                                String phoneNumber = patient.getString("phoneNumber");
                                Boolean isUpdatedObject = patient.getBoolean("isUpdated");
                                boolean isUpdated = isUpdatedObject != null ? isUpdatedObject : false;//                                Kiem tra field boolean updated trong patient
//                                if (isUpdated) {
                                    patientList.add(new patientUser(name, age, phoneNumber,isUpdated));
//                                }

                            }
                            adapter = new PatientAdapter(patientList, ViewPatientsList.this, new PatientAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(patientUser patient) {
//                                    System.out.println(patient.getName());
                                    Intent intent = new Intent(ViewPatientsList.this, ExitHospitalActivity.class);
                                    intent.putExtra("patitentInfor", patient);
                                    intent.putExtra("isUpdated", false);

//                                    boolean isUpdated = getIntent().getExtras().getBoolean("isUpdated");
//                                    System.out.println(isUpdated);
                                    startActivity(intent);
                                }
                            });

//                            System.out.println(isUpdated);
                            if (isUpdated) {
//                                Xoa benh nhan:
//                                - Co them 1 field boolean cho patient
//                                - Check boolean nay truoc khi dua vao list
//                                - Neu false thi cho vao list, true thi cho cook
                                ArrayList<patientUser> updatedPatientList = new ArrayList<>();
                                for (patientUser patient : patientList) {
                                    if (!patient.isStatus()) {
                                        updatedPatientList.add(patient);
                                    }
                                }
                                patientList = updatedPatientList;
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(ViewPatientsList.this));
                            recyclerView.setAdapter(adapter);

                        } else {
                            Log.v("Data Success", "Document does not contain an 'arr' field");
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Not Found", Toast.LENGTH_LONG).show();
                        Log.v("Data Error", result.getError().toString());
                    }
                });
//                System.out.println(patientList);
            }

        });


//        confirmBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (int i = 0; i < numOfSwitchs; i++) {
//                    System.out.println(switchsArr[i].isChecked());
//                }
//            }
//        });
//
//        cancelBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


        System.out.println("Load list of patients");
    }

//    public void onPause() {
//        super.onPause();
//        for (int i = 0; i < numOfSwitchs; i++) {
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putBoolean(String.valueOf(i + 1), switchsArr[i].isChecked());
//            editor.apply();
//        }
//    }
//
//    public void onResume(){
//        super.onResume();
//        for (int i = 0; i < numOfSwitchs; i++) {
//            switchsArr[i].setChecked(sharedPreferences.getBoolean(String.valueOf(i + 1), false));
//        }
//    }

    //        Getting data:
    private void getPatientFromDB() {

//        for (PatientInter patient : patientList) {
//
//        }
//        for (int i = 0; i < mongoCollection; i++) {
//
//        }
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


//        Document queryFilter = new Document().append("arr.0", new Document("$exists", true));
//        mongoCollection.findOne(queryFilter).getAsync(result -> {
//            if (result.isSuccess()) {
//                Toast.makeText(getApplicationContext(), "Found", Toast.LENGTH_LONG).show();
//                Document resultData = result.get();
//                Log.v("Data Success", resultData.toString());
//                if (resultData.containsKey("arr")) {
//                    ArrayList<Document> arrList = (ArrayList<Document>) resultData.get("arr");
//                    if (arrList.size() > 1) {
//                        Document secondElement = arrList.get(0);
//
////                        textView.setText(secondElement.toJson());
//                    } else {
//                        Log.v("Data Success", "Array does not contain a second element");
//                    }
//                } else {
//                    Log.v("Data Success", "Document does not contain an 'arr' field");
//                }
//            } else {
//                Toast.makeText(getApplicationContext(), "Not Found", Toast.LENGTH_LONG).show();
//                Log.v("Data Error", result.getError().toString());
//            }
//        });
    }
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