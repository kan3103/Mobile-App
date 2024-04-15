package com.example.mobile_app.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_app.LoginActivity;
import com.example.mobile_app.MainActivity;
import com.example.mobile_app.R;
import com.example.mobile_app.api.user.userObject.adminUser;
import com.example.mobile_app.api.user.userObject.patientUser;
import com.example.mobile_app.api.user.userObject.userInterface;
import com.example.mobile_app.databinding.FragmentSettingsBinding;
import com.example.mobile_app.ui.add_user.AddUserActivity;
import com.example.mobile_app.ui.capthuoc_frag.capthuoc_acti;
import com.example.mobile_app.ui.dashboard.ViewDoctorsList;
import com.example.mobile_app.ui.media_record.RecordActivity;
import com.example.mobile_app.ui.profile_frag.profile_activity;

import java.util.ArrayList;


public class SettingsFragment extends Fragment {
    private userInterface user;
    private View mview;
    private ArrayList<SettingsComp> array_com_doc = new ArrayList<>();
    private ArrayList<SettingsComp> array_com = new ArrayList<>();
    private ArrayList<SettingsComp> array_com_admin = new ArrayList<>();
    private Settings_Fragment adapter;
    private GridView gridView;
    private TextView hello;
    private Button btnSend;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        binding = FragmentSettingsBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
        mview = inflater.inflate(R.layout.fragment_settings, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        user = mainActivity.getUser();
        array_com_doc.add(new SettingsComp(R.drawable.icon_person, "Thông tin cá nhân"));
        array_com_doc.add(new SettingsComp(R.drawable.list, "Danh sách bệnh nhân"));
        array_com.add(new SettingsComp(R.drawable.icon_person, "Thông tin cá nhân"));
        array_com.add(new SettingsComp(R.drawable.medical_record, "Hồ sơ bệnh án"));
        array_com_doc.add(new SettingsComp(R.drawable.drug_icon, "Cấp thuốc"));
        array_com_admin.add(new SettingsComp(R.drawable.icon_person, "Thông tin cá nhân"));
        array_com_admin.add(new SettingsComp(R.drawable.add_doctor, "Thêm bác sĩ"));
//        array_com_admin.add(new SettingsComp(R.drawable.drug_icon, "Hóa đơn thuốc"));
        if (user.getTypeuser().equals("Patient")) {
            adapter = new Settings_Fragment(getActivity(), array_com);
            gridView = mview.findViewById(R.id.setting_com);
            gridView.setNumColumns(2);
            gridView.setAdapter(adapter);
        } else if (user.getTypeuser().equals("Doctor")) {
            adapter = new Settings_Fragment(getActivity(), array_com_doc);
            gridView = mview.findViewById(R.id.setting_com);
            gridView.setNumColumns(2);
            gridView.setAdapter(adapter);
        } else {
            adapter = new Settings_Fragment(getActivity(), array_com_admin);
            gridView = mview.findViewById(R.id.setting_com);
            gridView.setNumColumns(2);
            gridView.setAdapter(adapter);
        }
        hello = mview.findViewById(R.id.textView);
        String hi = "Chào mừng, " + user.getUsername() + "!";
        hello.setText(hi);
        btnSend = mview.findViewById(R.id.button_logout);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Xử lý khi phần tử được nhấp
                if (position == 0) {
                    startActivity(new Intent(getActivity(), profile_activity.class));
                } else if (position == 1 && user.getTypeuser().equals("Patient")) {
                    Intent intent = new Intent(getActivity(), RecordActivity.class);
                    intent.putExtra("userobject", (patientUser) user);
                    startActivity(intent);
                }
                else if(position == 1 && user.getTypeuser().equals("Doctor")){
                    Intent intent = new Intent(view.getContext(), ViewDoctorsList.class);
                    startActivity(intent);
                }
                else if (position == 1 && user.getTypeuser().equals("Admin")) {
                    startActivity(new Intent(getActivity(), AddUserActivity.class));
                } else if (position == 2 && user.getTypeuser().equals("Doctor")) {
                    startActivity(new Intent(getActivity(), capthuoc_acti.class));
                }
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        return mview;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mview = null;
    }
}
