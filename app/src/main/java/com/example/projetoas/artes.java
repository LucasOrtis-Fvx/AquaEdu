package com.example.projetoas;


import android.content.Intent;
import android.os.Bundle;
import android.text.Html; // Importação NECESSÁRIA
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class artes extends AppCompatActivity {


    private ImageButton btnVoltar;




    private TextView txtOQueEhArte, txtLinguagens, txtElementos, txtCores, txtHistoria, txtArtistas, txtSustentabilidade;

    private Button btnOQueEhArte, btnLinguagens, btnElementos, btnCores, btnHistoria, btnArtistas, btnSustentabilidade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artes);

        txtOQueEhArte = findViewById(R.id.txtOQueEhArte);
        txtLinguagens = findViewById(R.id.txtLinguagens);
        txtElementos = findViewById(R.id.txtElementos);
        txtCores = findViewById(R.id.txtCores);
        txtHistoria = findViewById(R.id.txtHistoria);
        txtArtistas = findViewById(R.id.txtArtistas);
        txtSustentabilidade = findViewById(R.id.txtSustentabilidade);

        processarConteudoHtml(txtOQueEhArte);
        processarConteudoHtml(txtLinguagens);
        processarConteudoHtml(txtElementos);
        processarConteudoHtml(txtCores);
        processarConteudoHtml(txtHistoria);
        processarConteudoHtml(txtArtistas);
        processarConteudoHtml(txtSustentabilidade);

        btnOQueEhArte = findViewById(R.id.btnOQueEhArte);
        btnLinguagens = findViewById(R.id.btnLinguagens);
        btnElementos = findViewById(R.id.btnElementos);
        btnCores = findViewById(R.id.btnCores);
        btnHistoria = findViewById(R.id.btnHistoria);
        btnArtistas = findViewById(R.id.btnArtistas);
        btnSustentabilidade = findViewById(R.id.btnSustentabilidade);


        btnVoltar = findViewById(R.id.btn_voltar_artes);
        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(artes.this, Estudos.class);
            startActivity(intent);
            finish();
        });

        btnOQueEhArte.setOnClickListener(v -> toggleSection(txtOQueEhArte));
        btnLinguagens.setOnClickListener(v -> toggleSection(txtLinguagens));
        btnElementos.setOnClickListener(v -> toggleSection(txtElementos));
        btnCores.setOnClickListener(v -> toggleSection(txtCores));
        btnHistoria.setOnClickListener(v -> toggleSection(txtHistoria));
        btnArtistas.setOnClickListener(v -> toggleSection(txtArtistas));
        btnSustentabilidade.setOnClickListener(v -> toggleSection(txtSustentabilidade));
    }

    private void toggleSection(TextView targetView) {


        TextView[] allSections = {txtOQueEhArte, txtLinguagens, txtElementos, txtCores, txtHistoria, txtArtistas, txtSustentabilidade};




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