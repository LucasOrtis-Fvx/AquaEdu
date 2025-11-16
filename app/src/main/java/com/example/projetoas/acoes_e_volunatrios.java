package com.example.projetoas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.projetoas.BancoControllerContatos;

public class acoes_e_volunatrios extends AppCompatActivity implements AcaoAdapter.OnAcaoListener {

    private RecyclerView rvAcoes;
    private ImageButton btnVoltar;

    private List<acoes> listaAcoes;
    private AcaoAdapter adapter;
    private BancoControllerContatos db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_acoes_e_voluntarios);

        db = new BancoControllerContatos(this);

        rvAcoes = findViewById(R.id.rvAcoes);

        btnVoltar = findViewById(R.id.btn_back_acoes);
        if (btnVoltar == null) {
        }

        setupRecyclerView();
        setupBackButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarAcoesDoBanco();
    }

    private void setupRecyclerView() {
        listaAcoes = new ArrayList<>();

        adapter = new AcaoAdapter(listaAcoes, this, this);
        rvAcoes.setLayoutManager(new LinearLayoutManager(this));
        rvAcoes.setAdapter(adapter);
    }

    private void carregarAcoesDoBanco() {
        List<acoes> novasAcoes = db.listarAcoes();

        listaAcoes.clear();
        if (novasAcoes != null && !novasAcoes.isEmpty()) {
            listaAcoes.addAll(novasAcoes);
        } else {
            carregarAcoesExemplo();
        }
        adapter.notifyDataSetChanged();
    }

    private void carregarAcoesExemplo() {
        if (listaAcoes.isEmpty()) {
            listaAcoes.add(new acoes("Limpeza de praias e rios", "Participe de mutirões para remover lixo das costas e margens.", "🌊"));
            listaAcoes.add(new acoes("Proteção de animais marinhos", "Voluntariado em centros de reabilitação de tartarugas e aves.", "🐢"));
            listaAcoes.add(new acoes("Denúncias de crimes ambientais", "Reporte despejo ilegal de esgoto, pesca predatória e poluição.", "⚖️"));
            listaAcoes.add(new acoes("Campanhas de conscientização", "Ajude a educar o público em escolas e eventos sobre a poluição por plásticos.", "📢"));
            listaAcoes.add(new acoes("Projetos de reciclagem", "Implemente programas de coleta seletiva em comunidades costeiras.", "🌱"));
        }
    }

    private void setupBackButton() {
        if (btnVoltar != null) {
            btnVoltar.setOnClickListener(v -> {

                Intent intent = new Intent(acoes_e_volunatrios.this, OdsMarActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(intent);
                finish();
            });
        }
    }

    @Override
    public void onSaberMaisClick(acoes acao) {
        Intent intent = new Intent(this, DetalheAcaoActivity.class);
        intent.putExtra("ACAO_TITULO", acao.getTitulo());
        intent.putExtra("ACAO_DESCRICAO", acao.getDescricao());
        startActivity(intent);
    }
}