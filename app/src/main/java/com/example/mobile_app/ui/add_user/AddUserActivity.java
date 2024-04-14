package com.example.mobile_app.ui.add_user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mobile_app.LoginActivity;
import com.example.mobile_app.MainActivity;
import com.example.mobile_app.R;

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


public class AddUserActivity extends AppCompatActivity {
    private Button btn1,btn2;
    MainActivity mainActivity;
    private App app;
    String Appid = "mobileapp-fyjbw";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    MongoCollection<Document> mongoCollection;
    User user;
    EditText username,pass,name,birth,specialty;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        btn1 = findViewById(R.id.button_huy);
        btn2 = findViewById(R.id.button_xacnnhan);
        username = findViewById(R.id.add_username);
        pass = findViewById(R.id.add_password);
        name = findViewById(R.id.add_name);
        birth = findViewById(R.id.add_birthday);
        specialty = findViewById(R.id.add_specialty);
        Realm.init(getApplicationContext());
        App app = new App(new AppConfiguration.Builder(Appid).build());
        Credentials credentials = Credentials.emailPassword("khanglytronVN@KL.com", "123456");
        app.loginAsync(credentials, new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                user = app.currentUser();
                mongoClient = user.getMongoClient("mongodb-atlas");
                mongoDatabase = mongoClient.getDatabase("Hospital");
                mongoCollection = mongoDatabase.getCollection("Doctor");
                if(mongoCollection != null)
                    Log.v("hi","oke nha");
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một tài liệu mới với thông tin từ các trường dữ liệu trên giao diện người dùng
                Document filter = new Document().append("username", username.getText().toString().trim());
                Document newDoctor = new Document()
                        .append("username", username.getText().toString().trim())
                        .append("password", pass.getText().toString().trim())
                        .append("name", name.getText().toString().trim())
                        .append("birthday", birth.getText().toString().trim())
                        .append("specialty", specialty.getText().toString().trim());
                // Thêm tài liệu mới vào collection "Doctor"
                mongoCollection.updateOne(filter,newDoctor,new UpdateOptions().upsert(true)).getAsync(result -> {
                    if(result.isSuccess()){
                        Toast.makeText(getApplicationContext(),"Add Doctor Successful", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Fail to add new", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
}
