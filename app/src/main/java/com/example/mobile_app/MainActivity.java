package com.example.mobile_app;

import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.mobile_app.api.user.userObject.adminUser;
import com.example.mobile_app.api.user.userObject.doctorUser;
import com.example.mobile_app.api.user.userObject.patientUser;
import com.example.mobile_app.api.user.userObject.userInterface;
import com.example.mobile_app.ui.settings.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mobile_app.databinding.ActivityMainBinding;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.FindIterable;
import org.bson.Document;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String usertype = "Admin";
    private userInterface user;
    private User user1;
    private Button btnSend;
    String Appid = "mobileapp-fyjbw";
    private App app;
    private MongoClient mongoClient;
    public MongoDatabase mongoDatabase;
    public   MongoCollection mongoCollection;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Success");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressBar = (ProgressBar) findViewById(R.id.wait);

        progressBar.setVisibility(View.VISIBLE);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,R.id.navigation_dashboard, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        Realm.init(getApplicationContext());

        app = new App(new AppConfiguration.Builder(Appid).build());
        Credentials credentials = Credentials.emailPassword("khanglytronVN@KL.com", "123456");

        app.getEmailPassword().registerUserAsync("khanglytronVN@KL.com", "123456",it->{
            if(it.isSuccess()){
                Log.v("User", "Successfully registered user");
            }else{
                Log.v("User", "Failed to register user");
            }
        });
        app.loginAsync(credentials, new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                user1 = app.currentUser();
                mongoClient = user1.getMongoClient("mongodb-atlas");
                mongoDatabase = mongoClient.getDatabase("Hospital");
                mongoCollection = mongoDatabase.getCollection("Doctor");
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        Intent intent = getIntent();
        if(intent != null) {
            user = (userInterface) intent.getSerializableExtra("userobject");
        }
        if (user instanceof adminUser) {
            sendDataToSettingsFragment(false);
        } else if(user instanceof doctorUser) {
            sendDataToSettingsFragment(false);
        }
        else{
            Log.v("ok","oke");
            sendDataToSettingsFragment(false);
        }
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    public void sendDataToSettingsFragment(boolean check) {
        if(!check) return;
        SettingsFragment settingsFragment = new SettingsFragment();
        Bundle bundle = new Bundle();
        if(user instanceof adminUser) usertype = "Admin";
        else if(user instanceof patientUser) usertype = "Patient";
        else usertype = "Doctor";
        bundle.putString("user", usertype);
        settingsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_settings, settingsFragment)
                .addToBackStack(null)
                .commit();
    }
    public userInterface getUser() {
        return user;
    }
}