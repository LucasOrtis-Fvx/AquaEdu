package com.example.projetoas;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


public class DetalheAcaoActivity extends AppCompatActivity {
    private TextView DetalheTitulo;
    private TextView DetalheDescricao;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_acao);


        DetalheTitulo = findViewById(R.id.DetalheTitulo);
        DetalheDescricao = findViewById(R.id.DetalheDescricao);


        String titulo = getIntent().getStringExtra("ACAO_TITULO");
        String descricao = getIntent().getStringExtra("ACAO_DESCRICAO");


        if (titulo != null) {
            DetalheTitulo.setText(titulo);
        } else {
            DetalheTitulo.setText("Título não encontrado");
        }


        if (descricao != null) {
            DetalheDescricao.setText(descricao);
        } else {
            DetalheDescricao.setText("Descrição não disponível.");
        }


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Detalhes da Ação");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}