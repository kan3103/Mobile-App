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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;

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
    App app;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.nhapthuoc_layout);

        idthuoc = findViewById(R.id.id_thuoc_nhapthuoc);
        slthuoc = findViewById(R.id.soluong_nhapthuoc) ;
        button = findViewById(R.id.button_nhapthuoc) ;


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
                mongoCollection = mongoDatabase.getCollection("Drug");
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH); // 0-based
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                // Chuyển ngày, tháng, năm thành chuỗi
                String entryDate = formatDate(year, month + 1, day);

                // Tính toán ngày kết thúc sau 3 năm
                calendar.add(Calendar.YEAR, 3);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH); // 0-based
                day = calendar.get(Calendar.DAY_OF_MONTH);

                // Chuyển ngày, tháng, năm thành chuỗi
                String expirationDate = formatDate(year, month + 1, day);
                String id_string, sl_string ;
                id_string = idthuoc.getText().toString();
                sl_string = slthuoc.getText().toString();
                transToWareHouse(id_string, sl_string,entryDate,expirationDate);
            }
        });

    }

    public void transToWareHouse(String idthuoc, String soluong, String entryDate, String expirationDate){
        Log.v("updating","updating");
        Document filter = new Document().append("id", idthuoc);
        Document prescriptionObject = new Document(); // Tạo một đối tượng Document cho đơn thuốc
        prescriptionObject.put("quantity", soluong);
        prescriptionObject.put("entryDate", entryDate);
        prescriptionObject.put("expiredDate", expirationDate);
        Document update = new Document().append("$push", new Document().append("prescription", prescriptionObject));

        mongoCollection.updateOne(filter, update, new UpdateOptions().upsert(true)).getAsync(result1 -> {
            if(result1.isSuccess()) {
                long numModified = result1.get().getModifiedCount();
                if (numModified == 1) {
                    Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_LONG).show();
                    Log.v("Update", "Successfully updated document");
                } else {
                    Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_LONG).show();
                    Log.v("Update", "Inserted new document");
                }
            } else {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                Log.v("Update", "Failed to update document: " + result1.getError().toString());
            }
        });
    }
    // Hàm chuyển đổi ngày, tháng, năm thành chuỗi
    public static String formatDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day); // month là 0-based, nên trừ đi 1
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }




}
