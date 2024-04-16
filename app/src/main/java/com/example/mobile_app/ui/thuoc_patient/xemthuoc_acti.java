package com.example.mobile_app.ui.thuoc_patient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_app.R;
import com.example.mobile_app.api.user.userObject.userInterface;

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

public class xemthuoc_acti extends AppCompatActivity {

    public String Appid = "mobileapp-fyjbw";
    private App app;
    User user;
    private
    MongoDatabase mongoDatabase;
    private MongoClient mongoClient;
    private MongoCollection<Document> mongoCollection;
    public userInterface user1;
    Credentials credentials;
    ListView listView ;
    ArrayList<in4medicine> items = new ArrayList<>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.xemthuoc_layout);

        khoitao();
        listView = findViewById(R.id.listthuoc_xemthuoc) ;
        Intent intent = getIntent();
        if(intent != null) {
            user1 = (userInterface) intent.getSerializableExtra("userobject");
        }

        app.loginAsync(credentials, new App.Callback<User>() {

            @Override
            public void onResult(App.Result<User> result) {
                user = app.currentUser();
                mongoClient = user.getMongoClient("mongodb-atlas");
                mongoDatabase = mongoClient.getDatabase("Hospital");
                mongoCollection = mongoDatabase.getCollection("Patient");
                Document document = new Document().append("username" , user1.getUsername() );

                mongoCollection.findOne(document).getAsync(result1 -> {
                    if (result1.isSuccess()) {
                        Document doc = result1.get();

                        ArrayList<Document> tmp  ;
                        TextView nameView = findViewById(R.id.name_patient_xemthuoc) ;
                        nameView.setText(doc.get("name").toString());

                        if(doc.containsKey("drugList")) {
                                tmp = ( ArrayList<Document> ) doc.get("drugList");

                                for(Document med : tmp) {
                                    String name , quantity , prescritionDate;
                                    name = ( med.containsKey("name") ? med.get("name").toString() : "" ) ;
                                    quantity = ( med.containsKey("quantity") ? med.get("quantity").toString() : "" ) ;
                                    prescritionDate = ( med.containsKey("prescritionDate") ? med.get("prescritionDate").toString() : "" ) ;
                                    if ( name!="" && quantity !="" && prescritionDate!="" )  items.add(new in4medicine(name, quantity , prescritionDate )) ;
                                }


                                if(items.get(0).getName().toString() !="" ){
                                    xemthuoc_adap adapter = new xemthuoc_adap( items ,getApplicationContext());
                                    listView.setAdapter(adapter);
                                }
                                else {
                                    TextView textView = findViewById(R.id.thong_bao_nothing_xemthuoc) ;
                                    textView.setText("Nothing Here!");
                                }

                        }
                        else {
                            TextView textView = findViewById(R.id.thong_bao_nothing_xemthuoc) ;
                            textView.setText("Nothing Here!");
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "FAIL", Toast.LENGTH_LONG).show();
                    }


                });


            }


        });


    }
    public void khoitao(){
        Realm.init(getApplicationContext());
        app = new App(new AppConfiguration.Builder(Appid).build());
        credentials = Credentials.emailPassword("khanglytronVN@KL.com", "123456");

        app.getEmailPassword().registerUserAsync("khanglytronVN@KL.com", "123456",it->{
            if(it.isSuccess()){
                Log.v("User", "Successfully registered user");
            }else{
                Log.v("User", "Failed to register user");
            }
        });
    }
}
