package com.example.projetoas;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetoas.BancoControllerContatos;
// Importação de CriaBanco presumida

public class InfoResponsavelActivity extends AppCompatActivity {

    private static final String TAG = "InfoResponsavelActivity";

    private EditText etNomeResponsavelInfo;
    private EditText etEmailResponsavelInfo;
    private EditText etTelefoneResponsavelInfo;
    private TextView tvCpfResponsavelInfo;

    // btnSalvarDados REMOVIDO
    private Button btnVoltarInfo;

    private BancoControllerContatos db;
    private long responsavelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_resp);

        db = new BancoControllerContatos(this);

        responsavelId = getIntent().getLongExtra("ID_RESPONSAVEL", -1);

        if (responsavelId == -1) {
            Toast.makeText(this, "Erro: ID do responsável não encontrado.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        inicializarComponentes();
        configurarListeners();
        carregarDadosResponsavel();
    }

    private void inicializarComponentes() {
        etNomeResponsavelInfo = findViewById(R.id.etNomeResponsavelInfo);
        etEmailResponsavelInfo = findViewById(R.id.etEmailResponsavelInfo);
        etTelefoneResponsavelInfo = findViewById(R.id.etTelefoneResponsavelInfo);
        tvCpfResponsavelInfo = findViewById(R.id.tvCpfResponsavelInfo);

        // Inicialização de btnSalvarDados removida
        btnVoltarInfo = findViewById(R.id.btnVoltarInfo);
    }

    private void configurarListeners() {
        // O botão Voltar agora chama a função de salvar antes de fechar a tela.
        btnVoltarInfo.setOnClickListener(v -> {
            salvarAlteracoes();
            finish();
        });
    }

    private void carregarDadosResponsavel() {
        Cursor cursor = db.carregarResponsavelPorId(responsavelId);

        if (cursor != null && cursor.moveToFirst()) {

            try {
                String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String cpf = cursor.getString(cursor.getColumnIndexOrThrow("cpf"));
                String telefone = cursor.getString(cursor.getColumnIndexOrThrow("telefone"));

                etNomeResponsavelInfo.setText(nome);
                etEmailResponsavelInfo.setText(email);
                tvCpfResponsavelInfo.setText(cpf);
                etTelefoneResponsavelInfo.setText(telefone);

            } catch (IllegalArgumentException e) {
                Log.e(TAG, "Erro ao ler colunas do Cursor. Verifique as constantes do CriaBanco.", e);
                Toast.makeText(this, "Erro interno ao carregar dados.", Toast.LENGTH_LONG).show();
            } finally {
                cursor.close();
            }
        } else {
            Toast.makeText(this, "Não foi possível carregar os dados.", Toast.LENGTH_SHORT).show();
        }
    }

    private void salvarAlteracoes() {
        String nome = etNomeResponsavelInfo.getText().toString().trim();
        String email = etEmailResponsavelInfo.getText().toString().trim();
        String telefone = etTelefoneResponsavelInfo.getText().toString().trim();

        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
            Toast.makeText(this, "Preencha os campos Nome, E-mail e Telefone.", Toast.LENGTH_LONG).show();
            return;
        }

        boolean dadosOk = db.atualizarResponsavel(responsavelId, nome, email, telefone);
        if (dadosOk) {
            Toast.makeText(this, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Erro ao atualizar os dados.", Toast.LENGTH_SHORT).show();
        }
    }
}