package com.example.mobile_app.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_app.R;
import com.example.mobile_app.databinding.FragmentSettingsBinding;


public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        ArrayList<SettingsComp> array_com=new ArrayList<>();
//        array_com.add(new SettingsComp(R.drawable.ex,"pic1"));
//        array_com.add(new SettingsComp(R.drawable.ex,"pic2"));
//        array_com.add(new SettingsComp(R.drawable.ex,"pic3"));
//        array_com.add(new SettingsComp(R.drawable.ex,"pic1"));
//        array_com.add(new SettingsComp(R.drawable.ex,"pic2"));
//        array_com.add(new SettingsComp(R.drawable.ex,"pic3"));
//        array_com.add(new SettingsComp(R.drawable.ex,"pic1"));
//        array_com.add(new SettingsComp(R.drawable.ex,"pic2"));
//        array_com.add(new SettingsComp(R.drawable.ex,"pic3"));
//        array_com.add(new SettingsComp(R.drawable.ex,"pic1"));
//        array_com.add(new SettingsComp(R.drawable.ex,"pic2"));
//        array_com.add(new SettingsComp(R.drawable.ex,"pic3"));
//        array_com.add(new SettingsComp(R.drawable.ex,"pic3"));
//        array_com.add(new SettingsComp(R.drawable.ex,"pic3"));
//
//        Settings_Fragment adapter = new Settings_Fragment(getActivity(),array_com);
//
//        GridView gridView = root.findViewById(R.id.setting_com); // Thay thế id gridView bằng id của GridView trong layout của bạn
//        gridView.setAdapter(adapter);
        RecyclerView rclSe = root.findViewById(R.id.setting_com);

        Settings_Fragment setadap=new Settings_Fragment(getContext());


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
