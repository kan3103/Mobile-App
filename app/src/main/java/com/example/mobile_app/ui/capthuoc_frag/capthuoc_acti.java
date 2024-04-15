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
        ListView listView;

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

            capthuoc_adap adapter = new capthuoc_adap(this,R.layout.capthuoc_listview_custom, tenthuoc) ;
            ListView
                    listView = findViewById(R.id.list_view_capthuoc) ;
            listView.setAdapter(adapter);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

        public void get_child(){
            for (int i = 0; i < listView.getChildCount(); i++) {
                View rowView = listView.getChildAt(i);
                EditText editText = (EditText) rowView.findViewById(R.id.sl_thuoc_capathuoc);
                String text = editText.getText().toString();
                // Xử lý giá trị text tại đây
            }
        }

}

