package com.example.mobile_app.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_app.Data.Doctor;

import com.example.mobile_app.R;
import com.example.mobile_app.api.Patient.PatientObj.Patient;
import com.example.mobile_app.api.Patient.PatientObj.PatientInter;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<Patient> list;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView patientName;
        TextView patientSex;
        TextView patientAge;


        public ViewHolder(View view) {
            super(view);
            patientName = (TextView) view.findViewById(R.id.doctoritem_textViewName);
            patientSex = (TextView) view.findViewById(R.id.doctoritem_textViewSex);
            patientAge = (TextView) view.findViewById(R.id.doctoritem_textViewAge);
        }
    }

    public CustomAdapter(ArrayList<Patient> list,Context context) {
        this.list=list;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item_doctor, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final int index=viewHolder.getAdapterPosition();

        String name = list.get(position).getName();
        String sex = list.get(position).getSex();
        String age = list.get(position).getAge();

        viewHolder.patientName.setText(name);
        viewHolder.patientSex.setText(sex);
        viewHolder.patientAge.setText(age);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
