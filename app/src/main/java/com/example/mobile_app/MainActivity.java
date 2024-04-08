package com.example.mobile_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String user = "Patient";
    private Button btnSend;
    private EditText dataEditText;

    // Set your Realm app ID in the appId variable
    String Appid = "mobileapp-fyjbw";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Success");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up the button and EditText (test)
        dataEditText = (EditText) findViewById(R.id.data);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_settings)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.navigation_settings) {
                    sendDataToSettingsFragment();
                }
            }
        });
        // Initialize the Realm app
        Realm.init(this);
        App app = new App(new AppConfiguration.Builder(Appid).build());
        Credentials credentials = Credentials.emailPassword("gn27082004@gmail.com","27082004");
        // Authenticate the user
        app.loginAsync(credentials, new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                if (result.isSuccess()) {
                    Log.v("User", "Successfully logged in to MongoDB Realm");
                } else {
                    Log.v("User", "Failed to log in to MongoDB Realm");
                }
            }
        });

        // Register a new user (test)
        app.getEmailPassword().registerUserAsync("khanglytronVN@KL.com", "123456",it->{
            if(it.isSuccess()){
                Log.v("User", "Successfully registered user");
            }else{
                Log.v("User", "Failed to register user");
            }
        });
        //test InsertData
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = app.currentUser();
                MongoClient mongoClient = user.getMongoClient("mongodb-atlas");
                mongoDatabase = mongoClient.getDatabase("sample_mflix");
                MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("Test");
                mongoCollection.insertOne(new Document("Doctor", user.getId()).append("data", dataEditText.getText().toString())).getAsync(task -> {
                    if (task.isSuccess()) {
                        Log.v("Data", "Successfully inserted data");
                    } else {
                        Log.v("Data","Error:" + result.getError().toString());
                    }
                });
            }
        });
        }
    }
    public void sendDataToSettingsFragment() {
        SettingsFragment settingsFragment = new SettingsFragment();
        Bundle bundle = new Bundle();
        if(user.equals("Patient")) user = "Doctor";
        else user = "Patient";
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