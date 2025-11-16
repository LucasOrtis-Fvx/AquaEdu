package com.example.projetoas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class Estudos extends AppCompatActivity implements View.OnClickListener {


    private CardView cardCiencia, cardGramatica, cardLiteratura, cardRedacao,
            cardMatematica, cardHistoria, cardGeografia, cardFlashcards;

    private CardView cardJogosFerramenta, cardVideoaulas, cardJogosResumo;

    private ImageButton btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudos);

        inicializarViews();
        configurarListeners();
    }


    private void inicializarViews() {
        cardCiencia = findViewById(R.id.card_ciencia);
        cardGramatica = findViewById(R.id.card_gramatica);
        cardLiteratura = findViewById(R.id.card_literatura);
        cardRedacao = findViewById(R.id.card_redacao);
        cardMatematica = findViewById(R.id.card_matematica);
        cardHistoria = findViewById(R.id.card_historia);
        cardGeografia = findViewById(R.id.card_geografia);

        cardFlashcards = findViewById(R.id.card_flashcards);

        cardJogosFerramenta = findViewById(R.id.card_mapas_mentais);
        cardVideoaulas = findViewById(R.id.card_videoaulas);
        cardJogosResumo = findViewById(R.id.card_jogos_resumo);

        btnBack = findViewById(R.id.btn_back_estudos);
    }


    private void configurarListeners() {
        cardCiencia.setOnClickListener(this);
        cardGramatica.setOnClickListener(this);
        cardLiteratura.setOnClickListener(this);
        cardRedacao.setOnClickListener(this);
        cardMatematica.setOnClickListener(this);
        cardHistoria.setOnClickListener(this);
        cardGeografia.setOnClickListener(this);
        cardFlashcards.setOnClickListener(this);

        cardJogosFerramenta.setOnClickListener(this);
        cardVideoaulas.setOnClickListener(this);
        cardJogosResumo.setOnClickListener(this);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(Estudos.this, tela_menu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.card_ciencia) {
            abrirMateriaDetalhe(ciencias.class, "Ciências");
        } else if (id == R.id.card_gramatica) {
            abrirMateriaDetalhe(portugues.class, "Português");
        } else if (id == R.id.card_literatura) {
            abrirMateriaDetalhe(artes.class, "Artes");
        } else if (id == R.id.card_redacao) {
            abrirMateriaDetalhe(ingles.class, "Inglês");
        } else if (id == R.id.card_matematica) {
            abrirMateriaDetalhe(Matematica.class, "Matemática");
        } else if (id == R.id.card_historia) {
            abrirMateriaDetalhe(historia.class, "História");
        } else if (id == R.id.card_geografia) {
            abrirMateriaDetalhe(geografia.class, "Geografia");
        } else if (id == R.id.card_flashcards) {
            abrirMateriaDetalhe(FlashcardsActivity.class, "Flashcards");
        }

        // --- FERRAMENTAS ---
        else if (id == R.id.card_mapas_mentais) {
            abrirFerramenta("Jogos (Ferramenta)");
        } else if (id == R.id.card_videoaulas) {
            abrirFerramenta("Videoaulas");
        } else if (id == R.id.card_jogos_resumo) {
            abrirFerramenta("Atividades (Jogos)");
        }
    }


    private void abrirMateriaDetalhe(Class<?> activityClass, String nomeMateria) {
        Toast.makeText(this, "Abrindo " + nomeMateria + "...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, activityClass);
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "ERRO: Classe '" + nomeMateria + "' não encontrada/declarada.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    private void abrirFerramenta(String nomeFerramenta) {
        Toast.makeText(this, "Abrindo " + nomeFerramenta + "...", Toast.LENGTH_SHORT).show();

        Intent intent = null;

        if (nomeFerramenta.equals("Jogos (Ferramenta)")) {
            intent = new Intent(this, activity_quiz.class);
        } else if (nomeFerramenta.equals("Atividades (Jogos)")) {
            intent = new Intent(this, atividades.class);
        } else if (nomeFerramenta.equals("Videoaulas")) {
            intent = new Intent(this, VideoAulasActivity.class);
        }

        if (intent != null) {
            try {
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "ERRO: Classe de ferramenta não encontrada/declarada.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}