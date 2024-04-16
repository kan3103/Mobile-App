package com.example.mobile_app.ui.viewpatientlist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_app.R;
import com.example.mobile_app.api.user.userObject.doctorUser;
import com.example.mobile_app.api.user.userObject.patientUser;
import com.example.mobile_app.api.user.userObject.userInterface;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<userInterface> list;

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

    public CustomAdapter(ArrayList<userInterface> list, Context context) {
        this.list= list;
        Log.v("oke",list.get(0).getTypeuser());
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view=null;
        if(list.get(0).getTypeuser().equals("Patient")){

            if(((patientUser) list.get(0)).getSymptoms().equals("")){view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_item_patient, viewGroup, false);
                }
            else{
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.row_item_doctor, viewGroup, false);
            }

            return new ViewHolder(view);
        }
        else{
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_item_doctor2, viewGroup, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final int index=viewHolder.getAdapterPosition();
        if(list.get(0).getTypeuser().equals("Patient")) {
            if (((patientUser) list.get(0)).getSymptoms().equals("")) {
                String name = ((patientUser) list.get(position)).getName();
                String phoneNumber = ((patientUser) list.get(position)).getPhoneNumber();
                String age = ((patientUser) list.get(position)).getAge();

                viewHolder.patientName.setText(name);
                viewHolder.patientPhoneNumber.setText(phoneNumber);
                viewHolder.patientAge.setText(age);
            } else {
                String name = ((patientUser) list.get(position)).getName();
                String phoneNumber = ((patientUser) list.get(position)).getPhoneNumber();
                String symptoms = ((patientUser) list.get(position)).getSymptoms();

                viewHolder.patientName.setText(name);
                viewHolder.patientPhoneNumber.setText(phoneNumber);
                viewHolder.patientAge.setText(symptoms);
            }
        }
        else{
            String name = ((doctorUser) list.get(position)).getName();
            String phoneNumber = ((doctorUser) list.get(position)).getPhoneNum();
            String age = ((doctorUser) list.get(position)).getExperience();

            viewHolder.patientName.setText(name);
            viewHolder.patientPhoneNumber.setText(phoneNumber);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
