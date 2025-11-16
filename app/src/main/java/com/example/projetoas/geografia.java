package com.example.projetoas;


import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class geografia extends AppCompatActivity {


    private ImageButton btnVoltar;


    private TextView txtOQueEhGeografia, txtContinentes, txtRelevo, txtClima, txtRios, txtUrbanoRural, txtRegioes, txtSustentabilidade;


    private Button btnOQueEhGeografia, btnContinentes, btnRelevo, btnClima, btnRios, btnUrbanoRural, btnRegioes, btnSustentabilidade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geografia);


        txtOQueEhGeografia = findViewById(R.id.txtOQueEhGeografia);
        txtContinentes = findViewById(R.id.txtContinentes);
        txtRelevo = findViewById(R.id.txtRelevo);
        txtClima = findViewById(R.id.txtClima);
        txtRios = findViewById(R.id.txtRios);
        txtUrbanoRural = findViewById(R.id.txtUrbanoRural);
        txtRegioes = findViewById(R.id.txtRegioes);
        txtSustentabilidade = findViewById(R.id.txtSustentabilidade);


        processarConteudoHtml(txtOQueEhGeografia);
        processarConteudoHtml(txtContinentes);
        processarConteudoHtml(txtRelevo);
        processarConteudoHtml(txtClima);
        processarConteudoHtml(txtRios);
        processarConteudoHtml(txtUrbanoRural);
        processarConteudoHtml(txtRegioes);
        processarConteudoHtml(txtSustentabilidade);


        btnOQueEhGeografia = findViewById(R.id.btnOQueEhGeografia);
        btnContinentes = findViewById(R.id.btnContinentes);
        btnRelevo = findViewById(R.id.btnRelevo);
        btnClima = findViewById(R.id.btnClima);
        btnRios = findViewById(R.id.btnRios);
        btnUrbanoRural = findViewById(R.id.btnUrbanoRural);
        btnRegioes = findViewById(R.id.btnRegioes);
        btnSustentabilidade = findViewById(R.id.btnSustentabilidade);


        btnVoltar = findViewById(R.id.btn_voltar_geografia);
        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(geografia.this, Estudos.class);
            startActivity(intent);
            finish();
        });


        btnOQueEhGeografia.setOnClickListener(v -> toggleSection(txtOQueEhGeografia));
        btnContinentes.setOnClickListener(v -> toggleSection(txtContinentes));
        btnRelevo.setOnClickListener(v -> toggleSection(txtRelevo));
        btnClima.setOnClickListener(v -> toggleSection(txtClima));
        btnRios.setOnClickListener(v -> toggleSection(txtRios));
        btnUrbanoRural.setOnClickListener(v -> toggleSection(txtUrbanoRural));
        btnRegioes.setOnClickListener(v -> toggleSection(txtRegioes));
        btnSustentabilidade.setOnClickListener(v -> toggleSection(txtSustentabilidade));
    }


    private void toggleSection(TextView targetView) {
        TextView[] allSections = {txtOQueEhGeografia, txtContinentes, txtRelevo, txtClima, txtRios, txtUrbanoRural, txtRegioes, txtSustentabilidade};


        boolean isVisible = targetView.getVisibility() == View.VISIBLE;


        for (TextView view : allSections) {
            view.setVisibility(View.GONE);
        }


        if (!isVisible) {
            targetView.setVisibility(View.VISIBLE);
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