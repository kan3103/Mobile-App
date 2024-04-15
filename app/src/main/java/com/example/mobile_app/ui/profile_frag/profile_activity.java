package com.example.mobile_app.ui.profile_frag;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_app.LoginActivity;
import com.example.mobile_app.MainActivity;
import com.example.mobile_app.R;
import com.example.mobile_app.api.user.factoryUser.Login;
import com.example.mobile_app.api.user.userObject.adminUser;
import com.example.mobile_app.api.user.userObject.userInterface;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class profile_activity extends AppCompatActivity {

    String Appid = "mobileapp-fyjbw";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    MongoCollection<org.bson.Document> mongoCollection;
    User user;
    String userName ;
    ArrayList<Item_list> items ;
    ListView listView ;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.profile_layout);

        items = new ArrayList<>(); // Khởi tạo items


        Realm.init(getApplicationContext());
        App app = new App(new AppConfiguration.Builder(Appid).build());
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
                mongoCollection = mongoDatabase.getCollection("Patient");
            }
        });
        mongoCollection = mongoDatabase.getCollection(userName);
        org.bson.Document document = new Document().append("username",userName);
        mongoCollection.findOne(document).getAsync(result -> {
            if (result.isSuccess()) {
                org.bson.Document doc = result.get();
                ArrayList<String> keys = new ArrayList<>(Arrays.asList("name", "sex", "userName", "phoneNum", "birthday", "nationality", "address", "occupation"));
                for (int index = 0; index < 8; index++) {
                    String descrip = doc.getString(keys.get(index));
                    items.add(new Item_list(keys.get(index), descrip));
                }

                profile_adap adapter = new profile_adap(items, getApplicationContext());
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(getApplicationContext(), "FAIL", Toast.LENGTH_LONG).show();
            }
        });

    }
}