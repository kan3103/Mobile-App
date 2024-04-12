package com.example.mobile_app.ui.add_user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mobile_app.LoginActivity;
import com.example.mobile_app.MainActivity;
import com.example.mobile_app.R;


public class AddUserActivity extends AppCompatActivity {
    private Button btn1,btn2;
    MainActivity mainActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        btn1 = findViewById(R.id.button_huy);
        btn2 = findViewById(R.id.button_xacnnhan);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }
}
