package com.example.projetoas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ODSAtividadeActivity extends AppCompatActivity {

    private ScrollView scrollView;
    private String nomeUsuario = "Bruno";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ods_atividade);

        Button btnVoltarMenu = findViewById(R.id.btn_voltar_menu);
        scrollView = findViewById(R.id.scrollView);

        btnVoltarMenu.setOnClickListener(v -> {

            finish();

        });

        verificarProgressoDasAtividades();
    }



    private void verificarProgressoDasAtividades() {
        int[] progressBarIds = {
                R.id.progress_impacto,
                R.id.progress_residuos,
                R.id.progress_eficiencia,
                R.id.progress_biodiversidade,
                R.id.progress_engajamento
        };


        int totalAtividades = progressBarIds.length;
        int atividadesConcluidas = 0;


        for (int id : progressBarIds) {
            ProgressBar progressBar = findViewById(id);
            if (progressBar == null) continue;

            if (progressBar.getProgress() >= 100) {
                atividadesConcluidas++;
            }
        }

        scrollView.setVisibility(View.VISIBLE);

    }
}