package com.example.mobile_app.ui.capthuoc_frag;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_app.R;

import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.options.UpdateOptions;

public class capthuoc_acti extends AppCompatActivity {
    Button button;
    ArrayList<String> tenthuoc ;
    ListView listView;
    EditText editText ;
    String Appid = "mobileapp-fyjbw";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    MongoCollection<Document> mongoCollection;
    User user;
    MongoDatabase mongoDatabase1;
    MongoClient mongoClient1;
    MongoCollection<Document> mongoCollection1;

    boolean found = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.capthuoc_layout);



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
                mongoCollection = mongoDatabase.getCollection("Patient");

            }
        });

        button = findViewById(R.id.button_capthuoc);
        tenthuoc = new ArrayList<>(Arrays.asList("Pencilin" , "Paracetamol" , "Prospan" , "Vitamin B1" , "Petol" , "Gastro" , "Gaviscon" , "V.ROHTO" , "Seduxen" , "Nautamine") );

        capthuoc_adap adapter = new capthuoc_adap(this,R.layout.capthuoc_listview_custom, tenthuoc) ;
        listView = findViewById(R.id.list_view_capthuoc) ;
        listView.setAdapter(adapter);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText= findViewById(R.id.idpatient_capthuoc) ;
                String id = editText.getText().toString() ;
                get_child(id);
            }
        });

    }

    public void get_child(String id) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH); // 0-based
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        // Chuyển ngày, tháng, năm thành chuỗi
        String prescriptionDate = formatDate(year, month + 1, day);
        //EditText editText = (EditText) listView.getChildAt(1).findViewById(R.id.sl_thuoc_capathuoc);
        Log.d("ChildCount", "Number of children in listView: " + listView.getChildCount());

        for (int i = 0; i <listView.getChildCount() ; i++) {
            EditText editText2 = (EditText) listView.getChildAt(i).findViewById(R.id.sl_thuoc_capathuoc);
            String text = editText2.getText().toString();
            // Xử lý giá trị text tại đây
            add_thuoc(id, tenthuoc.get(i), text, prescriptionDate);
        }

    }

    public void add_thuoc( String id,String name, String sl, String prescriptionDate  ){
        if(find_thuoc(name,sl)) {
            Log.v("updating", "updating");
            Document filter = new Document().append("id", id);
            Document drugListObject = new Document(); // Tạo một đối tượng Document cho đơn thuốc
            drugListObject.put("name", name);
            drugListObject.put("quantity", sl);
            drugListObject.put("prescritionDate", prescriptionDate);
            Document update = new Document().append("$push", new Document().append("drugList", drugListObject));

            mongoCollection.updateOne(filter, update, new UpdateOptions().upsert(true)).getAsync(result1 -> {
                if (result1.isSuccess()) {
                    long numModified = result1.get().getModifiedCount();
                    if (numModified == 1) {
                        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                        Log.v("Update", "Successfully updated document");
                    } else {
                        Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_LONG).show();
                        Log.v("Update", "Inserted new document");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    Log.v("Update", "Failed to update document: " + result1.getError().toString());
                }
            });
        }
    }

    public static String formatDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day); // month là 0-based, nên trừ đi 1
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }

    public boolean find_thuoc(String name, String soluong) {
        int sl = Integer.parseInt(soluong);
        mongoClient1 = user.getMongoClient("mongodb-atlas");
        mongoDatabase1 = mongoClient1.getDatabase("Hospital");
        mongoCollection1 = mongoDatabase1.getCollection("Drug");

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
                mongoClient1 = user.getMongoClient("mongodb-atlas");
                mongoDatabase1 = mongoClient1.getDatabase("Hospital");
                mongoCollection1 = mongoDatabase1.getCollection("Drug");
            }
        });
        Document queryFilter = new Document("name", name);
        mongoCollection1.findOne(queryFilter).getAsync(result -> {
            if (result.isSuccess()) {
                Document foundDocument = result.get();
                if (foundDocument != null) {
                    ArrayList<Document> prescriptionArray = foundDocument.get("prescription", ArrayList.class);
                    for (int i = 0; i < prescriptionArray.size(); i++) {
                        if (prescriptionArray != null && !prescriptionArray.isEmpty()) {
                            if (sl != 0) {
                                Document firstPrescription = prescriptionArray.get(i); // Lấy ra object đầu tiên trong mảng prescription
                                String currentQuantity = firstPrescription.getString("quantity");
                                int quantity = Integer.parseInt(currentQuantity);// Lấy ra số lượng hiện tại từ object đó
                                int updatedQuantity = quantity - sl;

                                if (updatedQuantity <= 0) {
                                    // Xóa tài liệu nếu số lượng sau khi giảm bằng hoặc nhỏ hơn 0
                                    mongoCollection1.deleteOne(queryFilter).getAsync(deleteResult -> {
                                        if (deleteResult.isSuccess()) {
                                            Toast.makeText(getApplicationContext(), "Document deleted", Toast.LENGTH_LONG).show();
                                            Log.v("Delete", "Successfully deleted document");
                                            found = true;
                                            return;
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Failed to delete document", Toast.LENGTH_LONG).show();
                                            Log.v("Delete", "Failed to delete document: " + deleteResult.getError().toString());
                                        }
                                    });
                                } else {
                                    // Cập nhật số lượng mới vào object trong mảng prescription
                                    firstPrescription.put("quantity", String.valueOf(updatedQuantity));
                                    Document update = new Document("$set", new Document("prescription", prescriptionArray));
                                    mongoCollection1.updateOne(queryFilter, update).getAsync(updateResult -> {
                                        if (updateResult.isSuccess()) {
                                            Toast.makeText(getApplicationContext(), "Document updated", Toast.LENGTH_LONG).show();
                                            Log.v("Update", "Successfully updated document");
                                            found = true;
                                            return;
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Failed to update document", Toast.LENGTH_LONG).show();
                                            Log.v("Update", "Failed to update document: " + updateResult.getError().toString());
                                        }
                                    });
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Prescription array is empty", Toast.LENGTH_LONG).show();
                            Log.v("Prescription", "Prescription array is empty");
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Document not found", Toast.LENGTH_LONG).show();
                    Log.v("Find", "Document not found");
                }
            } else {
                Toast.makeText(getApplicationContext(), "Error finding document", Toast.LENGTH_LONG).show();
                Log.v("Find", "Error finding document: " + result.getError().toString());
            }
        });
        return found;
    }

}