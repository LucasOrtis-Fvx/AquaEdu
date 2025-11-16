package com.example.projetoas;


import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class historia extends AppCompatActivity {


    private ImageButton btnVoltar;



    private TextView txtOQueEhHistoria, txtEras, txtFormacaoBrasil, txtImperioRepublica, txtBrasilContemporaneo, txtCultura, txtSustentabilidade;

    private Button btnOQueEhHistoria, btnEras, btnFormacaoBrasil, btnImperioRepublica, btnBrasilContemporaneo, btnCultura, btnSustentabilidade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia);

        txtOQueEhHistoria = findViewById(R.id.txtOQueEhHistoria);
        txtEras = findViewById(R.id.txtEras);
        txtFormacaoBrasil = findViewById(R.id.txtFormacaoBrasil);
        txtImperioRepublica = findViewById(R.id.txtImperioRepublica);
        txtBrasilContemporaneo = findViewById(R.id.txtBrasilContemporaneo);
        txtCultura = findViewById(R.id.txtCultura);
        txtSustentabilidade = findViewById(R.id.txtSustentabilidade);

        processarConteudoHtml(txtOQueEhHistoria);
        processarConteudoHtml(txtEras);
        processarConteudoHtml(txtFormacaoBrasil);
        processarConteudoHtml(txtImperioRepublica);
        processarConteudoHtml(txtBrasilContemporaneo);
        processarConteudoHtml(txtCultura);
        processarConteudoHtml(txtSustentabilidade);

        btnOQueEhHistoria = findViewById(R.id.btnOQueEhHistoria);
        btnEras = findViewById(R.id.btnEras);
        btnFormacaoBrasil = findViewById(R.id.btnFormacaoBrasil);
        btnImperioRepublica = findViewById(R.id.btnImperioRepublica);
        btnBrasilContemporaneo = findViewById(R.id.btnBrasilContemporaneo);
        btnCultura = findViewById(R.id.btnCultura);
        btnSustentabilidade = findViewById(R.id.btnSustentabilidade);

        btnVoltar = findViewById(R.id.btn_voltar_historia);
        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(historia.this, Estudos.class);
            startActivity(intent);
            finish();
        });

        btnOQueEhHistoria.setOnClickListener(v -> toggleSection(txtOQueEhHistoria));
        btnEras.setOnClickListener(v -> toggleSection(txtEras));
        btnFormacaoBrasil.setOnClickListener(v -> toggleSection(txtFormacaoBrasil));
        btnImperioRepublica.setOnClickListener(v -> toggleSection(txtImperioRepublica));
        btnBrasilContemporaneo.setOnClickListener(v -> toggleSection(txtBrasilContemporaneo));
        btnCultura.setOnClickListener(v -> toggleSection(txtCultura));
        btnSustentabilidade.setOnClickListener(v -> toggleSection(txtSustentabilidade));
    }

    private void toggleSection(TextView targetView) {
        TextView[] allSections = {txtOQueEhHistoria, txtEras, txtFormacaoBrasil, txtImperioRepublica, txtBrasilContemporaneo, txtCultura, txtSustentabilidade};

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