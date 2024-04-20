package com.example.mobile_app.ui.viewpatientlist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_app.R;
import com.example.mobile_app.api.user.userObject.adminUser;
import com.example.mobile_app.ui.home.HomeFragment;

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

public class ViewAdminList extends AppCompatActivity {
    AdminAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Document> adminList = new ArrayList<>();
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
        setContentView(R.layout.hospital_infomation_activity);

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
                mongoCollection = mongoDatabase.getCollection("Admin");
                mongoCollection.find().iterator().getAsync(task -> {
                    if (task.isSuccess()) {
                        MongoCursor<Document> results = task.get();
                        while (results.hasNext()) {
                            Document currentDocument = results.next();

                            String mssv = currentDocument.getString("password");
                            String name = currentDocument.getString("username");


                            // Create a new patientUser object and add it to the patientList
                            adminList.add(currentDocument);
//                            System.out.println(adminList);
                        }

                        // Set up the RecyclerView with the patientList
                        if (adminList != null) {
//            System.out.println(patientList.get(0).toString());
                            adapter = new AdminAdapter(adminList, ViewAdminList.this);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ViewAdminList.this));
                            recyclerView.setAdapter(adapter);
                        } else {
                            Log.e("APP", "Failed to find documents with: ", task.getError());
                        }
                    }
                });
            }
        });
    }
}

