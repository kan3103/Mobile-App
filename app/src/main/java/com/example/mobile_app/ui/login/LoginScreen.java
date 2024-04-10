package com.example.mobile_app.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mobile_app.MainActivity;
import com.example.mobile_app.R;
import com.example.mobile_app.ui.dashboard.ViewDoctorsList;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LoginScreen extends AppCompatActivity {
    private com.example.mobile_app.databinding.ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("Success");

        binding = com.example.mobile_app.databinding.ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        View loginButton = findViewById(R.id.login_button);
//        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
//        view_doctors = (Button) view.findViewById(R.id.buttonViewDoctors);
//        System.out.println(view_doctors);

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginScreen.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
