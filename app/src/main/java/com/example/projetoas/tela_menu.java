package com.example.projetoas;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class tela_menu extends AppCompatActivity {

    private TextView textViewTitle;
    private Button buttonOds;
    private Button buttonProgress;
    private Button buttonSettings;

    private long usuarioIdLogado = -1;
    public static final String PREFS_NAME = "SessaoUsuario";
    public static final String KEY_USER_ID = "ID_USUARIO_LOGADO";
    public static final String KEY_USER_NAME = "NOME_USUARIO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_menu);

        // --- CARREGA SESSÃO A PARTIR DE PREFERENCES ---
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        usuarioIdLogado = prefs.getLong(KEY_USER_ID, -1);

        if (usuarioIdLogado == -1) {
            // Se o ID for -1, a sessão está perdida. Redirecionar para o login
            Toast.makeText(this, "Aviso: Sessão expirada. Faça login novamente.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, tela_login.class);
            // Limpa a pilha de atividades para que o usuário não possa voltar
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return; // Encerra o onCreate para evitar erros
        }

        inicializarComponentes();
        definirTituloDoCabecalho();
        configurarListeners();
    }


    private void inicializarComponentes() {
        textViewTitle = findViewById(R.id.textViewTitle);
        buttonOds = findViewById(R.id.buttonMenuODS);
        buttonProgress = findViewById(R.id.buttonMenuProgress);
        buttonSettings = findViewById(R.id.buttonMenuSettings);
    }


    private void definirTituloDoCabecalho() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        // Pega o nome do SharedPreferences
        String nomeRecebido = prefs.getString(KEY_USER_NAME, "Usuário");

        // Certifique-se de que sua string resource R.string.header_title existe e tem um placeholder %s (ex: "Bem-vindo, %s!")
        String tituloFormatado = String.format(getString(R.string.header_title), nomeRecebido);

        textViewTitle.setText(tituloFormatado);
    }


    private void configurarListeners() {

        buttonOds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(tela_menu.this, "Iniciando Estudo Agora!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(tela_menu.this, Estudos.class);
                startActivity(intent);
            }
        });

        buttonProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(tela_menu.this, "Acessando Meu Progresso", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(tela_menu.this, tela_Meu_Progresso.class);
                startActivity(intent);
            }
        });

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(tela_menu.this, "Abrindo Ajustes", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(tela_menu.this, tela_ajustes.class);

                // Repassa o ID carregado para a tela de Ajustes
                intent.putExtra(KEY_USER_ID, usuarioIdLogado);

                startActivity(intent);
            }
        });

        findViewById(R.id.cardStudy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(tela_menu.this, "Acessando ODS & Mar", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(tela_menu.this, OdsMarActivity.class);
                startActivity(intent);
            }
        });
    }
}