package com.example.mobile_app.ui.viewpatientlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_app.R;
import com.example.mobile_app.api.user.userObject.patientUser;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<patientUser> list;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView patientName;
        TextView patientPhoneNumber;
        TextView patientAge;


        public ViewHolder(View view) {
            super(view);
            patientName = (TextView) view.findViewById(R.id.doctoritem_textViewName);
            patientPhoneNumber = (TextView) view.findViewById(R.id.doctoritem_textViewPhoneNumber);
            patientAge = (TextView) view.findViewById(R.id.doctoritem_textViewAge);
        }
    }

    public CustomAdapter(ArrayList<patientUser> list, Context context) {
        this.list=list;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;
        if(list.get(0).getSymptoms().equals("")){view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item_patient, viewGroup, false);}
        else{
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_item_doctor, viewGroup, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final int index=viewHolder.getAdapterPosition();

        if(list.get(0).getSymptoms().equals("")){
            String name = list.get(position).getName();
            String phoneNumber = list.get(position).getPhoneNumber();
            String age = list.get(position).getAge();

            viewHolder.patientName.setText(name);
            viewHolder.patientPhoneNumber.setText(phoneNumber);
            viewHolder.patientAge.setText(age);
        }else {
            String name = list.get(position).getName();
            String phoneNumber = list.get(position).getPhoneNumber();
            String symptoms = list.get(position).getSymptoms();

            viewHolder.patientName.setText(name);
            viewHolder.patientPhoneNumber.setText(phoneNumber);
            viewHolder.patientAge.setText(symptoms);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
