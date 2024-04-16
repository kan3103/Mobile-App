package com.example.mobile_app.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobile_app.Data.Doctor;
import com.example.mobile_app.MainActivity;
import com.example.mobile_app.R;
import com.example.mobile_app.api.user.userObject.patientUser;
import com.example.mobile_app.api.user.userObject.userInterface;
import com.example.mobile_app.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_app.Data.Doctor;
import com.example.mobile_app.R;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.options.UpdateOptions;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    MainActivity mainActivity;
    userInterface user;
    private View view;

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
                        EditText name,username,birthday,symtomps;

                        name = getView().findViewById(R.id.board_name);
                        username = getView().findViewById(R.id.board_id);
                        birthday = getView().findViewById(R.id.board_birthday);
                        symtomps = getView().findViewById(R.id.board_symptoms);
                        // Tạo một tài liệu mới với thông tin từ các trường dữ liệu trên giao diện người dùng
                        Document filter = new Document().append("username", username.getText().toString().trim());
                        Document newDoctor = new Document()
                                .append("username", username.getText().toString().trim())
                                .append("name", name.getText().toString().trim())
                                .append("birthday",birthday.getText().toString().trim())
                                .append("symtomps",symtomps.getText().toString().trim());
                        // Thêm tài liệu mới vào collection "Doctor"
                        MongoCollection mongoCollection = mainActivity.mongoCollection;
                        mongoCollection = mainActivity.mongoDatabase.getCollection("Register");
                        mongoCollection.updateOne(filter,newDoctor,new UpdateOptions().upsert(true)).getAsync(result -> {
                            if(result.isSuccess()){
                                Toast.makeText(getContext(),"Register succesful", Toast.LENGTH_LONG).show();
                                username.setText("");
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
            view = inflater.inflate(R.layout.activity_view_patient_list,container,false);
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