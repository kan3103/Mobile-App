package com.example.mobile_app.ui.nhapthuoc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_app.R;

public class nhapthuoc_acti extends AppCompatActivity {
    Button button ;
    EditText idthuoc ;
    EditText slthuoc;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.nhapthuoc_layout);

        idthuoc = findViewById(R.id.id_thuoc_nhapthuoc);
        slthuoc = findViewById(R.id.soluong_nhapthuoc) ;
        button = findViewById(R.id.button_nhapthuoc) ;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_string, sl_string ;
                id_string = idthuoc.getText().toString();
                sl_string = slthuoc.getText().toString();
                int id = Integer.parseInt(id_string);
                int sl = Integer.parseInt(sl_string);
                transToWareHouse(id, sl);
            }
        });

    }

    public void transToWareHouse(int idthuoc, int soluong){

    }


}
