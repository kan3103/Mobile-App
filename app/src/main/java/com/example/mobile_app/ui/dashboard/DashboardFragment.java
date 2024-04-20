package com.example.mobile_app.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;



import com.example.mobile_app.MainActivity;
import com.example.mobile_app.R;
import com.example.mobile_app.api.user.userObject.patientUser;
import com.example.mobile_app.api.user.userObject.userInterface;
import com.example.mobile_app.databinding.FragmentDashboardBinding;

import java.io.Serializable;
import java.util.ArrayList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_app.ui.viewpatientlist.ApplyToHospital;
import com.example.mobile_app.ui.viewpatientlist.CustomAdapter;
import com.example.mobile_app.ui.viewpatientlist.PatientAdapter;
import com.example.mobile_app.ui.viewpatientlist.RegisterAdapter;
import com.example.mobile_app.ui.viewpatientlist.ViewDoctorsList;


import org.bson.Document;

import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.iterable.MongoCursor;
import io.realm.mongodb.mongo.options.UpdateOptions;


public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    MainActivity mainActivity;
    userInterface user;
    private View view;
    CustomAdapter adapter;
    RegisterAdapter adapter1;
    RecyclerView recyclerView;
    ArrayList<patientUser> patientList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mainActivity= (MainActivity) getActivity();
        user = mainActivity.getUser();
        if(user instanceof patientUser){
            view = inflater.inflate(R.layout.fragment_dashboard, container, false);
            Button btn2 = view.findViewById(R.id.board_xacnhan);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(user instanceof patientUser){
                        EditText name,birthday,symtomps;
                        name = getView().findViewById(R.id.board_name);
                        birthday = getView().findViewById(R.id.board_birthday);
                        symtomps = getView().findViewById(R.id.board_symptoms);
                        // Tạo một tài liệu mới với thông tin từ các trường dữ liệu trên giao diện người dùng
                        Document filter = new Document().append("username", mainActivity.getUser().getUsername());
                        Document newDoctor = new Document()
                                .append("username", mainActivity.getUser().getUsername())
                                .append("name", name.getText().toString().trim())
                                .append("phoneNumber",birthday.getText().toString().trim())
                                .append("symptoms",symtomps.getText().toString().trim());
                        // Thêm tài liệu mới vào collection "Doctor"
                        MongoCollection mongoCollection = mainActivity.mongoCollection;
                        mongoCollection = mainActivity.mongoDatabase.getCollection("Register");
                        mongoCollection.updateOne(filter,newDoctor,new UpdateOptions().upsert(true)).getAsync(result -> {
                            if(result.isSuccess()){
                                Toast.makeText(getContext(),"Register succesful", Toast.LENGTH_LONG).show();
                                name.setText("");
                                birthday.setText("");
                                symtomps.setText("");
                            }
                            else {
                                Toast.makeText(getContext(),"Fail to register", Toast.LENGTH_LONG).show();
                            }
                        });}
                }
            });
        }
        else{
            view = inflater.inflate(R.layout.activity_view_doctors_list,container,false);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            MongoCollection mongoCollection = mainActivity.mongoCollection;
            mongoCollection = mainActivity.mongoDatabase.getCollection("Register");
            mongoCollection.find().iterator().getAsync(task -> {
                if (task.isSuccess()) {
                    MongoCursor<Document> results = (MongoCursor<Document>) task.get();
                    while (results.hasNext()) {
                        Document currentDocument = results.next();
                        String name = currentDocument.getString("name");
                        String phoneNum = currentDocument.getString("phoneNumber");
                        String symptoms = currentDocument.getString("symptoms");
                        patientList.add((patientUser) new patientUser(name, symptoms, phoneNum));

                    }
                    if (patientList != null)
                    {
                        adapter1 = new RegisterAdapter(patientList, getContext(), new RegisterAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(patientUser patient) {
                                Intent intent = new Intent(getContext(), ApplyToHospital.class);
                                intent.putExtra("patientInformation", (Serializable) patient);
                                startActivity(intent);
                            }
                        });
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter1);
                    System.out.println(recyclerView);

                } else {
                    Log.e("APP", "Failed to find documents with: ", task.getError());
                }
            });
        }
        System.out.println(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}