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

import java.util.ArrayList;

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
    EditText username,pass,name,birth,specialty,sex,nationality,phone,experience;
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
        sex = findViewById(R.id.add_sex);
        nationality = findViewById(R.id.add_nationality);
        phone = findViewById(R.id.add_phone);
        experience = findViewById(R.id.experience);
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
                mongoCollection.findOne(filter).getAsync(result -> {
                    if(result.isSuccess()){
                        if(result.get()!=null)
                        Toast.makeText(getApplicationContext(),"Đã có người dùng username này", Toast.LENGTH_LONG).show();
                        else{
                            MongoCollection<Document> mongoCollection1 = mongoDatabase.getCollection("Specialty");
                            Document filter1 = new Document().append("name", specialty.getText().toString().trim());
                            mongoCollection1.findOne(filter1).getAsync(result1 -> {
                                if(result1.isSuccess()){
                                    if(result1.get()==null)
                                        Toast.makeText(getApplicationContext(),"Không có khoa này", Toast.LENGTH_LONG).show();
                                    else {
                                        Document document = result1.get();
                                        ArrayList<Document> he = (ArrayList<Document>) document.get("array");
                                        Document new2 = new Document()
                                                .append("username", username.getText().toString().trim())
                                                .append("sex", sex.getText().toString().trim())
                                                .append("name", name.getText().toString().trim())
                                                .append("experience",experience.getText().toString().trim());
                                        he.add(new2);
                                        Document update = document.append("array", he);
                                        mongoCollection1.updateOne(filter1, update).getAsync(result2 -> {});
                                        Document newDoctor = new Document()
                                                .append("username", username.getText().toString().trim())
                                                .append("password", pass.getText().toString().trim())
                                                .append("name", name.getText().toString().trim())
                                                .append("birthday", birth.getText().toString().trim())
                                                .append("specialty", specialty.getText().toString().trim())
                                                .append("sex", sex.getText().toString().trim())
                                                .append("nationality", nationality.getText().toString().trim())
                                                .append("numPhone", phone.getText().toString().trim())
                                                .append("experience",experience.getText().toString().trim());
                                        // Thêm tài liệu mới vào collection "Doctor"
                                        mongoCollection.updateOne(filter, newDoctor, new UpdateOptions().upsert(true)).getAsync(result3 -> {
                                            if (result3.isSuccess()) {
                                                Toast.makeText(getApplicationContext(), "Add Doctor Successful", Toast.LENGTH_LONG).show();
                                                finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Fail to add new", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "ko truy cap duoc", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }else {


                    }
                });


            }
        });

    }
}
