package com.example.projetoas;


import android.content.Intent;
import android.os.Bundle;
import android.text.Html; // Importação NECESSÁRIA
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


public class ciencias extends AppCompatActivity {


    private ImageButton btnVoltar;
    // TextViews para cada seção de conteúdo
    private TextView txtCorpoHumano, txtAgua, txtMateria, txtEnergia, txtEcossistemas, txtCuriosidades;
    // Buttons para acionar as seções
    private Button btnCorpoHumano, btnAgua, btnMateria, btnEnergia, btnEcossistemas, btnVerCuriosidades;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Garanta que o nome do arquivo XML está correto
        setContentView(R.layout.activity_ciencias);


        // 1. Inicializa TextViews
        txtCorpoHumano = findViewById(R.id.txtCorpoHumano);
        txtAgua = findViewById(R.id.txtAgua);
        txtMateria = findViewById(R.id.txtMateria);
        txtEnergia = findViewById(R.id.txtEnergia);
        txtEcossistemas = findViewById(R.id.txtEcossistemas);
        txtCuriosidades = findViewById(R.id.txtCuriosidades);


        // NOVO: Processar HTML em todas as TextViews de conteúdo
        processarConteudoHtml(txtCorpoHumano);
        processarConteudoHtml(txtAgua);
        processarConteudoHtml(txtMateria);
        processarConteudoHtml(txtEnergia);
        processarConteudoHtml(txtEcossistemas);
        processarConteudoHtml(txtCuriosidades);




        // 2. Inicializa Buttons
        btnCorpoHumano = findViewById(R.id.btnCorpoHumano);
        btnAgua = findViewById(R.id.btnAgua);
        btnMateria = findViewById(R.id.btnMateria);
        btnEnergia = findViewById(R.id.btnEnergia);
        btnEcossistemas = findViewById(R.id.btnEcossistemas);
        btnVerCuriosidades = findViewById(R.id.btnCuriosidades); // Renomeado para melhor clareza no código


        // 3. Configura o botão de Voltar
        btnVoltar = findViewById(R.id.btn_voltar_ciencia);
        btnVoltar.setOnClickListener(v -> {
            // Assumindo que você quer voltar para uma Activity chamada 'Estudos'
            Intent intent = new Intent(ciencias.this, Estudos.class);
            startActivity(intent);
            finish();
        });


        // 4. Configura os Listeners para os botões de conteúdo
        btnCorpoHumano.setOnClickListener(v -> toggleSection(txtCorpoHumano));
        btnAgua.setOnClickListener(v -> toggleSection(txtAgua));
        btnMateria.setOnClickListener(v -> toggleSection(txtMateria));
        btnEnergia.setOnClickListener(v -> toggleSection(txtEnergia));
        btnEcossistemas.setOnClickListener(v -> toggleSection(txtEcossistemas));
        btnVerCuriosidades.setOnClickListener(v -> toggleSection(txtCuriosidades));
    }


    /**
     * Alterna a visibilidade da TextView alvo (mostrar/esconder) e esconde todas as outras.
     */
    private void toggleSection(TextView targetView) {
        // Lista de todas as TextViews de conteúdo
        TextView[] allSections = {txtCorpoHumano, txtAgua, txtMateria, txtEnergia, txtEcossistemas, txtCuriosidades};


        // Verifica o estado atual da TextView alvo
        boolean isVisible = targetView.getVisibility() == View.VISIBLE;


        // Esconde todas as TextViews
        for (TextView view : allSections) {
            view.setVisibility(View.GONE);
        }


        // Se a TextView alvo NÃO estava visível (ou seja, foi escondida acima), mostra-a.
        if (!isVisible) {
            targetView.setVisibility(View.VISIBLE);
        }
    }


    /**
     * Pega o texto bruto do TextView (com tags <b>) e o renderiza como HTML.
     */
    private void processarConteudoHtml(TextView textView) {
        String rawText = textView.getText().toString();
        // Usando fromHtml() para renderizar o negrito (<b>)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(rawText, Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml(rawText));
        }
    }
}