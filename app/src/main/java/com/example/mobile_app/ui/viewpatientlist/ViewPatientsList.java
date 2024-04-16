package com.example.mobile_app.ui.viewpatientlist;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_app.Data.Doctor;
import com.example.mobile_app.R;
import com.example.mobile_app.api.user.userObject.patientUser;
import com.example.mobile_app.api.user.userObject.userInterface;

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
    CustomAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Doctor> doctorList = new ArrayList<>();

    ArrayList<userInterface> patientList = new ArrayList<>();

    String Appid = "mobileapp-fyjbw";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    MongoCollection<Document> mongoCollection;
    private App app;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_list);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

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

//                if (mongoCollection == null){
//                    System.out.println(user);
//                    System.out.println(mongoClient);
//                    System.out.println(mongoDatabase);
//                    System.out.println("Collection Null ma cung xai?");
//                }

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

                                String patientDoc = patient.toJson();
//                                System.out.println(patientDoc);

                                String name = patient.getString("name");
                                String age = patient.getString("age");
                                String phoneNumber = patient.getString("phoneNumber");
                                userInterface hi = new patientUser(name,age,phoneNumber);
                                patientList.add(hi);

                            }
                            System.out.println(patientList);
                            adapter = new CustomAdapter(patientList, ViewPatientsList.this);
//                            System.out.println(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ViewPatientsList.this));
                            recyclerView.setAdapter(adapter);
                            System.out.println(recyclerView);
//                    textView.setText(arrList.toString());
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

        // Find an element in array
        System.out.println("Load list of patients");

//        Document queryFilter = new Document().append("arr", new Document("$exists", true));
//        mongoCollection.findOne(queryFilter).getAsync(result -> {
//            if (result.isSuccess()) {
//                Toast.makeText(getApplicationContext(), "Found", Toast.LENGTH_LONG).show();
//                Document resultData = result.get();
//                Log.v("Data Success", resultData.toString());
//                if (resultData.containsKey("arr")) {
//                    ArrayList<Document> arrList = (ArrayList<Document>) resultData.get("arr");
//                    for (Document patient : arrList) {
//                        System.out.println(patient);
//                    }
////                    textView.setText(arrList.toString());
//                } else {
//                    Log.v("Data Success", "Document does not contain an 'arr' field");
//                }
//            } else {
//                Toast.makeText(getApplicationContext(), "Not Found", Toast.LENGTH_LONG).show();
//                Log.v("Data Error", result.getError().toString());
//            }
//        });
//        getPatientFromDB();
//        Document filter = new Document().append("name","A");
//
//        if(mongoCollection.findOne(filter) != null){
//            System.out.println("Null ma cung xai?");
//            Document queryFilter = new Document().append("patientList.0", new Document("$exists", true));
//            mongoCollection.findOne(queryFilter).getAsync(result -> {
//                if (result.isSuccess()) {
//                    Toast.makeText(getApplicationContext(), "Found", Toast.LENGTH_LONG).show();
//                    Document resultData = result.get();
//                    Log.v("Data Success", resultData.toString());
//                    if (resultData.containsKey("patientList")) {
//                        ArrayList<Document> arrList = (ArrayList<Document>) resultData.get("patientList");
//                        if (arrList.size() > 1) {
//                            Document secondElement = arrList.get(0);
//                            System.out.println(secondElement);
//                            //                        textView.setText(secondElement.toJson());
//                        } else {
//                            Log.v("Data Success", "Array does not contain a second element");
//                        }
//                    } else {
//                        Log.v("Data Success", "Document does not contain an 'arr' field");
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), "Not Found", Toast.LENGTH_LONG).show();
//                    Log.v("Data Error", result.getError().toString());
//                }
//            });
//
//        }


//        Doctor doctor1 = new Doctor("Japan", "Alpha", "Beta", "a@abc.com", "Japan", "Alpha", "1", "Master", "Japan");
//        Doctor doctor2 = new Doctor("Japan", "Alpha", "Beta", "a@abc.com", "Japan", "Alpha", "2", "Professional", "Japan");
//        Doctor doctor3 = new Doctor("Japan", "Alpha", "Beta", "a@abc.com", "Japan", "Alpha", "3", "Doctor", "Japan");
//
//        doctorList.add(doctor1);
//        doctorList.add(doctor2);
//        doctorList.add(doctor3);


    }


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
