package com.example.projetoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

// Importa o novo controlador
import com.example.projetoas.BancoControllerContatos;

public class tela_cadastro extends AppCompatActivity {

    private EditText etNome, etEmail, etCpf, etOutroGenero, etTelefone, etSenha, etConfirmarSenha;
    private RadioGroup rgGenero;
    private Button btnCadastrar, btnVoltar;

    private static final int MIN_SENHA_LENGTH = 6;
    private BancoControllerContatos db; // MUDANÇA

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        // MUDANÇA: Instancia o novo controlador
        db = new BancoControllerContatos(this);

        inicializarComponentes();
        configurarListeners();
        etOutroGenero.setVisibility(View.GONE);
    }

    private void inicializarComponentes() {
        etNome = findViewById(R.id.etNome);
        etEmail = findViewById(R.id.etEmail);
        etCpf = findViewById(R.id.etCpf);
        etOutroGenero = findViewById(R.id.etOutroGenero);
        etTelefone = findViewById(R.id.etTelefone);
        etSenha = findViewById(R.id.etSenha);
        etConfirmarSenha = findViewById(R.id.etConfirmarSenha);
        rgGenero = findViewById(R.id.rgGenero);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnVoltar = findViewById(R.id.btnVoltar);
    }

    private void configurarListeners() {
        rgGenero.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbOutro) {
                etOutroGenero.setVisibility(View.VISIBLE);
                etOutroGenero.requestFocus();
            } else {
                etOutroGenero.setVisibility(View.GONE);
                etOutroGenero.setText("");
                etOutroGenero.setError(null);
            }
        });

        btnCadastrar.setOnClickListener(v -> efetuarCadastro());

        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(tela_cadastro.this, tela_login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void efetuarCadastro() {
        if (!validarCampos()) return;

        String nome = etNome.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String cpf = etCpf.getText().toString().trim();
        String telefone = etTelefone.getText().toString().trim();
        String senha = etSenha.getText().toString().trim();

        int idGeneroSelecionado = rgGenero.getCheckedRadioButtonId();
        String genero = "";
        if (idGeneroSelecionado == R.id.rbMasculino) genero = "Masculino";
        else if (idGeneroSelecionado == R.id.rbFeminino) genero = "Feminino";
        else if (idGeneroSelecionado == R.id.rbOutro)
            genero = etOutroGenero.getText().toString().trim();

        // MUDANÇA: O método agora se chama inserirUsuario e tem todos os campos
        long id = db.inserirUsuario(nome, email, cpf, telefone, genero, senha);

        if (id != -1) {
            Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(tela_cadastro.this, tela_login.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "E-mail ou CPF já cadastrado.", Toast.LENGTH_LONG).show();
            etEmail.setError("Verifique se o e-mail ou CPF já existem");
            etEmail.requestFocus();
        }
    }

    private boolean validarCampos() {
        // ... (Sua lógica de validação está ótima, sem mudanças)
        etNome.setError(null);
        etEmail.setError(null);
        etCpf.setError(null);
        etOutroGenero.setError(null);
        etTelefone.setError(null);
        etSenha.setError(null);
        etConfirmarSenha.setError(null);

        String nome = etNome.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String cpf = etCpf.getText().toString().trim();
        String telefone = etTelefone.getText().toString().trim();
        String senha = etSenha.getText().toString();
        String confirmarSenha = etConfirmarSenha.getText().toString();

        boolean erro = false;
        View focusView = null;

        if (TextUtils.isEmpty(nome)) {
            etNome.setError("Informe o nome completo.");
            focusView = etNome;
            erro = true;
        }

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("E-mail inválido.");
            if (focusView == null) focusView = etEmail;
            erro = true;
        }

        if (TextUtils.isEmpty(cpf) || cpf.length() != 11) {
            etCpf.setError("CPF inválido (11 dígitos).");
            if (focusView == null) focusView = etCpf;
            erro = true;
        }

        if (TextUtils.isEmpty(telefone) || telefone.length() < 10) {
            etTelefone.setError("Telefone inválido.");
            if (focusView == null) focusView = etTelefone;
            erro = true;
        }

        if (senha.length() < MIN_SENHA_LENGTH) {
            etSenha.setError("Senha deve ter pelo menos 6 caracteres.");
            if (focusView == null) focusView = etSenha;
            erro = true;
        }

        if (!senha.equals(confirmarSenha)) {
            etConfirmarSenha.setError("As senhas não coincidem.");
            if (focusView == null) focusView = etConfirmarSenha;
            erro = true;
        }

        int idGeneroSelecionado = rgGenero.getCheckedRadioButtonId();
        if (idGeneroSelecionado == -1) {
            Toast.makeText(this, "Por favor, selecione um gênero.", Toast.LENGTH_SHORT).show();
            if (focusView == null) focusView = rgGenero;
            erro = true;
        } else if (idGeneroSelecionado == R.id.rbOutro && TextUtils.isEmpty(etOutroGenero.getText().toString().trim())) {
            etOutroGenero.setError("Especifique o gênero.");
            if (focusView == null) focusView = etOutroGenero;
            erro = true;
        }


        if (erro) {

            if (focusView instanceof EditText) {
                focusView.requestFocus();
            }
            return false;
        }

        return true;
    }
}
