package com.example.mobile_app;

import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.Button;


import com.example.mobile_app.api.user.userObject.doctorUser;
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

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
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
    private String user = "Admin";
    private Button btnSend;
    String Appid = "mobileapp-fyjbw";
    private App realmApp;
    private MongoClient mongoClient;
    private MongoDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Success");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.navigation_settings) {
                    sendDataToSettingsFragment(false);
                }
            }
        });

    }
    public void sendDataToSettingsFragment(boolean check) {
        if(!check) return;
        SettingsFragment settingsFragment = new SettingsFragment();
        Bundle bundle = new Bundle();
        if(user.equals("Patient")) user = "Doctor";
        else if(user.equals("Admin")) user = "Patient";
        else user = "Admin";
        bundle.putString("user", user);
        settingsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_settings, settingsFragment)
                .addToBackStack(null)
                .commit();
    }
    public String getUser() {
        return user;
    }
}