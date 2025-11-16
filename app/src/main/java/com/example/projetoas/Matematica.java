package com.example.projetoas;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html; // IMPORTAÇÃO NECESSÁRIA
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Intent;


public class Matematica extends AppCompatActivity {
    private TextView txtFormulas, txtCuriosidades, txtCriadores;
    private Button btnVerFormulas, btnVerCuriosidades, btnVerCriadores;
    private ImageButton btnVoltar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matematica);




        txtFormulas = findViewById(R.id.txtFormulasMatematica);
        txtCuriosidades = findViewById(R.id.txtCuriosidadesMatematica);
        txtCriadores = findViewById(R.id.txtCriadoresMatematica);
        btnVerFormulas = findViewById(R.id.btnVerFormulas);
        btnVerCuriosidades = findViewById(R.id.btnVerCuriosidades);
        btnVerCriadores = findViewById(R.id.btnVerCriadores);
        btnVoltar = findViewById(R.id.btnVoltarMatematica);


        processarConteudoHtml(txtFormulas);
        processarConteudoHtml(txtCuriosidades);
        processarConteudoHtml(txtCriadores);




        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(Matematica.this, Estudos.class);
            startActivity(intent);
            finish();
        });




        btnVerFormulas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(txtFormulas);
                txtCuriosidades.setVisibility(View.GONE);
                txtCriadores.setVisibility(View.GONE);
            }
        });




        btnVerCuriosidades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(txtCuriosidades);
                txtFormulas.setVisibility(View.GONE);
                txtCriadores.setVisibility(View.GONE);
            }
        });




        btnVerCriadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(txtCriadores);
                txtFormulas.setVisibility(View.GONE);
                txtCuriosidades.setVisibility(View.GONE);
            }
        });
    }




    private void toggleVisibility(TextView view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
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