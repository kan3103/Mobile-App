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
import com.example.mobile_app.ui.register.RegisterActivity;

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
    Button loginButton, registerButton;
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

//        System.out.println("Success");
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        username = findViewById(R.id.loginPageEmailEditText);
        password = findViewById(R.id.loginPagePasswordEditText);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.registerButton);

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
                    Document document = new Document().append("userName",name);
                    mongoCollection.findOne(document).getAsync( result -> {
                        if(result.isSuccess()){
                            if(result.get()!=null){
                                Document dataa = result.get();
                                true_data = dataa.getString("password");
                                if(true_data.equals(password.getText().toString())) {
                                    Login login = new Login();
                                    userInterface user = login.createUser("Patient", name, true_data);
                                    setPatient(user, dataa);
                                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("userobject", (patientUser) user);
                                    startActivity(intent);
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
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    public MedRecord getMediarecord(Document data){
        MedRecord medRecord = new MedRecord(data.getString("name"),"","","","","","","");
        if(data.containsKey("medicalRecord")){

            ArrayList<Document> arrList = (ArrayList<Document>) data.get("medicalRecord");
            if (arrList.size() >= 1) {
                Document secondElement = arrList.get(0);
                medRecord.addRecord(secondElement.containsKey("weight")?secondElement.getString("weight"):"",secondElement.containsKey("height")?secondElement.getString("height"):"",
                        secondElement.containsKey("doctor")?secondElement.getString("doctor"):"", secondElement.containsKey("nurse")?secondElement.getString("nurse"):"",secondElement.containsKey("dateIn")?secondElement.getString("dateIn"):"",
                        secondElement.containsKey("reDate")?secondElement.getString("reDate"):"",secondElement.containsKey("specialty")?secondElement.getString("specialty"):"");
            } else {
                                Log.v("Data Success", "Array does not contain a second element");
            }
        }
        return medRecord;
    }
    public void setPatient(userInterface user, Document dataa){
        ((patientUser) user).setMedicalRecord(getMediarecord(dataa));
        ((patientUser) user).setSex(dataa.getString("sex"));
        ((patientUser) user).setId(dataa.getString("id"));
        ((patientUser) user).setNationality(dataa.getString("nationality"));
        ((patientUser) user).setBirth(dataa.getString("birth"));
    }
}