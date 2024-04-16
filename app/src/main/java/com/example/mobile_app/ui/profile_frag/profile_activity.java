package com.example.mobile_app.ui.profile_frag;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
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

    public String Appid = "mobileapp-fyjbw";
    private
    MongoDatabase mongoDatabase;
    private MongoClient mongoClient;
    private MongoCollection<Document> mongoCollection;
    private  User user;
    private String userName ;
    private ArrayList<Item_list> items ;
    private ListView listView ;
    private TextView pro5name;
    private App app;
    public userInterface user1;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.profile_layout);
        listView = findViewById(R.id.pro5list);
        pro5name = findViewById(R.id.pro5name);
        Intent intent = getIntent();
        if(intent != null) {
            user1 = (userInterface) intent.getSerializableExtra("userobject");
        }
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
                mongoCollection = mongoDatabase.getCollection(user1.getTypeuser());
                Document document = new Document().append("username", user1.getUsername());
                items = new ArrayList<>();

                mongoCollection.findOne(document).getAsync(result1 -> {
                    if (result1.isSuccess()) {
                        Document doc = result1.get();
                        setpro5(doc);
                    } else {
                        Toast.makeText(getApplicationContext(), "FAIL", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
    public void setpro5(Document doc){
        if(user1.getTypeuser().equals("Patient")) {
            ArrayList<String> keys = new ArrayList<>(Arrays.asList("name", "sex",  "birthday","phoneNum", "nationality", "address", "occupation"));
            ArrayList<String> key2 = new ArrayList<>(Arrays.asList("Name", "Sex",  "Birthday", "Phone number", "Nationality", "Address", "Occupation"));
            for (int index = 0; index < keys.size(); index++) {
                String descrip = doc.containsKey(keys.get(index)) ? doc.getString(keys.get(index)) : "";
                items.add(new Item_list(keys.get(index), descrip));
                Log.v("test",descrip);
            }
            profile_adap adapter = new profile_adap(items, getApplicationContext());
            pro5name.setText(doc.getString("name"));
            listView.setAdapter(adapter);
        }
        else if(user1.getTypeuser().equals("Doctor")){
            ArrayList<String> keys = new ArrayList<>(Arrays.asList("name", "sex",  "birthday", "specialty","phoneNum", "nationality", "address", "occupation"));
            ArrayList<String> key2 = new ArrayList<>(Arrays.asList("Name", "Sex",  "Birthday", "Specialty","Phone number", "Nationality", "Address", "Occupation"));
            for (int index = 0; index < keys.size(); index++) {
                String descrip = doc.containsKey(keys.get(index)) ? doc.getString(keys.get(index)) : "";
                items.add(new Item_list(key2.get(index), descrip));
                Log.v("test",descrip);
            }
            profile_adap adapter = new profile_adap(items, getApplicationContext());
            pro5name.setText(doc.getString("name"));
            listView.setAdapter(adapter);
        }else {
            ArrayList<String> keys = new ArrayList<>(Arrays.asList("name", "sex",  "birthday", "specialty","phoneNum", "nationality", "address", "occupation"));
            ArrayList<String> key2 = new ArrayList<>(Arrays.asList("Name", "Sex",  "Birthday", "Specialty","Phone number", "Nationality", "Address", "Occupation"));
            for (int index = 0; index < keys.size(); index++) {
                String descrip = doc.containsKey(keys.get(index)) ? doc.getString(keys.get(index)) : "";
                items.add(new Item_list(key2.get(index), descrip));
                Log.v("test",descrip);
            }
            profile_adap adapter = new profile_adap(items, getApplicationContext());
            pro5name.setText(doc.getString("name"));
            listView.setAdapter(adapter);
        }

    }
}