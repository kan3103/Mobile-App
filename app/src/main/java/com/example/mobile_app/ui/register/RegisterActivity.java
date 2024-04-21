package com.example.mobile_app.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_app.LoginActivity;
import com.example.mobile_app.R;
import com.example.mobile_app.databinding.RegisterViewBinding;

import org.bson.Document;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.options.UpdateOptions;

public class RegisterActivity extends AppCompatActivity {
    String Appid = "mobileapp-fyjbw";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    MongoCollection<Document> mongoCollection;
    private RegisterViewBinding binding;
    private Button completeRegister;
    private EditText editTextFullName,citizenid, editTextUsername, editTextPassword, editTextMobile, editTextAddress, editTextBirthday, editTextNationality, editTextJob;
    private RadioButton gender;
    private ProgressBar progressBar;
    private User user;

    private App app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = RegisterViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        completeRegister = findViewById(R.id.buttonRegisterPatient);

        editTextFullName = (EditText) findViewById(R.id.patient_name);
        editTextUsername = (EditText) findViewById(R.id.patient_username);
        editTextPassword = (EditText) findViewById(R.id.patient_password);
        editTextAddress = (EditText) findViewById(R.id.patient_address);
        editTextMobile = (EditText) findViewById(R.id.patient_phone);
        editTextBirthday = (EditText) findViewById(R.id.patient_birthday);
        editTextJob = (EditText) findViewById(R.id.patient_job);
        citizenid = (EditText) findViewById(R.id.register_nationality_field);
        gender = (RadioButton) findViewById(R.id.radioPatient);

        progressBar = (ProgressBar) findViewById(R.id.registerPatientIndeterminateProgressbar);

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


        System.out.println("Register");

        app.loginAsync(credentials, new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                user = app.currentUser();
                mongoClient = user.getMongoClient("mongodb-atlas");
                mongoDatabase = mongoClient.getDatabase("Hospital");
                mongoCollection = mongoDatabase.getCollection("Patient");

                if (mongoCollection == null) {
                    System.out.println(user);
                    System.out.println(mongoClient);
                    System.out.println(mongoDatabase);
                    System.out.println("Collection Null ma cung xai?");
                }
            }
        });


        completeRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPatient();
            }
        });
    }

    private void registerPatient() {
        //Inputs
        String fullName = editTextFullName.getText().toString().trim();
        String userName = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String mobile = editTextMobile.getText().toString().trim();
        String birthday = editTextBirthday.getText().toString().trim();
        String citizenID = citizenid.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String job = editTextJob.getText().toString().trim();

        //Validations
        if (fullName.isEmpty()) {
            editTextFullName.setError("Full Name is required");
            editTextFullName.requestFocus();
            return;
        }

        if (userName.isEmpty()) {
            editTextUsername.setError("Username is required");
            editTextUsername.requestFocus();
            return;
        }

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        int selectedGenderRadioBtnId = radioGroup.getCheckedRadioButtonId();
        if (selectedGenderRadioBtnId == -1) {
            Toast.makeText(getApplicationContext(), "Vui lòng chọn giới tính!", Toast.LENGTH_LONG).show();
            radioGroup.requestFocus();
            return;
        }
        RadioButton selectedGenderBtn = findViewById(selectedGenderRadioBtnId);
        String gender = selectedGenderBtn.getText().toString();

//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            editTextEmail.setError("Please provide valid email address");
//            editTextEmail.requestFocus();
//            return;
//        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password should be 6 characters long");
            editTextPassword.requestFocus();
            return;
        }

        if (mobile.isEmpty()) {
            editTextMobile.setError("Contact number is required");
            editTextMobile.requestFocus();
            return;
        }

        if (!Patterns.PHONE.matcher(mobile).matches() || mobile.length() != 10) {
            editTextMobile.setError("Invalid contact number");
            editTextMobile.requestFocus();
            return;
        }

        if (birthday.isEmpty()) {
            editTextBirthday.setText("");
        }
//        else{
//            String[] arr = symptoms.split(",");
//
//            for(int i=0;i< arr.length;i++){
//                symp.add(arr[i].trim());
//            }
//        }

        if (address.isEmpty()) {
//            editTextAddress.setError("Address is required");
//            editTextAddress.requestFocus();
            editTextBirthday.setText("");
//            return;
        }

        if (job.isEmpty()) {
//            editTextAddress.setError("Address is required");
//            editTextAddress.requestFocus();
            editTextBirthday.setText("");
//            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        System.out.println("Clicked register");
        Log.v("updating", "updating");
        Document newPatient = new Document().append("name", fullName).append("username", userName).append("password", password)
                .append("phoneNum", mobile).append("birthday", birthday).append("citizenID", citizenID)
                .append("address", address).append("occupation", job).append("sex", gender);

        Document filter = new Document().append("username", userName);

        mongoCollection.findOne(filter).getAsync(r -> {
            if (r.isSuccess()) {
                Document rData = r.get();
                if (rData != null) {
                    editTextUsername.setError("Username is exist");
                    editTextUsername.requestFocus();

                    return;
                }
                mongoCollection.updateOne(filter, newPatient, new UpdateOptions().upsert(true)).getAsync(r1 -> {
                    if (r1.isSuccess()) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);

                        long numModified = r1.get().getModifiedCount();
                        if (numModified == 1) {
                            Toast.makeText(getApplicationContext(), "Đã đăng kí thành công", Toast.LENGTH_LONG).show();
//                    Log.v("Update", "Successfully updated document");
                        } else {
                            Toast.makeText(getApplicationContext(), "Đã đăng kí thành công!", Toast.LENGTH_LONG).show();
//                    Log.v("Update", "Inserted new document");
                        }


                    } else {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                        Log.v("Update", "Failed to update document: " + r.getError().toString());
                    }
                });

            }
        });


//        mongoCollection = mongoDatabase.getCollection("Patient");
//        Document document = new Document().append("username",name);
//        mongoCollection.findOne(document).getAsync( result -> {
//            if(result.isSuccess()){
//                Log.v("hee","ok rooif");
//                Document dataa = result.get();
//                true_data = dataa.getString("password");
//
//                if(true_data.equals(password.getText().toString())){
//                    Login login = new Login();
//                    // Thực hiện đăng nhập
//                    userInterface user = login.createUser("Admin", name, true_data);
//                    Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    intent.putExtra("userobject", (adminUser) user);
//                    startActivity(intent);
//                }
//            }else {
//                Toast.makeText(getApplicationContext(),"FAIL",Toast.LENGTH_LONG).show();
//            }
//        });
    }
}
