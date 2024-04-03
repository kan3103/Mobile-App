package com.example.mobile_app.ui.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mobile_app.R;


import java.util.ArrayList;

public class Settings_Fragment extends ArrayAdapter<SettingsComp> {

    private ArrayList<SettingsComp> setcom;
    Context context;
    public Settings_Fragment(@NonNull Context context, ArrayList<SettingsComp> setcom) {
        super(context, R.layout.settings_component,setcom);
        this.setcom = setcom;
        this.context = context;
    }

    private static class ViewHolder{
        TextView name ;
        ImageView img ;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewholder;

        SettingsComp comp = getItem(position) ;

        if( convertView == null ){
            viewholder = new ViewHolder();
            LayoutInflater inflater =  LayoutInflater.from(getContext());
            convertView = inflater.inflate( R.layout.settings_component , parent , false ) ;

            viewholder.name = (TextView) convertView.findViewById(R.id.text_set);
            viewholder.img = (ImageView) convertView.findViewById(R.id.Img_set);

            convertView.setTag(viewholder);
        }else{
            viewholder = (ViewHolder) convertView.getTag();
        }
        viewholder.name.setText(comp.getDes());
        viewholder.img.setImageResource(comp.getImg());

        return convertView ;
    }
}
