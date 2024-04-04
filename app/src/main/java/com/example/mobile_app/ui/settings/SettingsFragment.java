package com.example.mobile_app.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_app.R;
import com.example.mobile_app.databinding.FragmentSettingsBinding;

import java.util.ArrayList;


public class SettingsFragment extends Fragment {

    private String user;
    private FragmentSettingsBinding binding;
    private ArrayList<SettingsComp> array_com=new ArrayList<>();
    private Settings_Fragment adapter;
    private GridView gridView;
    public void setUser(String user){
        this.user="Patient";
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        user = "Doctor";
        if(user.equals("Doctor")) {
            // Doctor-specific settings components
            array_com.add(new SettingsComp(R.drawable.ex,"pic1"));
            array_com.add(new SettingsComp(R.drawable.ex,"pic2"));
        } else if (user == "Patient") {
            // Patient-specific settings components
            array_com.add(new SettingsComp(R.drawable.ex,"pic1"));
        }
        adapter = new Settings_Fragment(getActivity(),array_com);

        gridView = root.findViewById(R.id.setting_com);
        gridView.setNumColumns(2);

        gridView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
