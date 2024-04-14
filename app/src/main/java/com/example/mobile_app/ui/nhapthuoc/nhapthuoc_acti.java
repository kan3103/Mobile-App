package com.example.mobile_app.ui.nhapthuoc;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import org.bson.Document;
import com.example.mobile_app.R;
import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.options.UpdateOptions;

public class nhapthuoc_acti extends AppCompatActivity {
    Button button ;
    EditText idthuoc ;
    EditText slthuoc;
    String Appid = "mobileapp-fyjbw";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    MongoCollection<Document> mongoCollection;
    User user;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.nhapthuoc_layout);

        idthuoc = findViewById(R.id.id_thuoc_nhapthuoc);
        slthuoc = findViewById(R.id.soluong_nhapthuoc) ;
        button = findViewById(R.id.button_nhapthuoc) ;


        Realm.init(getApplicationContext());
        App app = new App(new AppConfiguration.Builder(Appid).build());
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
                mongoCollection = mongoDatabase.getCollection("Drug");
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_string, sl_string ;
                id_string = idthuoc.getText().toString();
                sl_string = slthuoc.getText().toString();
                int id = Integer.parseInt(id_string);
                int sl = Integer.parseInt(sl_string);
                transToWareHouse(id, sl);
            }
        });

    }

    public void transToWareHouse(int idthuoc, int soluong){
        Log.v("updating","updating");
        Document filter = new Document().append("idthuoc", idthuoc);
        Document update = new Document().append("$set", new Document().append("id", idthuoc).append("quantity",soluong));

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




}
