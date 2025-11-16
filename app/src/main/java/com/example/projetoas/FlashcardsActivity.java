package com.example.projetoas;


import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;




import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;




import java.util.ArrayList;
import java.util.List;




public class FlashcardsActivity extends AppCompatActivity {


    private ImageButton btnVoltar;
    private Button btnFlashcards, btnCriar; // btnResumo removido


    private List<Flashcard> currentCards = new ArrayList<>();
    private int currentIndex = 0;
    private boolean mostrandoFrente = true;


    private final String[] materias = new String[]{
            "Ciências", "Português", "Inglês", "Artes", "História", "Matemática", "Geografia"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcards);


        iniciarComponentes();
        configurarListeners();
    }


    private void iniciarComponentes() {
        btnVoltar = findViewById(R.id.btn_voltar);


        btnFlashcards = findViewById(R.id.btnFlashcards);
        btnCriar = findViewById(R.id.btnCriar);
        // btnResumo removido: btnResumo = findViewById(R.id.btnResumo);
    }


    private void configurarListeners() {
        btnVoltar.setOnClickListener(v -> finish());


        btnFlashcards.setOnClickListener(v -> abrirEscolhaMateriaParaEstudo());
        btnCriar.setOnClickListener(v -> abrirCriarFlashcard());
        // Listener de btnResumo removido: btnResumo.setOnClickListener(v -> abrirCriarResumo());
    }


    private void abrirEscolhaMateriaParaEstudo() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Escolha a matéria");
        b.setItems(materias, (dialog, which) -> {
            String materia = materias[which];
            carregarEExibirFlashcards(materia);
        });
        b.setNegativeButton("Cancelar", null);
        b.show();
    }


    private void carregarEExibirFlashcards(String materia) {
        List<Flashcard> cards = FlashcardStore.loadCards(this, materia);
        if (cards == null || cards.isEmpty()) {
            Toast.makeText(this, "Nenhum flashcard em " + materia, Toast.LENGTH_SHORT).show();
            return;
        }


        currentCards = new ArrayList<>(cards);
        currentIndex = 0;
        mostrandoFrente = true;


        mostrarDialogFlashcard(materia);
    }


    private void mostrarDialogFlashcard(String materia) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(materia);


        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setPadding(36, 24, 36, 8);


        TextView tvCard = new TextView(this);
        tvCard.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        tvCard.setTextSize(18f);
        tvCard.setGravity(Gravity.CENTER);
        tvCard.setPadding(24, 40, 24, 40);
        tvCard.setBackgroundResource(android.R.color.white);
        tvCard.setTextColor(getResources().getColor(android.R.color.black));
        tvCard.setText(obterTextoAtual()); // pergunta inicial
        container.addView(tvCard);


        LinearLayout botoes = new LinearLayout(this);
        botoes.setOrientation(LinearLayout.HORIZONTAL);
        botoes.setGravity(Gravity.CENTER);
        botoes.setPadding(0, 18, 0, 0);


        Button btnFlip = new Button(this);
        btnFlip.setText("Virar");
        botoes.addView(btnFlip);


        Button btnNext = new Button(this);
        btnNext.setText("Próximo");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(24, 0, 0, 0);
        btnNext.setLayoutParams(lp);
        botoes.addView(btnNext);


        container.addView(botoes);


        b.setView(container);
        b.setNegativeButton("Fechar", null);


        AlertDialog dialog = b.create();


        btnFlip.setOnClickListener(v -> {
            if (currentCards.isEmpty()) return;
            mostrandoFrente = !mostrandoFrente;
            tvCard.setText(obterTextoAtual());
        });


        btnNext.setOnClickListener(v -> {
            if (currentCards.isEmpty()) return;
            currentIndex++;
            if (currentIndex >= currentCards.size()) currentIndex = 0;
            mostrandoFrente = true;
            tvCard.setText(obterTextoAtual());
        });


        dialog.show();
    }


    private String obterTextoAtual() {
        if (currentCards == null || currentCards.isEmpty()) return "";
        Flashcard f = currentCards.get(currentIndex);
        return mostrandoFrente ? f.getQuestion() : f.getAnswer();
    }


    private void abrirCriarFlashcard() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Criar Flashcard");


        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setPadding(36, 24, 36, 8);


        final TextView tvMateria = new TextView(this);
        tvMateria.setText("Matéria: " + materias[0]);
        tvMateria.setPadding(0, 0, 0, 12);
        container.addView(tvMateria);


        tvMateria.setOnClickListener(v -> {
            int idx = 0;
            for (int i = 0; i < materias.length; i++) {
                if (tvMateria.getText().toString().endsWith(materias[i])) {
                    idx = i;
                    break;
                }
            }
            idx = (idx + 1) % materias.length;
            tvMateria.setText("Matéria: " + materias[idx]);
        });


        EditText inPerg = new EditText(this);
        inPerg.setHint("Pergunta");
        inPerg.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        container.addView(inPerg);


        EditText inResp = new EditText(this);
        inResp.setHint("Resposta");
        inResp.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        container.addView(inResp);


        b.setView(container);


        b.setPositiveButton("Salvar", (dialog, which) -> {
            String materiaSelecionada = tvMateria.getText().toString().replace("Matéria: ", "").trim();
            String pergunta = inPerg.getText().toString().trim();
            String resposta = inResp.getText().toString().trim();


            if (pergunta.isEmpty() || resposta.isEmpty()) {
                Toast.makeText(this, "Preencha pergunta e resposta", Toast.LENGTH_SHORT).show();
                return;
            }


            // carregar lista existente, adicionar e salvar
            List<Flashcard> list = FlashcardStore.loadCards(this, materiaSelecionada);
            if (list == null) list = new ArrayList<>();
            list.add(new Flashcard(pergunta, resposta));
            FlashcardStore.saveCards(this, materiaSelecionada, list);


            Toast.makeText(this, "Flashcard salvo em " + materiaSelecionada, Toast.LENGTH_SHORT).show();
        });


        b.setNegativeButton("Cancelar", null);
        b.show();
    }
}