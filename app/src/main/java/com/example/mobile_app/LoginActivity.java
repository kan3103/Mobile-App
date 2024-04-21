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
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

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

        app.getEmailPassword().registerUserAsync("khanglytronVN@KL.com", "123456", it -> {
            if (it.isSuccess()) {
                Log.v("User", "Successfully registered user");
            } else {
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
                if (name == "") {
                    Toast.makeText(getApplicationContext(), "Enter the username and password", Toast.LENGTH_LONG).show();
                    return;
                }
                RadioGroup radioGroup = findViewById(R.id.radiogroup);
                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (selectedRadioButtonId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                    selectedText = selectedRadioButton.getText().toString();
                } else {
                    Toast.makeText(getApplicationContext(), "Select role", Toast.LENGTH_LONG).show();
                    return;
                }
                if (selectedText.equals("Admin")) {
                    mongoCollection = mongoDatabase.getCollection(selectedText);
                    Document document = new Document().append("username", name);
                    mongoCollection.findOne(document).getAsync(result -> {
                        if (result.isSuccess()) {
                            Log.v("hee", "ok rooif");
                            if (result.get() != null) {
                                Document dataa = result.get();
                                if (dataa.containsKey("password"))
                                    true_data = dataa.getString("password");
                                else return;

                                if (true_data.equals(password.getText().toString())) {
                                    Login login = new Login();
                                    // Thực hiện đăng nhập
                                    userInterface user = login.createUser("Admin", name, true_data);
                                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("userobject", (adminUser) user);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "FAIL", Toast.LENGTH_LONG).show();
                        }
                    });
                } else if (selectedText.equals("Doctor")) {
                    mongoCollection = mongoDatabase.getCollection(selectedText);
                    Document document = new Document().append("username", name);
                    mongoCollection.findOne(document).getAsync(result -> {
                        if (result.isSuccess()) {
                            Log.v("hee", "ok rooif");
                            ArrayList<Document> documentArrayList;
                            if (result.get() != null) {
                                Document dataa = result.get();
                                true_data = dataa.getString("password");
                                ArrayList<patientUser> he = new ArrayList<>();
                                if (dataa.containsKey("patientList")) {
                                    documentArrayList = (ArrayList<Document>) dataa.get("patientList");
                                    int totalCount = documentArrayList.size(); // Số lượng callback cần đợi
                                    AtomicInteger callbackCount = new AtomicInteger(0); // Biến đếm để theo dõi số lượng callback đã hoàn thành
                                    if(totalCount==0){
                                        if (true_data.equals(password.getText().toString())) {
                                            Log.v("okee","hichic");
                                            Login login = new Login();
                                            // Thực hiện đăng nhập
                                            userInterface user = login.createUser("Doctor", name, true_data);
                                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                                            setDoctor(user, dataa, he);
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            intent.putExtra("userobject", (doctorUser) user);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    for (Document run : documentArrayList) {
                                        patientUser new_patient = new patientUser(run.containsKey("username")?run.getString("username"):"",run.containsKey("name")?run.getString("name"):"",run.containsKey("phoneNumber")?run.getString("phoneNumber"):"",run.containsKey("symptoms")?run.getString("symptoms"):"");
                                        MongoCollection<Document> mongoCollection1 = mongoDatabase.getCollection("Patient");
                                        Document filter = new Document().append("username",run.getString("username"));
                                        mongoCollection1.findOne(filter).getAsync(result1 -> {
                                            if(result1.isSuccess()){
                                                Document data= result1.get();
                                                new_patient.setMedicalRecord(getMediarecord(data));
                                                Log.v("ko duowc", String.valueOf(new_patient.getMedicalRecord().getRecords().size()));
                                                he.add(new_patient);
                                                callbackCount.incrementAndGet();
                                                if (callbackCount.get() == totalCount){
                                                    if (true_data.equals(password.getText().toString())) {
                                                        Log.v("okee","hichic");
                                                        Login login = new Login();
                                                        // Thực hiện đăng nhập
                                                        userInterface user = login.createUser("Doctor", name, true_data);
                                                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                                                        setDoctor(user, dataa, he);
                                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                        intent.putExtra("userobject", (doctorUser) user);
                                                        startActivity(intent);
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            }
                                            else{
                                                Log.v("okee","hichickodccc");
                                            }
                                        });

                                    };
                                }


                            } else {
                                Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "FAIL", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    mongoCollection = mongoDatabase.getCollection(selectedText);
                    Document document = new Document().append("username", name);
                    mongoCollection.findOne(document).getAsync(result -> {
                        if (result.isSuccess()) {
                            if (result.get() != null) {
                                Document dataa = result.get();
                                true_data = dataa.getString("password");

                                if (true_data.equals(password.getText().toString())) {
                                    Login login = new Login();
                                    userInterface user = login.createUser("Patient", name, true_data);
                                    setPatient(user, dataa);
                                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("userobject", (patientUser) user);
                                    startActivity(intent);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "FAIL", Toast.LENGTH_LONG).show();
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

    public MedRecord getMediarecord(Document data) {
        MedRecord medRecord = new MedRecord(data.getString("name"), "", "", "", "", "", "","");
        if (data.containsKey("medicalRecord")) {

            ArrayList<Document> arrList = (ArrayList<Document>) data.get("medicalRecord");

            if(arrList==null) {
                medRecord.setRecords(null);
                return medRecord;};
            for (int i = 0; i < arrList.size(); ++i) {
                Document secondElement = arrList.get(i);
                medRecord.addRecord(secondElement.containsKey("weight") ? secondElement.getString("weight") : "", secondElement.containsKey("height") ? secondElement.getString("height") : "",
                        secondElement.containsKey("doctor") ? secondElement.getString("doctor") : "", secondElement.containsKey("nurse") ? secondElement.getString("nurse") : "", secondElement.containsKey("dateIn") ? secondElement.getString("dateIn") : "",
                        secondElement.containsKey("reDate") ? secondElement.getString("reDate") : "", secondElement.containsKey("specialty") ? secondElement.getString("specialty") : "",secondElement.containsKey("bloodPressure") ? secondElement.getString("bloodPressure") : ""
                ,secondElement.containsKey("testResults") ? secondElement.getString("testResults") : "");
            }
        }
        return medRecord;
    }

    public void setPatient(userInterface user, Document dataa) {
        ((patientUser) user).setMedicalRecord(getMediarecord(dataa));
        ((patientUser) user).setSex(dataa.getString("sex"));
        ((patientUser) user).setId(dataa.getString("id"));
        ((patientUser) user).setNationality(dataa.getString("nationality"));
        ((patientUser) user).setBirth(dataa.getString("birthday"));
        ((patientUser) user).setName(dataa.getString("name"));
        ((patientUser) user).setCitizenID(dataa.getString("citizenID"));
        ((patientUser) user).setPhoneNumber(dataa.getString("phoneNum"));
    }

    public void setDoctor(userInterface user, Document dataa, ArrayList<patientUser> he) {
        ((doctorUser) user).setName(dataa.containsKey("name") ? dataa.getString("name") : "");
        ((doctorUser) user).setExperience(dataa.containsKey("experience") ? dataa.getString("experience") : "");
        ((doctorUser) user).setSex(dataa.containsKey("sex") ? dataa.getString("sex") : "");
        ((doctorUser) user).setSpecialty(dataa.containsKey("specialty") ? dataa.getString("specialty") : "");
        ((doctorUser) user).setBirthday(dataa.containsKey("birthday") ? dataa.getString("birthday") : "");
        ((doctorUser) user).setNationality(dataa.containsKey("nationality") ? dataa.getString("nationality") : "");
        ((doctorUser) user).setPhoneNum(dataa.containsKey("numPhone") ? dataa.getString("numPhone") : "");
        if (he.size() != 0) ((doctorUser) user).setPatientList(he);
    }
}