package com.example.mobile_app.ui.capthuoc_frag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mobile_app.R;

import java.util.ArrayList;

public class capthuoc_adap extends ArrayAdapter<String> {
    private ArrayList<String> list_thuoc ;
    Context context ;

    public capthuoc_adap(@NonNull Context context, int resource, ArrayList<String> list_thuoc) {
        super(context, R.layout.capthuoc_layout, list_thuoc);
        this.list_thuoc = list_thuoc;
        this.context = context;
    }
    private static class viewHolder{
        TextView text ;
        EditText editText;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        viewHolder viewholder ;
        String item = getItem(position) ;

        if( convertView == null ){
            viewholder = new capthuoc_adap.viewHolder();
            LayoutInflater inflater =  LayoutInflater.from(getContext());
            convertView = inflater.inflate( R.layout.capthuoc_listview_custom, parent , false ) ;

            viewholder.text = (TextView) convertView.findViewById(R.id.drugName_capthuoc);
            convertView.setTag(viewholder);
        }else{
            viewholder = (capthuoc_adap.viewHolder) convertView.getTag();
        }
        viewholder.text.setText( item);
        return convertView ;
    }



}
