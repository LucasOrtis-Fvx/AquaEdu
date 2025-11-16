package com.example.projetoas;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageButton;


public class SobreActivity extends AppCompatActivity {


    private Button buttonMotivos, buttonOds;
    private TextView textMotivos;
    private LinearLayout odsContentLayout;
    private ImageButton btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        buttonMotivos = findViewById(R.id.buttonMotivos);
        textMotivos = findViewById(R.id.textMotivos);
        buttonOds = findViewById(R.id.buttonOds);
        odsContentLayout = findViewById(R.id.odsContentLayout);

        btnBack = findViewById(R.id.btn_back_sobre);

        buttonMotivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(textMotivos);
            }
        });

        buttonOds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(odsContentLayout);
            }
        });

        btnBack.setOnClickListener(v -> {
            finish();
        });
    }


    private void toggleVisibility(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
}