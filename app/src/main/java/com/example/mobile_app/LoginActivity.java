package com.example.mobile_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.bson.Document;

import com.example.mobile_app.databinding.ActivityLoginBinding;

import androidx.appcompat.app.AppCompatActivity;


import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    private View view;

    Button loginButton;
    EditText username;
    EditText password;
    private App app;

    String Appid = "mobileapp-fyjbw";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    MongoCollection<Document> mongoCollection;
    String id;
    User user;
    String true_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("Success");

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        username = findViewById(R.id.loginPageEmailEditText);
        password = findViewById(R.id.loginPagePasswordEditText);
//        view = ActivityMainBinding.inflate(getLayoutInflater());
        loginButton = findViewById(R.id.login_button);
        Realm.init(getApplicationContext());
//        Realm.init(this);
        App app = new App(new AppConfiguration.Builder(Appid).build());
        Credentials credentials = Credentials.emailPassword("khanglytronVN@KL.com", "123456");
        app.loginAsync(credentials, new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                user = app.currentUser();
                mongoClient = user.getMongoClient("mongodb-atlas");
                mongoDatabase = mongoClient.getDatabase("sample_mflix");
                mongoCollection = mongoDatabase.getCollection("Test");
                Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString();
                Document document = new Document().append("name",name);
                mongoCollection.findOne(document).getAsync( result -> {
                    if(result.isSuccess()){
                        Log.v("hee","ok rooif");
                        Document dataa = result.get();
                        true_data = dataa.getString("pass");
                        if(true_data.equals(password.getText().toString()))
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }else {
                        Log.v("hi","not oke");
                    }
                });
            }
        });
    }
}