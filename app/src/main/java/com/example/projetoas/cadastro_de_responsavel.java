package com.example.projetoas;

import androidx.appcompat.app.AppCompatActivity;

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

public class cadastro_de_responsavel extends AppCompatActivity {

    private EditText etNome, etEmail, etCpf, etOutroResponsavel, etTelefone, etSenha, etConfirmarSenha;
    private RadioGroup rgTipoResponsavel;
    private Button btnCadastrar, btnVoltar;

    private BancoControllerContatos db;
    private long usuarioIdLogado;

    private static final int MIN_SENHA_LENGTH = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_de_responsavel);

        db = new BancoControllerContatos(this);

        usuarioIdLogado = getIntent().getLongExtra("ID_USUARIO_LOGADO", -1);
        if (usuarioIdLogado == -1) {
            Toast.makeText(this, "Erro: ID do usuário não encontrado.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        inicializarComponentes();
        configurarListeners();
        etOutroResponsavel.setVisibility(View.GONE);
    }

    private void inicializarComponentes() {
        etNome = findViewById(R.id.etNomeResponsavel);
        etEmail = findViewById(R.id.etEmailResponsavel);
        etCpf = findViewById(R.id.etCpfResponsavel);
        etOutroResponsavel = findViewById(R.id.etOutroResponsavel);
        etTelefone = findViewById(R.id.etTelefoneResponsavel);
        etSenha = findViewById(R.id.etSenhaResponsavel);
        etConfirmarSenha = findViewById(R.id.etConfirmarSenhaResponsavel);
        rgTipoResponsavel = findViewById(R.id.rgTipoResponsavel);
        btnCadastrar = findViewById(R.id.btnCadastrarResponsavel);
        btnVoltar = findViewById(R.id.btnVoltarResponsavel);
    }

    private void configurarListeners() {
        rgTipoResponsavel.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbOutroResponsavel) {
                etOutroResponsavel.setVisibility(View.VISIBLE);
                etOutroResponsavel.requestFocus();
            } else {
                etOutroResponsavel.setVisibility(View.GONE);
                etOutroResponsavel.setText("");
                etOutroResponsavel.setError(null);
            }
        });

        btnCadastrar.setOnClickListener(v -> efetuarCadastro());
        btnVoltar.setOnClickListener(v -> finish());
    }

    private void efetuarCadastro() {
        if (!validarCampos()) {
            Toast.makeText(this, "Corrija os campos em destaque.", Toast.LENGTH_LONG).show();
            return;
        }

        String nome = etNome.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String cpf = etCpf.getText().toString().trim();
        String telefone = etTelefone.getText().toString().trim();
        String senha = etSenha.getText().toString(); // Pega a senha
        String tipoResponsavel = obterTipoResponsavelSelecionado();

        // Chama o método do banco
        long id = db.inserirResponsavel(usuarioIdLogado, nome, email, cpf, telefone, senha, tipoResponsavel);

        if (id != -1) {
            Toast.makeText(this, "Responsável cadastrado com sucesso!", Toast.LENGTH_LONG).show();
            finish(); // Fecha a tela e volta
        } else {
            Toast.makeText(this, "Erro ao cadastrar. Verifique se o CPF já existe.", Toast.LENGTH_LONG).show();
        }
    }

    private String obterTipoResponsavelSelecionado() {
        int selectedId = rgTipoResponsavel.getCheckedRadioButtonId();
        if (selectedId == R.id.rbMae) {
            return "Mãe";
        } else if (selectedId == R.id.rbPai) {
            return "Pai";
        } else if (selectedId == R.id.rbAvo) {
            return "Avô/Avó";
        } else if (selectedId == R.id.rbOutroResponsavel) {
            String outro = etOutroResponsavel.getText().toString().trim();
            return TextUtils.isEmpty(outro) ? "Outro" : outro;
        }
        return "Não Selecionado";
    }

    private boolean validarCampos() {
        // ... (Sua lógica de validação está ótima, sem mudanças)
        etNome.setError(null);
        etEmail.setError(null);
        etCpf.setError(null);
        etOutroResponsavel.setError(null);
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
            etNome.setError("Nome obrigatório");
            focusView = etNome;
            erro = true;
        }

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("E-mail obrigatório");
            focusView = focusView == null ? etEmail : focusView;
            erro = true;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("E-mail inválido");
            focusView = focusView == null ? etEmail : focusView;
            erro = true;
        }

        if (TextUtils.isEmpty(cpf)) {
            etCpf.setError("CPF obrigatório");
            focusView = focusView == null ? etCpf : focusView;
            erro = true;
        } else if (cpf.length() < 11) {
            etCpf.setError("CPF inválido. Mínimo 11 dígitos.");
            focusView = focusView == null ? etCpf : focusView;
            erro = true;
        }

        if (TextUtils.isEmpty(telefone)) {
            etTelefone.setError("Telefone obrigatório");
            focusView = focusView == null ? etTelefone : focusView;
            erro = true;
        }

        if (TextUtils.isEmpty(senha)) {
            etSenha.setError("Senha obrigatória");
            focusView = focusView == null ? etSenha : focusView;
            erro = true;
        } else if (senha.length() < MIN_SENHA_LENGTH) {
            etSenha.setError("A senha deve ter no mínimo " + MIN_SENHA_LENGTH + " caracteres.");
            focusView = focusView == null ? etSenha : focusView;
            erro = true;
        }

        if (TextUtils.isEmpty(confirmarSenha)) {
            etConfirmarSenha.setError("Confirmação de senha obrigatória");
            focusView = focusView == null ? etConfirmarSenha : focusView;
            erro = true;
        } else if (!senha.equals(confirmarSenha)) {
            etConfirmarSenha.setError("As senhas não coincidem.");
            focusView = focusView == null ? etConfirmarSenha : focusView;
            erro = true;
        }

        int selectedId = rgTipoResponsavel.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Selecione o seu tipo de parentesco.", Toast.LENGTH_LONG).show();
            erro = true;
        } else if (selectedId == R.id.rbOutroResponsavel) {
            if (TextUtils.isEmpty(etOutroResponsavel.getText().toString().trim())) {
                etOutroResponsavel.setError("Especifique o seu parentesco.");
                focusView = focusView == null ? etOutroResponsavel : focusView;
                erro = true;
            }
        }

        if (erro) {
            if (focusView != null) {
                focusView.requestFocus();
            }
            return false;
        }

        return true;
    }
}
