package com.example.projetoas;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import android.view.View;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class tela_Meu_Progresso extends AppCompatActivity {


    private TextView tvPeriodo;
    private CircularProgressIndicator progressParticipacao;
    private TextView tvPercentualParticipacao;
    private CircularProgressIndicator progressDesempenho;
    private TextView tvPercentualDesempenho;
    private TextView tvListaPrecisaEstudar;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_meu_progresso);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.my_progress_title);
        }

        inicializarViews();
        configurarListeners();
        carregarDadosDeProgresso();
    }

    private void inicializarViews() {
        tvPeriodo = findViewById(R.id.tv_periodo_v2);
        progressParticipacao = findViewById(R.id.progress_participacao);
        tvPercentualParticipacao = findViewById(R.id.tv_percentual_participacao_v2);
        progressDesempenho = findViewById(R.id.progress_desempenho);
        tvPercentualDesempenho = findViewById(R.id.tv_percentual_desempenho_v2);
        tvListaPrecisaEstudar = findViewById(R.id.tv_lista_precisa_estudar_v2);
        btnBack = findViewById(R.id.btn_back);
    }


    private void configurarListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(tela_Meu_Progresso.this, tela_menu.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(intent);
            }
        });
    }

    private void carregarDadosDeProgresso() {
        int participacao = 95;
        int desempenho = 88;

        List<String> precisaEstudar = new ArrayList<>();
        precisaEstudar.add("[Matemática] Cap. 05 - Funções Trigonométricas");
        precisaEstudar.add("[Física] Cap. 07 - Leis de Newton");

        progressParticipacao.setProgress(participacao, true);
        progressDesempenho.setProgress(desempenho, true);

        tvPercentualParticipacao.setText(String.format(Locale.getDefault(), "%d%%", participacao));
        tvPercentualDesempenho.setText(String.format(Locale.getDefault(), "%d%%", desempenho));

        atualizarListaCapitulos(tvListaPrecisaEstudar, precisaEstudar);
    }

    private void atualizarListaCapitulos(TextView textView, List<String> capitulos) {
        if (capitulos == null || capitulos.isEmpty()) {
            textView.setText(R.string.no_chapter);
        } else {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < capitulos.size(); i++) {
                builder.append("• ").append(capitulos.get(i));
                if (i < capitulos.size() - 1) {
                    builder.append("\n");
                }
            }
            textView.setText(builder.toString());
        }
    }
}
