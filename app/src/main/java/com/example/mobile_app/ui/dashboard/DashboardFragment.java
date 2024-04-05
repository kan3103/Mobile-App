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

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

//        binding = FragmentDashboardBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();

        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        view_doctors = (Button) view.findViewById(R.id.buttonViewDoctors);
        System.out.println(view_doctors);

        view_doctors.setOnClickListener(v -> {
            System.out.println("1");
//            progressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(view.getContext(), ViewDoctorsList.class);
            startActivity(intent);
//            progressBar.setVisibility(View.GONE);
            System.out.println("1");
        });

//        Intent intent = new Intent(DashboardFragment.this, loginPatient.class);
//        startActivity(intent);
//        System.out.println(binding.buttonViewDoctors);
//        final Button button = binding.buttonViewDoctors;
//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    Button my_profile, view_doctors, book_appointment, view_appointment_history, submit_appointment_form, load_fees, reset_form, sign_out;
    TextView welcome_text, textview_name, textview_email, textview_address, textview_contact, textview_usertype, textview_allergies;
    AutoCompleteTextView textview_doctorname, textview_fees;
    RelativeLayout layout_myprofile, layout_bookappointment;
    ProgressBar progressBar;
    EditText edittext_date, edittext_time, edittext_allergies;

    //    DatabaseReference reference;
//    FirebaseUser user;
    String uid,doctorId;

    //Doctor's List
    ArrayList<String> doctor_name = new ArrayList<>();
    ArrayList<Doctor> doctors = new ArrayList<>();


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        System.out.println(2311);
//        ((MainActivity) getActivity()).setContentView(R.layout.fragment_dashboard);
////        progressBar=findViewById(R.id.loginActivityIndeterminateProgressbar);
//
//        view_doctors=(Button) view.findViewById(R.id.buttonViewDoctors);
//        view_doctors.setOnClickListener(DashboardFragment.this);
//
//
//
//        progressBar.setVisibility(View.VISIBLE);
//        System.out.println(1);
//
//    }


//    public void onClick(View view) {
//        System.out.println("1");
//        progressBar.setVisibility(View.VISIBLE);
//        Intent intent = new Intent(DashboardFragment.this, ViewDoctorsList.class);
//        startActivity(intent);
//        progressBar.setVisibility(View.GONE);
//        System.out.println("1");
//    }


}