package com.example.mobile_app.ui.media_record;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mobile_app.R;

import java.util.List;

public class Record_Adapter extends BaseAdapter {
    private List<Record_Data> mDataList;
    private Context mContext;

    public Record_Adapter(Context context, List<Record_Data> dataList) {
        this.mDataList = dataList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.card_view_record, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.date_in = convertView.findViewById(R.id.record_datein);
            viewHolder.date_out= convertView.findViewById(R.id.record_dateout);
            viewHolder.Doctor = convertView.findViewById(R.id.record_doctor);
            viewHolder.Nurse = convertView.findViewById(R.id.record_nurse);
            viewHolder.Dia = convertView.findViewById(R.id.record_dia);
            viewHolder.Blood = convertView.findViewById(R.id.record_blood);
            viewHolder.Height = convertView.findViewById(R.id.record_height);
            viewHolder.Weight = convertView.findViewById(R.id.record_weight);
            viewHolder.Re_ex = convertView.findViewById(R.id.record_re_ex);
            viewHolder.Test_result = convertView.findViewById(R.id.record_test_result);
            viewHolder.Note = convertView.findViewById(R.id.record_note);
            viewHolder.Relatives = convertView.findViewById(R.id.record_relatives);
            viewHolder.Department = convertView.findViewById(R.id.record_department);
            viewHolder.Health_record = convertView.findViewById(R.id.record_health_record);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Record_Data recordData = mDataList.get(position);
        viewHolder.date_in.setText(recordData.getDate_in());
        viewHolder.date_out.setText(recordData.getDate_out());
        viewHolder.Doctor.setText(recordData.getDoctor());
        viewHolder.Nurse.setText(recordData.getNurse());
        viewHolder.Dia.setText(recordData.getDia());
        viewHolder.Blood.setText(recordData.getBlood());
        viewHolder.Height.setText(recordData.getHeight());
        viewHolder.Re_ex.setText(recordData.getRe_ex());
        viewHolder.Test_result.setText(recordData.getTest_result());
        viewHolder.Note.setText(recordData.getNote());
        viewHolder.Relatives.setText(recordData.getRelatives());
        viewHolder.Department.setText(recordData.getDepartment());
        viewHolder.Health_record.setText(recordData.getHealth_record());
        // Set other TextViews with corresponding data
        return convertView;
    }

    static class ViewHolder {
        TextView date_in;
        TextView date_out;
        TextView Doctor;
        TextView Nurse;
        TextView Dia;
        TextView Blood;
        TextView Height;
        TextView Weight;
        TextView Re_ex;
        TextView Test_result;
        TextView Note;
        TextView Relatives;
        TextView Department;
        TextView Health_record;
    }
}
