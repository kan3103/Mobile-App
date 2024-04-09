package com.example.mobile_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//Test library
import android.widget.EditText;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

//import realm's library
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

import org.bson.Document;
import java.util.ArrayList;

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

    // Set your Realm app ID in the appId variable
    private EditText dataEditText;
    private App app;

    String Appid = "mobileapp-fyjbw";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
//    EditText editText;
    Button button,button1,button2;
    TextView textView;
//    String data;
//     User user;
    MongoCollection<Document> mongoCollection;
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
        Credentials credentials = Credentials.emailPassword("khanglytronVN@KL.com","123456");
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

         //Register a new user (test)
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

        //test QueryData
//        editText = findViewById(R.id.data);
//        button = findViewById(R.id.addData);
//        button1 = findViewById(R.id.findDataButton);
//        textView = findViewById(R.id.findData);
//        button2 = findViewById(R.id.signin);
//        app = new app(new AppConfiguration.Builder(Appid).build());
//        app.loginAsync(Credentials.anonymous(), new App.Callback<User>() {
//            @Override
//            public void onResult(App.Result<User> result) {
//                if (result.isSuccess()) {
//                    Log.v("User", "Logged In Successfully");
//                    user = app.currentUser();
//                    MongoClient mongoClient = user.getMongoClient("mongodb-atlas");
//                    mongoDatabase = mongoClient.getDatabase("sample_mflix");
//                    mongoCollection = mongoDatabase.getCollection("Test");
//                    Toast.makeText(getApplicationContext(), "Logged In Successfully", Toast.LENGTH_LONG).show();
//                } else {
//                    Log.v("User", "Failed to login");
//                }
//            }
//        });
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                document queryFilter = new Document().append(uniqueId, "19");
//                RealmResultTask<MongoCursor<Document>> findTask = mongoCollection.find(queryFilter).iterator();
//
//                findTask.getAsync(task -> {
//                    if (task.isSuccess()) {
//                        MongoCursor<Document>results = task.get();
//                        if(results.hasNext()){
//                            Log.v("FindFunction","FindSomething");
//                            Document result = results.next();
//                            strings = (ArrayList<String>) result.get("strings");
//                            if(strings == null){
//                                strings = new ArrayList<>();
//                            }
//                            String data = editText.getText().toString();
//                            strings.add(data);
//                            results.append("strings",strings);
//                            mongoCollection.updateOne(queryFilter,results).getAsync(task1 -> {
//                                if(task1.isSuccess()){
//                                    Log.v("Data","Successfully updated the document.");
//                                }else{
//                                    Log.e("Data","failed to update the document with: ",task1.getError().toString());
//                                }
//                            });
//                    } else {
//                        Log.e("Data", "failed to find documents with: ", task.getError());
//                    }
//                });
//            }
//        });
//        }
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