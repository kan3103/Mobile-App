package com.example.mobile_app.ui.media_record;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.mobile_app.R;
import com.example.mobile_app.api.MedicalRecord.MedRecord;

import java.util.ArrayList;
import java.util.List;

//public class Record_Adapter extends CardView {
//    private List<MedRecord.Record> mDataList;
//    private Context mContext;
//
//    public Record_Adapter(Context context, List<MedRecord.Record> dataList) {
//        this.mDataList = dataList;
//        mContext = context;
//    }
//
//    @Override
//    public int getCount() {
//        return mDataList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return mDataList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder;
//        if (convertView == null) {
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.card_view_record, parent, false);
//            viewHolder = new ViewHolder();
//            viewHolder.date_in = convertView.findViewById(R.id.record_datein);
//            viewHolder.date_out= convertView.findViewById(R.id.record_dateout);
//            viewHolder.Doctor = convertView.findViewById(R.id.record_doctor);
//            viewHolder.Nurse = convertView.findViewById(R.id.record_nurse);
//            viewHolder.Dia = convertView.findViewById(R.id.record_dia);
//            viewHolder.Blood = convertView.findViewById(R.id.record_blood);
//            viewHolder.Height = convertView.findViewById(R.id.record_height);
//            viewHolder.Weight = convertView.findViewById(R.id.record_weight);
//            viewHolder.Re_ex = convertView.findViewById(R.id.record_re_ex);
//            viewHolder.Test_result = convertView.findViewById(R.id.record_test_result);
//            viewHolder.Note = convertView.findViewById(R.id.record_note);
//            viewHolder.Relatives = convertView.findViewById(R.id.record_relatives);
//            viewHolder.Specialty = convertView.findViewById(R.id.record_department);
//            viewHolder.Health_record = convertView.findViewById(R.id.record_health_record);
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//
//        MedRecord.Record recordData = mDataList.get(position);
//        viewHolder.date_in.setText(recordData.getDate());
////        viewHolder.date_out.setText(recordData.getDate_out());
//        viewHolder.Doctor.setText(recordData.getDoctor());
//        viewHolder.Nurse.setText(recordData.getNurse());
//        viewHolder.Dia.setText(recordData.getDiagnosis());
////        viewHolder.Blood.setText(recordData.getBloodType());
//        viewHolder.Height.setText(recordData.getHeight());
////        viewHolder.Re_ex.setText(recordData.getRe_ex());
//        viewHolder.Test_result.setText(recordData.getTestResults());
//        viewHolder.Note.setText(recordData.getNotes());
////        viewHolder.Relatives.setText(recordData.getRelatives());
//        viewHolder.Specialty.setText(recordData.getSpecialty());
////        viewHolder.Health_record.setText(recordData.getHealth_record());
//        // Set other TextViews with corresponding data
//        return convertView;
//    }
//
//    static class ViewHolder {
//        TextView date_in;
//        TextView date_out;
//        TextView Doctor;
//        TextView Nurse;
//        TextView Dia;
//        TextView Blood;
//        TextView Height;
//        TextView Weight;
//        TextView Re_ex;
//        TextView Test_result;
//        TextView Note;
//        TextView Relatives;
//        TextView Specialty;
//        TextView Health_record;
//    }
//}
public class CustomCardListView extends CardView {
    private MedRecord.Record mDataList;
    private TextView dateInTextView;
    private TextView dateOutTextView;
    private TextView doctorTextView;
    private TextView nurseTextView;
    private TextView diaTextView;
    private TextView bloodTextView;
    private TextView heightTextView;
    private TextView weightTextView;
    private TextView reExTextView;
    private TextView testResultTextView;
    private TextView noteTextView;
    private TextView relativesTextView;
    private TextView specialtyTextView;
    private TextView healthRecordTextView;
    public CustomCardListView(Context context) {
        super(context);
        init(context);
    }

    public CustomCardListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomCardListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.card_view_record, this);
        dateInTextView = findViewById(R.id.record_datein);
        dateOutTextView = findViewById(R.id.record_dateout);
        doctorTextView = findViewById(R.id.record_doctor);
        nurseTextView = findViewById(R.id.record_nurse);
        diaTextView = findViewById(R.id.record_dia);
        bloodTextView = findViewById(R.id.record_blood);
        heightTextView = findViewById(R.id.record_height);
        weightTextView = findViewById(R.id.record_weight);
        reExTextView = findViewById(R.id.record_re_ex);
        testResultTextView = findViewById(R.id.record_test_result);
        noteTextView = findViewById(R.id.record_note);
        relativesTextView = findViewById(R.id.record_relatives);
        specialtyTextView = findViewById(R.id.record_department);
        healthRecordTextView = findViewById(R.id.record_health_record);
        mDataList = null;
    }

    public void setDataList(MedRecord.Record dataList) {
        if (dataList != null) {
            mDataList =  dataList;
        };
        dateInTextView.setText("Date in: "+mDataList.getDate());
        doctorTextView.setText("Doctor: "+mDataList.getDoctor());
//        nurseTextView.setText(mDataList.getNurse());
//        diaTextView.setText(mDataList.getDiagnosis());
//        bloodTextView.setText(mDataList.getBloodPressure());
        heightTextView.setText("Height: "+ mDataList.getHeight());
        weightTextView.setText("Weight: "+ mDataList.getWeight());
//        reExTextView.setText(mDataList.getRevisionDate());
//        testResultTextView.setText(mDataList.getTestResults());
//        noteTextView.setText(mDataList.getNotes());
//        specialtyTextView.setText(mDataList.getSpecialty());
        // Update the UI here, e.g., call a method to refresh the list
    }

    // Add any other methods you need to interact with the list

}