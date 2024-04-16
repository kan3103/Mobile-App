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
import com.example.mobile_app.api.user.userObject.patientUser;
import com.example.mobile_app.api.user.userObject.userInterface;
import com.example.mobile_app.databinding.ExitHospitalFormBinding;
import com.example.mobile_app.ui.register.RegisterActivity;

import org.bson.Document;

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
    String Appid = "mobileapp-fyjbw";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    MongoCollection<Document> mongoCollection;
    private ExitHospitalFormBinding binding;
    private Button btn2;
    private EditText name, username, birthday, symtomps;
    private User user;
    public boolean isUpdated;
    private ProgressBar progressBar;
    private App app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ExitHospitalFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        name = findViewById(R.id.board_name);
        username = findViewById(R.id.board_id);
        birthday = findViewById(R.id.board_birthday);
        symtomps = findViewById(R.id.board_symptoms);
        btn2 = (Button) findViewById(R.id.board_xacnhan);

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

//        Bundle extras = getIntent().getExtras();
//
//        if (extras != null) {
//            patient = extras.getSerializableExtra("patientInfor");
//        }
//
//        patientUser patient = (patientUser) getIntent().getSerializableExtra("patientInfor");

//        boolean isUpdated = getIntent().getExtras().getBoolean("isUpdated");

//        System.out.print(patient.getUsername());
//        System.out.print(patient.getName());
//        username.setText(patient.getUsername());
//        username.setFocusable(false);
//        name.setText(patient.getName());
//        name.setFocusable(false);
//        birthday.setText("11111");
//        symtomps.setText("");

        app.loginAsync(credentials, new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                user = app.currentUser();
                mongoClient = user.getMongoClient("mongodb-atlas");
                mongoDatabase = mongoClient.getDatabase("Hospital");
                mongoCollection = mongoDatabase.getCollection("Patient");

                if (mongoCollection == null) {
                    System.out.println(user);
                    System.out.println(mongoClient);
                    System.out.println(mongoDatabase);
                    System.out.println("Collection Null ma cung xai?");
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillPatientInfor();
            }
        });

    }

    private void fillPatientInfor() {
        //Inputs
        String fullName = name.getText().toString().trim();
        String userName = username.getText().toString().trim();
        String birthDay = birthday.getText().toString().trim();
        String symTomps = symtomps.getText().toString().trim();

//        username.setText();
//        username.setFocusable(false);
//        name.setText(patient.getName());
//        name.setFocusable(false);
//        birthday.setText("11111");
//        symtomps.setText("");

//        progressBar.setVisibility(View.VISIBLE);


        Log.v("updating","updating");
        Document filter = new Document().append("username", userName);
//        Document update = new Document().append("$set", new Document().append("data", "Finding"));
        Document updatedPatient = new Document().append("name", fullName).append("username", userName).
                append("birthday", birthDay).append("symtomps", symTomps);

        mongoCollection.updateOne(filter, updatedPatient, new UpdateOptions().upsert(true)).getAsync(result -> {
            if(result.isSuccess()) {
                Intent intent = new Intent(ExitHospitalActivity.this, ViewPatientsList.class);
                intent.putExtra("isUpdated", true);
                startActivity(intent);

                long numModified = result.get().getModifiedCount();
                if (numModified == 1) {
                    Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_LONG).show();
                    Log.v("Update", "Successfully updated document");
                } else {
                    Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_LONG).show();
                    Log.v("Update", "Inserted new document");
                }
            } else {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                Log.v("Update", "Failed to update document: " + result.getError().toString());
            }
        });



        System.out.println("Clicked register");
        Log.v("updating", "updating");




//        mongoCollection.findOne(filter).getAsync(r -> {
//            if (r.isSuccess()) {
//                mongoCollection.updateOne(filter, newPatient, new UpdateOptions().upsert(true)).getAsync(r1 -> {
//                    if (r1.isSuccess()) {
//                        Intent intent = new Intent(ExitHospitalActivity.this, ViewPatientsList.class);
//                        startActivity(intent);
//
//                        long numModified = r1.get().getModifiedCount();
//                        if (numModified == 1) {
//                            Toast.makeText(getApplicationContext(), "Đã gửi thành công", Toast.LENGTH_LONG).show();
////                    Log.v("Update", "Successfully updated document");
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Đã gửi thành công!", Toast.LENGTH_LONG).show();
////                    Log.v("Update", "Inserted new document");
//                        }
//
//
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
//                        Log.v("Update", "Failed to update document: " + r.getError().toString());
//                    }
//                });
//
//            }
//        });

    }
}
