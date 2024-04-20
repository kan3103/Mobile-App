package com.example.mobile_app.ui.viewpatientlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_app.R;
import com.example.mobile_app.api.user.userObject.adminUser;

import org.bson.Document;

import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {

    private ArrayList<Document> adminList;
    Context context;

    public interface OnItemClickListener {
        void onItemClick(adminUser item);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView adminName ;
        TextView adminMSSV ;
//        Switch selectedPatient;

        public ViewHolder(View view) {
            super(view);
            adminName = (TextView) view.findViewById(R.id.doctoritem_textViewName);
            System.out.println(adminName);
            adminMSSV = (TextView) view.findViewById(R.id.doctoritem_textViewStudentID);
            System.out.println(adminMSSV);

//            selectedPatient = (Switch) view.findViewById(R.id.switch1);
        }

        public void bind(final adminUser admin, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(admin);
                    System.out.println(admin);
                }
            });
        }
    }

    public AdminAdapter(ArrayList<Document> adminList, Context context) {
        this.adminList = adminList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_admin_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final int index = viewHolder.getAdapterPosition();

        String name = adminList.get(position).getString("username");
        String phoneNumber = adminList.get(position).getString("password");

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
        viewHolder.adminName.setText(name);
        viewHolder.adminMSSV.setText(phoneNumber);

//        System.out.println(viewHolder.itemView);
        viewHolder.adminName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                System.out.println(patientList.get(position));
            }
        });

        viewHolder.adminMSSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                System.out.println(patientList.get(position));
            }
        });


//        viewHolder.bind(patientList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return adminList.size();
    }
//    public boolean getSwitchBtnStatus(){
//        return switchBtnStatus;
//    }
}