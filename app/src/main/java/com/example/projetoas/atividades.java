package com.example.projetoas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class atividades extends AppCompatActivity {

    private LinearLayout rootLayout;
    private ScrollView scrollView;
    private LinearLayout emptyViewSemAtividades;
    private LinearLayout emptyViewTudoConcluido;
    private String nomeUsuario = "Bruno";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividades);

        ImageButton btnVoltarMenu = findViewById(R.id.btn_voltar_menu);
        scrollView = findViewById(R.id.scrollView);

        rootLayout = findViewById(R.id.root_layout);

        emptyViewSemAtividades = criarEmptyView(
                "Você não tem nenhuma atividade no momento 😀",
                "Que tal dar uma revisada nos conteúdos?"
        );
        emptyViewTudoConcluido = criarEmptyView(
                "AEEEEEE " + nomeUsuario + ", VOCÊ FEZ TODAS AS ATIVIDADES DA SEMANA 😁 🎉",
                "QUE TAL JOGAR UM JOGO AGORA PRA DESCANSAR?"
        );

        if (rootLayout != null) {
            rootLayout.addView(emptyViewSemAtividades);
            rootLayout.addView(emptyViewTudoConcluido);
        }

        btnVoltarMenu.setOnClickListener(v -> {
            Intent intent = new Intent(this, Estudos.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        verificarProgressoDasAtividades();
    }


    private LinearLayout criarEmptyView(String titulo, String subtitulo) {
        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1.0f
        );
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(android.view.Gravity.CENTER);
        layout.setPadding(32, 32, 32, 32);
        layout.setVisibility(View.GONE);


        TextView tvTitulo = new TextView(this);
        tvTitulo.setText(titulo);
        tvTitulo.setTextSize(20);
        tvTitulo.setTextColor(getResources().getColor(android.R.color.black));
        tvTitulo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tvTitulo.setTypeface(null, android.graphics.Typeface.BOLD);


        TextView tvSubtitulo = new TextView(this);
        tvSubtitulo.setText(subtitulo);
        tvSubtitulo.setTextSize(16);
        tvSubtitulo.setTextColor(getResources().getColor(android.R.color.darker_gray));
        tvSubtitulo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


        layout.addView(tvTitulo);
        layout.addView(tvSubtitulo);


        return layout;
    }


    private void verificarProgressoDasAtividades() {
        int[] progressBarIds = {
                R.id.progress_portugues, R.id.progress_matematica,
                R.id.progress_ciencias, R.id.progress_historia,
                R.id.progress_geografia, R.id.progress_arte,
                R.id.progress_edfisica, R.id.progress_ingles
        };


        int totalAtividades = progressBarIds.length;
        int atividadesConcluidas = 0;
        int atividadesVazias = 0;


        for (int id : progressBarIds) {
            ProgressBar progressBar = findViewById(id);
            if (progressBar != null) {
                int progresso = progressBar.getProgress();


                if (progresso == 0) {
                    atividadesVazias++;
                } else if (progresso >= 100) {
                    atividadesConcluidas++;
                }
            }
        }

        if (atividadesVazias == totalAtividades) {
            scrollView.setVisibility(View.GONE);
            emptyViewSemAtividades.setVisibility(View.VISIBLE);
            emptyViewTudoConcluido.setVisibility(View.GONE);
        } else if (atividadesConcluidas == totalAtividades) {
            scrollView.setVisibility(View.GONE);
            emptyViewTudoConcluido.setVisibility(View.VISIBLE);
            emptyViewSemAtividades.setVisibility(View.GONE);
        } else {
            scrollView.setVisibility(View.VISIBLE);
            emptyViewSemAtividades.setVisibility(View.GONE);
            emptyViewTudoConcluido.setVisibility(View.GONE);
        }
    }
}