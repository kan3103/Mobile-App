package com.example.mobile_app.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_app.R;

import com.example.mobile_app.api.user.userObject.doctorUser;
import com.example.mobile_app.api.user.userObject.patientUser;
import com.example.mobile_app.api.user.userObject.userInterface;
import com.example.mobile_app.ui.viewpatientlist.CustomAdapter;
import com.example.mobile_app.ui.viewpatientlist.ViewDoctorsList;
import com.example.mobile_app.ui.viewpatientlist.ViewPatientsList;

import org.bson.Document;

import java.lang.reflect.Array;
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

public class HomeListActivity extends AppCompatActivity {

    private CardView Cardiology, Endocrinology,ENT, Neurology, Pediatrics, Obstetrics;
    private App app;
    private ProgressBar progressBar;
    private View overlay;
    String Appid = "mobileapp-fyjbw";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    MongoCollection<Document> mongoCollection;
    private RecyclerView recyclerView;
    User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_list);
        Realm.init(getApplicationContext());
        progressBar = (ProgressBar) findViewById(R.id.wait);

        progressBar.setVisibility(View.VISIBLE);

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
                        mongoCollection = mongoDatabase.getCollection("Specialty");
                        progressBar.setVisibility(View.INVISIBLE);
                    };
        });
        Cardiology = findViewById(R.id.cardio);
        Endocrinology = findViewById(R.id.endoc);
        ENT = findViewById(R.id.ear);
        Neurology = findViewById(R.id.neur);
        Pediatrics = findViewById(R.id.pedia);
        Obstetrics = findViewById(R.id.obs);
        Cardiology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_view_patient_list);
                recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                ArrayList<userInterface> save = new ArrayList<>();
                Document doc = new Document().append("name","Cardiology");
                mongoCollection.findOne(doc).getAsync( result -> {
                    if(result.isSuccess()){

                        Document doc2 = result.get();
                        if(doc2.containsKey("array")){
                            ArrayList<Document> check = (ArrayList<Document>) doc2.get("array");
                            for(Document run : check){
                                userInterface hi = new doctorUser(run.getString("username"),run.getString("name"),run.getString("sex"),run.getString("experience"));
                                save.add(hi);
                            }
                            Log.v("oke","Thanh cong roi");
                            CustomAdapter adapter = new CustomAdapter(save, HomeListActivity.this,null);
                            recyclerView.setLayoutManager(new LinearLayoutManager(HomeListActivity.this));

                            recyclerView.setAdapter(adapter);
                        }

                    }
                    else {
                        Log.v("oke","kooo Thanh cong roi");
                    }
                });
            }

        });

        Endocrinology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_view_patient_list);
                recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                ArrayList<userInterface> save = new ArrayList<>();
                Document doc = new Document().append("name","Endocrinology");
                mongoCollection.findOne(doc).getAsync( result -> {
                    if(result.isSuccess()){

                        Document doc2 = result.get();
                        if(doc2.containsKey("array")){
                            ArrayList<Document> check = (ArrayList<Document>) doc2.get("array");
                            if(check!=null){
                            for(Document run : check){
                                userInterface hi = new doctorUser(run.getString("username"),run.getString("name"),run.getString("sex"),run.getString("experience"));
                                save.add(hi);
                            }
                            Log.v("oke","Thanh cong roi");
                            CustomAdapter adapter = new CustomAdapter(save, HomeListActivity.this,null);
                            recyclerView.setLayoutManager(new LinearLayoutManager(HomeListActivity.this));
                            recyclerView.setAdapter(adapter);}
                        }

                    }
                    else {
                        Log.v("oke","kooo Thanh cong roi");
                    }
                });
            }

        });
        Neurology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_view_patient_list);
                recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                ArrayList<userInterface> save = new ArrayList<>();
                Document doc = new Document().append("name","Neurology");
                mongoCollection.findOne(doc).getAsync( result -> {
                    if(result.isSuccess()){

                        Document doc2 = result.get();
                        if(doc2.containsKey("array")){
                            ArrayList<Document> check = (ArrayList<Document>) doc2.get("array");
                            if(check!=null){
                                for(Document run : check){
                                    userInterface hi = new doctorUser(run.getString("username"),run.getString("name"),run.getString("sex"),run.getString("experience"));
                                    save.add(hi);
                                }
                                Log.v("oke","Thanh cong roi");
                                CustomAdapter adapter = new CustomAdapter(save, HomeListActivity.this,null);
                                recyclerView.setLayoutManager(new LinearLayoutManager(HomeListActivity.this));
                                recyclerView.setAdapter(adapter);}
                        }

                    }
                    else {
                        Log.v("oke","kooo Thanh cong roi");
                    }
                });
            }

        });
        Obstetrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_view_patient_list);
                recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                ArrayList<userInterface> save = new ArrayList<>();
                Document doc = new Document().append("name","OG");
                mongoCollection.findOne(doc).getAsync( result -> {
                    if(result.isSuccess()){

                        Document doc2 = result.get();
                        if(doc2.containsKey("array")){
                            ArrayList<Document> check = (ArrayList<Document>) doc2.get("array");
                            if(check!=null){
                                for(Document run : check){
                                    userInterface hi = new doctorUser(run.getString("username"),run.getString("name"),run.getString("sex"),run.getString("experience"));
                                    save.add(hi);
                                }
                                Log.v("oke","Thanh cong roi");
                                CustomAdapter adapter = new CustomAdapter(save, HomeListActivity.this,null);
                                recyclerView.setLayoutManager(new LinearLayoutManager(HomeListActivity.this));
                                recyclerView.setAdapter(adapter);}
                        }

                    }
                    else {
                        Log.v("oke","kooo Thanh cong roi");
                    }
                });
            }

        });
        Pediatrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_view_patient_list);
                recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                ArrayList<userInterface> save = new ArrayList<>();
                Document doc = new Document().append("name","Pediatrics");
                mongoCollection.findOne(doc).getAsync( result -> {
                    if(result.isSuccess()){

                        Document doc2 = result.get();
                        if(doc2.containsKey("array")){
                            ArrayList<Document> check = (ArrayList<Document>) doc2.get("array");
                            if(check!=null){
                                for(Document run : check){
                                    userInterface hi = new doctorUser(run.getString("username"),run.getString("name"),run.getString("sex"),run.getString("experience"));
                                    save.add(hi);
                                }
                                Log.v("oke","Thanh cong roi");
                                CustomAdapter adapter = new CustomAdapter(save, HomeListActivity.this,null);
                                recyclerView.setLayoutManager(new LinearLayoutManager(HomeListActivity.this));
                                recyclerView.setAdapter(adapter);}
                        }

                    }
                    else {
                        Log.v("oke","kooo Thanh cong roi");
                    }
                });
            }

        });
        ENT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_view_patient_list);
                recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                ArrayList<userInterface> save = new ArrayList<>();
                Document doc = new Document().append("name","ENT");
                mongoCollection.findOne(doc).getAsync( result -> {
                    if(result.isSuccess()){

                        Document doc2 = result.get();
                        if(doc2.containsKey("array")){
                            ArrayList<Document> check = (ArrayList<Document>) doc2.get("array");
                            if(check!=null){
                                for(Document run : check){
                                    userInterface hi = new doctorUser(run.getString("username"),run.getString("name"),run.getString("sex"),run.getString("experience"));
                                    save.add(hi);
                                }
                                Log.v("oke","Thanh cong roi");
                                CustomAdapter adapter = new CustomAdapter(save, HomeListActivity.this,null);
                                recyclerView.setLayoutManager(new LinearLayoutManager(HomeListActivity.this));
                                recyclerView.setAdapter(adapter);}
                        }

                    }
                    else {
                        Log.v("oke","kooo Thanh cong roi");
                    }
                });

        }
        });
    }
}
