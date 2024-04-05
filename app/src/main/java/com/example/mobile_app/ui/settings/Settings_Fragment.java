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
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_app.R;


import java.util.ArrayList;
import java.util.List;

public class Settings_Fragment extends ArrayAdapter<SettingsComp> {

    private ArrayList<SettingsComp> setcom;
    Context context;

    public void setSetcom(ArrayList<SettingsComp> setcom) {
        this.setcom = setcom;
    }

    public void setContext(Context context) {
        this.context = context;
    }

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
//
//public class Settings_Fragment extends RecyclerView.Adapter<Settings_Fragment.Setting_viewholder> {
//    private Context context;
//    private List<SettingsComp> mlistset;
//
//    public Settings_Fragment(Context context) {
//        this.context = context;
//    }
//    public void setdata(List<SettingsComp> list){
//        this.mlistset = list;
//        notifyDataSetChanged();
//    }
//    @NonNull
//    @Override
//    public Setting_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_component,parent,false);
//        return new Setting_viewholder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull Setting_viewholder holder, int position) {
//        SettingsComp settingsComp=mlistset.get(position);
//        if(settingsComp==null)
//            return;
//        holder.imgset.setImageResource(settingsComp.getImg());
//        holder.textset.setText(settingsComp.getDes());
//    }
//
//    @Override
//    public int getItemCount() {
//        if(mlistset != null)
//            return mlistset.size();
//        return 0;
//    }
//
//    public class Setting_viewholder extends RecyclerView.ViewHolder{
//        private ImageView imgset;
//        private TextView textset;
//        public Setting_viewholder(@NonNull View itemView) {
//            super(itemView);
//            imgset = itemView.findViewById(R.id.Img_set);
//            textset = itemView.findViewById(R.id.text_set);
//        }
//    }
//}
