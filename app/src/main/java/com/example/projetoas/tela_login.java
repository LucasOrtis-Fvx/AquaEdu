package com.example.projetoas;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetoas.BancoControllerContatos;

public class tela_login extends AppCompatActivity {

    private EditText etUsuario, etSenha;
    private Button btnEntrar, btnCadastro;

    private BancoControllerContatos db;
    public static final String PREFS_NAME = "SessaoUsuario";
    public static final String KEY_USER_ID = "ID_USUARIO_LOGADO";
    public static final String KEY_USER_NAME = "NOME_USUARIO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        long usuarioId = prefs.getLong(KEY_USER_ID, -1);

        if (usuarioId != -1) {
            Intent intent = new Intent(this, tela_menu.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_tela_login);

        etUsuario = findViewById(R.id.etUsuario);
        etSenha = findViewById(R.id.etSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnCadastro = findViewById(R.id.btnCadastro);

        db = new BancoControllerContatos(this);

        btnCadastro.setOnClickListener(v -> {
            Intent intent = new Intent(tela_login.this, tela_cadastro.class);
            startActivity(intent);
        });

        btnEntrar.setOnClickListener(v -> {
            String identificador = etUsuario.getText().toString().trim();
            String senha = etSenha.getText().toString().trim();

            if (identificador.isEmpty() || senha.isEmpty()) {
                Toast.makeText(tela_login.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            long idEncontrado = db.loginUsuario(identificador, senha);

            if (idEncontrado != -1) {

                SharedPreferences.Editor editor = prefs.edit();
                editor.putLong(KEY_USER_ID, idEncontrado);

                String nomeUsuario = db.verificarLogin(identificador, senha);
                editor.putString(KEY_USER_NAME, nomeUsuario);
                editor.apply();

                Intent intent = new Intent(tela_login.this, tela_menu.class);

                intent.putExtra(KEY_USER_NAME, nomeUsuario);

                startActivity(intent);
                finish();
            } else {
                Toast.makeText(tela_login.this, "Usuário ou senha incorretos!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}