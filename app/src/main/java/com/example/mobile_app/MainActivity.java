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
import io.realm.mongodb.mongo.options.UpdateOptions;
import io.realm.mongodb.mongo.result.InsertOneResult;


import org.bson.Document;
import java.util.ArrayList;
import java.util.Date;

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
    EditText editText;
    Button button,button1,button2;
    TextView textView;
    String data;
    MongoCollection<Document> mongoCollection;
    ArrayList<String> strings = new ArrayList<>();
    String key;
    String value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Success");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up the button and EditText (test)
        editText = findViewById(R.id.data);
        button = findViewById(R.id.adddata);
        button1 = findViewById(R.id.findDataButton);
        textView = findViewById(R.id.findData);
        button2 = findViewById(R.id.signin);
        button = findViewById(R.id.adddata);
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
        Realm.init(getApplicationContext());
//        Realm.init(this);
        App app = new App(new AppConfiguration.Builder(Appid).build());
        Credentials credentials = Credentials.emailPassword("khanglytronVN@KL.com","123456");

        //Register a new user (test)
        app.getEmailPassword().registerUserAsync("khanglytronVN@KL.com", "123456",it->{
            if(it.isSuccess()){
                Log.v("User", "Successfully registered user");
            }else{
                Log.v("User", "Failed to register user");
            }
        });

        // Authenticate the user
//        app.loginAsync(Credentials.anonymous(), new App.Callback<User>() {
//            @Override
//            public void onResult(App.Result<User> result) {
//                if (result.isSuccess()) {
//                    Log.v("User", "Successfully logged in");
//                } else {
//                    Log.v("User", "Failed to log in");
//                }
//            }
//        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User currentUser = app.currentUser();
                if (currentUser != null) {
                    currentUser.logOutAsync(result -> {
                        if(result.isSuccess()) {
                            Log.v("Logged Out","Logged Out");
                        } else {
                            Log.v("Logout failed", result.getError().toString());
                        }
                        loginUser();
                    });
                } else {
                    loginUser();
                }
            }

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
            }
        });

        //test InsertData
        //test QueryData
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("updating","updating");
                Document filter = new Document().append("myid", "2345");
                Document update = new Document().append("$set", new Document().append("data", "Finding is working after stopping"));

                mongoCollection.updateOne(filter, update, new UpdateOptions().upsert(true)).getAsync(result -> {
                    if(result.isSuccess()) {
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
            }
        });

        // Find a key-value

//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Document queryFilter =  new Document().append("myid","1000");
//                mongoCollection.findOne(queryFilter).getAsync(result -> {
//                    if(result.isSuccess())
//                    {
//                        Document resultData = result.get();
//                        if (resultData != null) {
//                            Toast.makeText(getApplicationContext(),"Found",Toast.LENGTH_LONG).show();
//                            Log.v("Data Success", resultData.toString());
//                            if (resultData.containsKey("data")) {
//                                textView.setText(resultData.getString("data"));
//                            } else {
//                                Log.v("Data Success", "Document does not contain a 'data' field");
//                            }
//                        } else {
//                            Toast.makeText(getApplicationContext(),"Not Found",Toast.LENGTH_LONG).show();
//                            Log.v("Data Error","Document not found");
//                        }
//                    }
//                    else
//                    {
//                        Toast.makeText(getApplicationContext(),"Not Found",Toast.LENGTH_LONG).show();
//                        Log.v("Data Error",result.getError().toString());
//                    }
//                });
//            }
//        });

        // Find all data in array
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Document queryFilter = new Document().append("arr", new Document("$exists", true));
//                mongoCollection.findOne(queryFilter).getAsync(result -> {
//                    if(result.isSuccess())
//                    {
//                        Toast.makeText(getApplicationContext(),"Found",Toast.LENGTH_LONG).show();
//                        Document resultData = result.get();
//                        Log.v("Data Success", resultData.toString());
//                        if (resultData.containsKey("arr")) {
//                            ArrayList<Document> arrList = (ArrayList<Document>) resultData.get("arr");
//                            textView.setText(arrList.toString());
//                        } else {
//                            Log.v("Data Success", "Document does not contain an 'arr' field");
//                        }
//                    }
//                    else
//                    {
//                        Toast.makeText(getApplicationContext(),"Not Found",Toast.LENGTH_LONG).show();
//                        Log.v("Data Error",result.getError().toString());
//                    }
//                });
//            }
//        });

        // Find an element in array
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Document queryFilter = new Document().append("arr.0", new Document("$exists", true));
                mongoCollection.findOne(queryFilter).getAsync(result -> {
                    if(result.isSuccess())
                    {
                        Toast.makeText(getApplicationContext(),"Found",Toast.LENGTH_LONG).show();
                        Document resultData = result.get();
                        Log.v("Data Success", resultData.toString());
                        if (resultData.containsKey("arr")) {
                            ArrayList<Document> arrList = (ArrayList<Document>) resultData.get("arr");
                            if (arrList.size() > 1) {
                                Document secondElement = arrList.get(0);
                                textView.setText(secondElement.toJson());
                            } else {
                                Log.v("Data Success", "Array does not contain a second element");
                            }
                        } else {
                            Log.v("Data Success", "Document does not contain an 'arr' field");
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Not Found",Toast.LENGTH_LONG).show();
                        Log.v("Data Error",result.getError().toString());
                    }
                });
            }
        });
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