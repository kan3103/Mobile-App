package com.example.mobile_app.ui.thuoc_patient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mobile_app.R;

import java.util.ArrayList;

public class xemthuoc_adap extends ArrayAdapter<in4medicine> {
    Context context ;
    ArrayList<in4medicine> items ;
    public xemthuoc_adap(ArrayList<in4medicine> items_list,@NonNull Context context) {
        super(context , R.layout.xemthuoc_customitem , items_list) ;
        this.items = items_list;
        this.context = context;
    }

    private static class viewHolder{
        TextView text1 , text2 , text3 ;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        viewHolder viewholder ;
        in4medicine point  = getItem(position) ;

        if( convertView == null ){
            viewholder = new viewHolder();
            LayoutInflater inflater =  LayoutInflater.from(getContext());
            convertView = inflater.inflate( R.layout.xemthuoc_customitem, parent , false ) ;

            viewholder.text1 = (TextView) convertView.findViewById(R.id.textViewxem1);
            viewholder.text2 = (TextView) convertView.findViewById(R.id.textViewxem2);
            viewholder.text3 = (TextView) convertView.findViewById(R.id.textViewxem3);

            convertView.setTag(viewholder);
        }else{
            viewholder = (viewHolder) convertView.getTag();
        }
        viewholder.text1.setText( "Tên thuốc: " + point.getName()   );
        viewholder.text2.setText( "Số lượng: " + point.getSoluong() );
        viewholder.text3.setText( "Ngày cấp: "+point.getNgay_cap() );

        return convertView ;
    }

}
