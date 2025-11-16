package com.example.projetoas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonStartStudy = findViewById(R.id.buttonStartStudy);
        Button buttonAbout = findViewById(R.id.buttonAbout);

        buttonStartStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Botão 'Começar a Estudar' clicado!");

                // Redireciona para a tela de Login
                Intent intent = new Intent(MainActivity.this, tela_login.class);
                startActivity(intent);
            }
        });

        buttonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Botão 'Sobre o Projeto' clicado!.");

                // Redireciona para a tela Sobre
                Intent intent = new Intent(MainActivity.this, SobreActivity.class);
                startActivity(intent);
            }
        });
    }
}