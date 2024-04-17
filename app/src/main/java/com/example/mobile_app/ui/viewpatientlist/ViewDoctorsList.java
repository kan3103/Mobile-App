package com.example.mobile_app.ui.viewpatientlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
import io.realm.mongodb.mongo.iterable.MongoCursor;

public class ViewDoctorsList extends AppCompatActivity {
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
        setContentView(R.layout.activity_view_doctors_list);

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
                mongoCollection = mongoDatabase.getCollection("Register");
                mongoCollection.find().iterator().getAsync(task -> {
                    if (task.isSuccess()) {
                        MongoCursor<Document> results = task.get();
                        while (results.hasNext()) {
                            Document currentDocument = results.next();

                            String username = currentDocument.getString("username");
                            String birthday = currentDocument.getString("birthday");
                            String name = currentDocument.getString("name");
                            patientList.add(new patientUser(username, birthday, name));
                        }
                        System.out.println(patientList);
                        adapter = new CustomAdapter(patientList, ViewDoctorsList.this);
//                            System.out.println(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ViewDoctorsList.this));
                        recyclerView.setAdapter(adapter);
                        System.out.println(recyclerView);
//                    textView.setText(arrList.toString());
                    } else {
                        Log.e("APP", "Failed to find documents with: ", task.getError());
                    }
                });

//                Document queryFilter = new Document().append("patientList", new Document("$exists", true));
//                mongoCollection.find().getAsync(result -> {
//                    if (result.isSuccess()) {
//                        Toast.makeText(getApplicationContext(), "Found", Toast.LENGTH_LONG).show();
//                        Document resultData = result.get();
//                        Log.v("Data Success", resultData.toString());
//                        if (resultData.containsKey("patientList")) {
//                            ArrayList<Document> arrList = (ArrayList<Document>) resultData.get("patientList");
//                            for (Document patient : arrList) {
////                                System.out.println(patient);
//
//                                String patientDoc = patient.toJson();
////                                System.out.println(patientDoc);
//
//                                String name = patient.getString("name");
//                                String age = patient.getString("age");
//                                String phoneNumber = patient.getString("phoneNumber");
//                                patientList.add(new patientUser(name,age,phoneNumber));
//
//                            }
//                            System.out.println(patientList);
//                            adapter = new CustomAdapter(patientList, ViewDoctorsList.this);
////                            System.out.println(adapter);
//                            recyclerView.setLayoutManager(new LinearLayoutManager(ViewDoctorsList.this));
//                            recyclerView.setAdapter(adapter);
//                            System.out.println(recyclerView);
////                    textView.setText(arrList.toString());
//                        } else {
//                            Log.v("Data Success", "Document does not contain an 'arr' field");
//                        }
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Not Found", Toast.LENGTH_LONG).show();
//                        Log.v("Data Error", result.getError().toString());
//                    }
//                });

//                System.out.println(patientList);

            }

        });

        // Find an element in array
        System.out.println("Load list of patients");

    }


    //        Getting data:
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


//        });
    }
}
