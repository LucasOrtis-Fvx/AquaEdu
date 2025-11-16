package com.example.projetoas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ImageButton;


public class OdsMarActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView cardConscientizacao;
    private CardView cardObjetivosOds;
    private CardView cardExerciciosMar;
    private CardView cardFaunaFlora;
    private CardView cardVoluntariado;
    private ImageButton btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_estude_o_mar);

        findViews();
        setClickListeners();
    }


    private void findViews() {
        cardConscientizacao = findViewById(R.id.card_conscientizacao);
        cardObjetivosOds = findViewById(R.id.card_objetivos_ods);
        cardExerciciosMar = findViewById(R.id.card_exercicios_mar);
        cardFaunaFlora = findViewById(R.id.card_fauna_flora);

        cardVoluntariado = findViewById(R.id.card_voluntariado);

        btnBack = findViewById(R.id.btn_back_to_menu);
    }

    private void setClickListeners() {
        cardConscientizacao.setOnClickListener(this);
        cardObjetivosOds.setOnClickListener(this);
        cardExerciciosMar.setOnClickListener(this);
        cardFaunaFlora.setOnClickListener(this);
        cardVoluntariado.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String mensagem = "";

        Intent intent = null;


        if (id == R.id.btn_back_to_menu) {
            finish();
            return;
        }


        if (id == R.id.card_conscientizacao) {
            mensagem = "Abrindo Conteúdo: Ameaças e Perigo Ambiental...";
            intent = new Intent(this, AmeacasPerigosActivity.class);
        }
        else if (id == R.id.card_objetivos_ods) {
            mensagem = "Detalhando os Objetivos ODS...";
            intent = new Intent(this, ObjetivosOdsActivity.class);
        }
        else if (id == R.id.card_exercicios_mar) {
            mensagem = "Explorando Animais Marinhos...";
            intent = new Intent(this, animais_marinhos.class);
        } else if (id == R.id.card_fauna_flora) {
            mensagem = "Explorando Fauna e Flora Oceânica...";
            intent = new Intent(this, OdsFaunaFloraActivity.class);
        }
        else if (id == R.id.card_voluntariado) {
            mensagem = "Encontrando Ações de Voluntariado...";
            intent = new Intent(this, acoes_e_volunatrios.class);
        }


        if (!mensagem.isEmpty()) {
            Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();

            if (intent != null) {
                startActivity(intent);
            }
        }
    }
}