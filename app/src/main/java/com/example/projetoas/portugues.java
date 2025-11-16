package com.example.projetoas;


import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class portugues extends AppCompatActivity {


    private TextView txtLeitura, txtGramatica, txtEscrita;
    private Button btnLeitura, btnGramatica, btnEscrita;
    private ImageButton btnVoltar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portugues);


        txtLeitura = findViewById(R.id.txtLeitura);
        txtGramatica = findViewById(R.id.txtGramatica);
        txtEscrita = findViewById(R.id.txtEscrita);


        btnLeitura = findViewById(R.id.btnLeitura);
        btnGramatica = findViewById(R.id.btnGramática);
        btnEscrita = findViewById(R.id.btnEscrita);


        btnVoltar = findViewById(R.id.btn_voltar_portugues);

        processarConteudoHtml(txtLeitura);
        processarConteudoHtml(txtGramatica);
        processarConteudoHtml(txtEscrita);


        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(portugues.this, Estudos.class);
            startActivity(intent);
            finish();
        });


        btnLeitura.setOnClickListener(v -> {
            toggleSection(txtLeitura);
            txtGramatica.setVisibility(View.GONE);
            txtEscrita.setVisibility(View.GONE);
        });


        btnGramatica.setOnClickListener(v -> {
            toggleSection(txtGramatica);
            txtLeitura.setVisibility(View.GONE);
            txtEscrita.setVisibility(View.GONE);
        });


        btnEscrita.setOnClickListener(v -> {
            toggleSection(txtEscrita);
            txtLeitura.setVisibility(View.GONE);
            txtGramatica.setVisibility(View.GONE);
        });
    }


    private void toggleSection(TextView section) {
        if (section.getVisibility() == View.VISIBLE) {
            section.setVisibility(View.GONE);
        } else {
            section.setVisibility(View.VISIBLE);
        }
    }

    private void processarConteudoHtml(TextView textView) {
        String rawText = textView.getText().toString();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(rawText, Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml(rawText));
        }
    }
}