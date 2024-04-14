package com.example.mobile_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import org.bson.Document;

import com.example.mobile_app.api.MedicalRecord.MedRecord;
import com.example.mobile_app.api.user.factoryUser.Login;
import com.example.mobile_app.api.user.userObject.adminUser;
import com.example.mobile_app.api.user.userObject.doctorUser;
import com.example.mobile_app.api.user.userObject.patientUser;
import com.example.mobile_app.api.user.userObject.userInterface;
import com.example.mobile_app.databinding.ActivityLoginBinding;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.options.UpdateOptions;

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
    String selectedText;
    RadioGroup radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("Success");
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        username = findViewById(R.id.loginPageEmailEditText);
        password = findViewById(R.id.loginPagePasswordEditText);
        loginButton = findViewById(R.id.login_button);
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
                user = app.currentUser();
                mongoClient = user.getMongoClient("mongodb-atlas");
                mongoDatabase = mongoClient.getDatabase("Hospital");
                mongoCollection = mongoDatabase.getCollection("Doctor");
            }
        });
        // Tạo một đối tượng Login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString().trim();
                if(name=="") {
                    Toast.makeText(getApplicationContext(),"Enter the username and password",Toast.LENGTH_LONG).show();
                    return;
                }
                RadioGroup radioGroup = findViewById(R.id.radiogroup);
                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (selectedRadioButtonId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                    selectedText = selectedRadioButton.getText().toString();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Select role",Toast.LENGTH_LONG).show();
                    return;
                }
                if(selectedText.equals("Admin")){
                    mongoCollection = mongoDatabase.getCollection(selectedText);
                    Document document = new Document().append("username",name);
                    mongoCollection.findOne(document).getAsync( result -> {
                        if(result.isSuccess()){
                            Log.v("hee","ok rooif");
                            if(result.get()!=null){
                                Document dataa = result.get();
                                true_data = dataa.getString("password");

                                if(true_data.equals(password.getText().toString())) {
                                    Login login = new Login();
                                    // Thực hiện đăng nhập
                                    userInterface user = login.createUser("Admin", name, true_data);
                                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("userobject", (adminUser) user);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Wrong username or password",Toast.LENGTH_LONG).show();
                                }
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Wrong username or password",Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(),"FAIL",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else if(selectedText.equals("Doctor")){
                    mongoCollection = mongoDatabase.getCollection(selectedText);
                    Document document = new Document().append("username",name);
                    mongoCollection.findOne(document).getAsync( result -> {
                        if(result.isSuccess()){
                            Log.v("hee","ok rooif");
                            if(result.get()!=null){
                                Document dataa = result.get();
                            true_data = dataa.getString("password");
                                if(true_data.equals(password.getText().toString())){
                                    Login login = new Login();
                                    // Thực hiện đăng nhập
                                    userInterface user = login.createUser("Doctor", name, true_data);
                                    Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("userobject", (doctorUser) user);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Wrong username or password",Toast.LENGTH_LONG).show();
                                }
                                getMediarecord(dataa);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Wrong username or password",Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(),"FAIL",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    mongoCollection = mongoDatabase.getCollection(selectedText);
                    Document document = new Document().append("name",name);
                    mongoCollection.findOne(document).getAsync( result -> {
                        if(result.isSuccess()){
                            if(result.get()!=null){
                                Document dataa = result.get();
                                Login login = new Login();
                                userInterface user = login.createUser("Patient", name, "true_data");
                                ((patientUser) user).setMedicalRecord(getMediarecord(dataa));
                                Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("userobject", (patientUser) user);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Wrong username or password",Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(),"FAIL",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }
    public MedRecord getMediarecord(Document data){
        MedRecord medRecord = new MedRecord(data.getString("name"),"","","","","","","");
//        medRecord.addRecord();
        if(data.containsKey("medicalRecord")){
            ArrayList<Document> arrList = (ArrayList<Document>) data.get("medicalRecord");
            if (arrList.size() >= 1) {
                Document secondElement = arrList.get(0);
                medRecord.addRecord(secondElement.getString("weight"),secondElement.getString("height"),secondElement.getString("doctor"),
                        "",secondElement.getString("dateIn"),"","");


            } else {
                                Log.v("Data Success", "Array does not contain a second element");
            }
        }
        return medRecord;
    }
//    public static List<Document> createMedicalRecord() {
//        List<Document> medications = new ArrayList<>();
//
//        Document medication1 = new Document("name", "Medication 1")
//                .append("dosage", "10mg");
//        medications.add(medication1);
//
//        Document medication2 = new Document("name", "Medication 2")
//                .append("dosage", "20mg");
//        medications.add(medication2);
//
//        return medications;
//    }
    // Find an element in array
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Document queryFilter = new Document().append("arr.0", new Document("$exists", true));
//                mongoCollection.findOne(queryFilter).getAsync(result -> {
//                    if(result.isSuccess())
//                    {
//                        Toast.makeText(getApplicationContext(),"Found",Toast.LENGTH_LONG).show();
//                        Document resultData = result.get();
//                        Log.v("Data Success", resultData.toString());
//                        if (resultData.containsKey("arr")) {
//                            ArrayList<Document> arrList = (ArrayList<Document>) resultData.get("arr");
//                            if (arrList.size() > 1) {
//                                Document secondElement = arrList.get(0);
//                                textView.setText(secondElement.toJson());
//                            } else {
//                                Log.v("Data Success", "Array does not contain a second element");
//                            }
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

}