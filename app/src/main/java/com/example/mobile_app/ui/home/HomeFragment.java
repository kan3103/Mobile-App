package com.example.mobile_app.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobile_app.R;
import com.example.mobile_app.databinding.FragmentHomeBinding;
public class HomeFragment extends Fragment {

    private View view;
//    private FragmentHomeBinding binding;
    private CardView dsach_khoa,overview;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_home, container, false);
        dsach_khoa = view.findViewById(R.id.home_dsach);
        overview = view.findViewById(R.id.home_overview);
        dsach_khoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HomeListActivity.class));
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view =null;
    }
}