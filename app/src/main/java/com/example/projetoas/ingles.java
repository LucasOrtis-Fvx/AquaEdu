package com.example.projetoas;


import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


public class ingles extends AppCompatActivity {


    private ImageButton btnVoltar;

    private TextView txtGreetings, txtColorsNumbers, txtFamily, txtBody, txtRoutine, txtFood, txtCulture;

    private Button btnGreetings, btnColorsNumbers, btnFamily, btnBody, btnRoutine, btnFood, btnCulture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingles);

        txtGreetings = findViewById(R.id.txtGreetings);
        txtColorsNumbers = findViewById(R.id.txtColorsNumbers);
        txtFamily = findViewById(R.id.txtFamily);
        txtBody = findViewById(R.id.txtBody);
        txtRoutine = findViewById(R.id.txtRoutine);
        txtFood = findViewById(R.id.txtFood);
        txtCulture = findViewById(R.id.txtCulture);


        processarConteudoHtml(txtGreetings);
        processarConteudoHtml(txtColorsNumbers);
        processarConteudoHtml(txtFamily);
        processarConteudoHtml(txtBody);
        processarConteudoHtml(txtRoutine);
        processarConteudoHtml(txtFood);
        processarConteudoHtml(txtCulture);

        btnGreetings = findViewById(R.id.btnGreetings);
        btnColorsNumbers = findViewById(R.id.btnColorsNumbers);
        btnFamily = findViewById(R.id.btnFamily);
        btnBody = findViewById(R.id.btnBody);
        btnRoutine = findViewById(R.id.btnRoutine);
        btnFood = findViewById(R.id.btnFood);
        btnCulture = findViewById(R.id.btnCulture);


        btnVoltar = findViewById(R.id.btn_voltar_ingles);
        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(ingles.this, Estudos.class);
            startActivity(intent);
            finish();
        });

        btnGreetings.setOnClickListener(v -> toggleSection(txtGreetings));
        btnColorsNumbers.setOnClickListener(v -> toggleSection(txtColorsNumbers));
        btnFamily.setOnClickListener(v -> toggleSection(txtFamily));
        btnBody.setOnClickListener(v -> toggleSection(txtBody));
        btnRoutine.setOnClickListener(v -> toggleSection(txtRoutine));
        btnFood.setOnClickListener(v -> toggleSection(txtFood));
        btnCulture.setOnClickListener(v -> toggleSection(txtCulture));
    }

    private void toggleSection(TextView targetView) {
        TextView[] allSections = {txtGreetings, txtColorsNumbers, txtFamily, txtBody, txtRoutine, txtFood, txtCulture};


        boolean isVisible = targetView.getVisibility() == View.VISIBLE;


        for (TextView view : allSections) {
            view.setVisibility(View.GONE);
        }


        if (!isVisible) {
            targetView.setVisibility(View.VISIBLE);
        }
    }

    private void processarConteudoHtml(TextView textView) {
        String rawText = textView.getText().toString();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(rawText, Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml(rawText));
        }
    }
}