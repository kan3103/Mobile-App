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
import com.example.mobile_app.databinding.ActivityMainBinding;
import com.example.mobile_app.ui.dashboard.DashboardFragment;
import com.example.mobile_app.ui.dashboard.ViewDoctorsList;
import com.example.mobile_app.ui.profile_frag.profile_activity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.MongoCursor;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.mongo.options.UpdateOptions;
import io.realm.mongodb.mongo.result.InsertOneResult;

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
        App app = new App(new AppConfiguration.Builder(Appid).build());
//        Credentials credentials = Credentials.emailPassword("khanglytronVN@KL.com","123456");

        Realm.init(this);
        app.loginAsync(Credentials.anonymous(), new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                if(result.isSuccess()) {
                    Log.v("User","Logged In Successfully");
                    User user = app.currentUser();
                    mongoClient = user.getMongoClient("mongodb-atlas");
                    mongoDatabase = mongoClient.getDatabase("sample_mflix");
                    mongoCollection = mongoDatabase.getCollection("Test");
                    Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
                } else {
                    Log.v("User","Failed to Login");
                }
            }
        });
        //Register a new user (test)
        app.getEmailPassword().registerUserAsync("khanglytronVN@KL.com", "123456",it->{
            if(it.isSuccess()){
                Log.v("User", "Successfully registered user");
            }else{
                Log.v("User", "Failed to register user");
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = username.getText().toString();
                String text2 = password.getText().toString();

//                Document x = findData("name",text);

                if(text2.equals("19"))
                    startActivity(new Intent(LoginActivity.this, profile_activity.class));
                // Lấy mật khẩu từ EditText password
            };


            private void loginUser() {
                app.loginAsync(Credentials.anonymous(), new App.Callback<User>() {
                    @Override
                    public void onResult(App.Result<User> result) {
                        if(result.isSuccess()) {
                            Log.v("User","Logged In Successfully");
                            User user = app.currentUser();
                            mongoClient = user.getMongoClient("mongodb-atlas");
                            mongoDatabase = mongoClient.getDatabase("sample_mflix");
                            mongoCollection = mongoDatabase.getCollection("Test");
                            Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
                        } else {
                            Log.v("User","Failed to Login");
                        }
                    }
                });
            };
            public Document findData(String key, String value) {
                Document[] returnDoc = new Document[0];
                Document queryFilter = new Document().append(key, value);
                mongoCollection.findOne(queryFilter).getAsync(result -> {
                    if(result.isSuccess())
                    {
                        Document resultData = result.get();
                        Log.v("Data Success", resultData.toString());
                        if (resultData.containsKey("pass")) {
                            returnDoc[0] = new Document();
                            returnDoc[0].append("pass", resultData.getString("pass"));
                        }
                        else {
                            Log.v("Data Success", "Document does not contain 'username' field");
                        };
                    }
                    else
                    {
                        Log.v("Data Error",result.getError().toString());
                    }
                });
                return returnDoc[0];
            }

        });

        System.out.println(loginButton);


//        loginButton.setOnClickListener(v -> {
//            username = findViewById(R.id.loginPageEmailTextField);
//            String text = username.getText().toString();
//
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//        });

//        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}