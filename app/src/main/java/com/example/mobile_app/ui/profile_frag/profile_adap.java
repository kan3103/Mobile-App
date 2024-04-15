package com.example.mobile_app.ui.profile_frag;

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

public class profile_adap extends ArrayAdapter<Item_list> {
    private ArrayList<Item_list> itemslist ;
    Context context ;

    public profile_adap(@NonNull Context context,ArrayList<Item_list> items_list) {
        super(context,R.layout.profile_listview_custom,items_list);
        this.itemslist = items_list;
        this.context = context;
    }

    private static class viewHolder{
        TextView text1 , text2 ;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        viewHolder viewholder ;
        Item_list point  = getItem(position) ;

        if( convertView == null ){
            viewholder = new viewHolder();
            LayoutInflater inflater =  LayoutInflater.from(getContext());
            convertView = inflater.inflate( R.layout.profile_listview_custom, parent , false ) ;

            viewholder.text1 = (TextView) convertView.findViewById(R.id.list_item_text1);
            viewholder.text2 = (TextView) convertView.findViewById(R.id.list_item_text2);

            convertView.setTag(viewholder);
        }else{
            viewholder = (viewHolder) convertView.getTag();
        }
        viewholder.text1.setText(point.getName_()  );
        viewholder.text2.setText(point.getDescrip() );

        return convertView ;
    }
}
