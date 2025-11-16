package com.example.projetoas;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetoas.BancoControllerContatos;

public class info_usuarios extends AppCompatActivity {

    private EditText etNomeContaInfo;
    private EditText etEmailContaInfo;
    private TextView tvCpfContaInfo;
    private EditText etTelefoneContaInfo;
    private Button btnSalvarAlteracoesConta;
    private Button btnVoltarConta;

    private BancoControllerContatos db;
    private long usuarioIdLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_usuarios);

        db = new BancoControllerContatos(this);

        usuarioIdLogado = getIntent().getLongExtra("ID_USUARIO_LOGADO", -1);
        if (usuarioIdLogado == -1) {
            Toast.makeText(this, "Erro: ID do usuário não encontrado.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        inicializarComponentes();
        carregarDadosUsuario();
        configurarListeners();
    }

    private void inicializarComponentes() {
        etNomeContaInfo = findViewById(R.id.etNomeContaInfo);
        etEmailContaInfo = findViewById(R.id.etEmailContaInfo);
        tvCpfContaInfo = findViewById(R.id.tvCpfContaInfo);
        etTelefoneContaInfo = findViewById(R.id.etTelefoneContaInfo);
        btnSalvarAlteracoesConta = findViewById(R.id.btnSalvarAlteracoesConta);
        btnVoltarConta = findViewById(R.id.btnVoltarConta);
    }

    private void carregarDadosUsuario() {
        Cursor cursor = db.carregarUsuarioPorId(usuarioIdLogado);
        if (cursor != null && cursor.moveToFirst()) {

            String nome = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.COL_USUARIO_NOME));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.COL_USUARIO_EMAIL));
            String cpf = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.COL_USUARIO_CPF));
            String telefone = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.COL_USUARIO_TELEFONE));

            etNomeContaInfo.setText(nome);
            etEmailContaInfo.setText(email);
            tvCpfContaInfo.setText(cpf);
            etTelefoneContaInfo.setText(telefone);

            cursor.close();
        } else {
            Toast.makeText(this, "Erro ao carregar dados.", Toast.LENGTH_SHORT).show();
        }
    }

    private void configurarListeners() {
        btnSalvarAlteracoesConta.setOnClickListener(v -> salvarAlteracoes());
        btnVoltarConta.setOnClickListener(v -> finish());
    }

    private void salvarAlteracoes() {
        String novoNome = etNomeContaInfo.getText().toString().trim();
        String novoEmail = etEmailContaInfo.getText().toString().trim();
        String novoTelefone = etTelefoneContaInfo.getText().toString().trim();

        if (novoNome.isEmpty() || novoEmail.isEmpty()) {
            etNomeContaInfo.setError("Nome e E-mail são obrigatórios.");
            return;
        }

        boolean sucesso = db.atualizarUsuario(usuarioIdLogado, novoNome, novoEmail, novoTelefone);

        if(sucesso) {
            Toast.makeText(this, "Dados atualizados com sucesso!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Erro ao atualizar dados.", Toast.LENGTH_LONG).show();
        }
    }
}