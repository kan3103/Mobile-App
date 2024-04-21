package com.example.mobile_app.ui.viewpatientlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_app.R;
import com.example.mobile_app.api.user.userObject.patientUser;

import java.util.ArrayList;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {

    private ArrayList<patientUser> patientList;
    Context context;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(patientUser item);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView patientName;
        TextView patientPhoneNumber;
        TextView patientAge;
//        Switch selectedPatient;

        public ViewHolder(View view) {
            super(view);
            patientName = (TextView) view.findViewById(R.id.doctoritem_textViewName);
            patientPhoneNumber = (TextView) view.findViewById(R.id.doctoritem_textViewPhoneNumber);
            patientAge = (TextView) view.findViewById(R.id.doctoritem_textViewAge);


//            selectedPatient = (Switch) view.findViewById(R.id.switch1);
        }

        public void bind(final patientUser patient, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(patient);
                    System.out.println(patient);
                }
            });
        }
    }

    public PatientAdapter(ArrayList<patientUser> patientList, Context context, OnItemClickListener listener) {
        this.patientList = patientList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item_patient, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final int index = viewHolder.getAdapterPosition();

        String name = patientList.get(position).getName();
        String phoneNumber = patientList.get(position).getPhoneNumber();
        String age = patientList.get(position).getSymptoms();

//        viewHolder.selectedPatient.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                System.out.println(isChecked);
//                switchBtnStatus = isChecked;
////                if (isChecked) {
////                } else {
////
////                }
//            }
//        });
        viewHolder.patientName.setText(name);
        viewHolder.patientPhoneNumber.setText(phoneNumber);
        viewHolder.patientAge.setText(age);

//        System.out.println(viewHolder.itemView);
        viewHolder.patientName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(patientList.get(position));
//                System.out.println(patientList.get(position));
            }
        });

        viewHolder.patientAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(patientList.get(position));

//                System.out.println(patientList.get(position));
            }
        });

        viewHolder.patientPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(patientList.get(position));
//                System.out.println(patientList.get(position));

            }
        });
//        viewHolder.bind(patientList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }
//    public boolean getSwitchBtnStatus(){
//        return switchBtnStatus;
//    }
}