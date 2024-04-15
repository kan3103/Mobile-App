package com.example.mobile_app.ui.capthuoc_frag;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_app.R;

import java.util.ArrayList;

public class capthuoc_acti extends AppCompatActivity {
        Button button;
        ArrayList<String> tenthuoc ;
        ListView listView ;
        EditText id_benhnhan;
    @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.capthuoc_layout);

            tenthuoc.add("Penicilin") ;
            tenthuoc.add("Paracetamol") ;
            tenthuoc.add("Coldacmin") ;
            tenthuoc.add("Panadol") ;
            tenthuoc.add("Vitamin B1") ;

            tenthuoc.add("Petol") ;
            tenthuoc.add("ROVAS") ;
            tenthuoc.add("Franvit") ;
            tenthuoc.add("Avamys") ;
            tenthuoc.add("Prospan") ;
            id_benhnhan = findViewById(R.id.idbenhnhan_capthuoc) ;
            capthuoc_adap adapter = new capthuoc_adap(this,R.layout.capthuoc_listview_custom, tenthuoc) ;
            listView = findViewById(R.id.list_view_capthuoc) ;
            listView.setAdapter(adapter);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
        public void getChild(){
            for (int i = 0; i < listView.getChildCount(); i++) {
                View rowView = listView.getChildAt(i);
                EditText editText = (EditText) rowView.findViewById(R.id.edittext_capthuoc);
                String text = editText.getText().toString();

            }
        }

}

