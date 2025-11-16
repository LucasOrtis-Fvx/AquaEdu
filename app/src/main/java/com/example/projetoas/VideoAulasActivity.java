package com.example.projetoas;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import androidx.core.view.GravityCompat;
import com.google.android.material.button.MaterialButton; // Importação necessária


public class VideoAulasActivity extends AppCompatActivity {


    private DrawerLayout drawerLayout;
    private ListView listMaterias;
    private LinearLayout containerVideos;
    private MaterialButton btnVoltarEstudos;


    private final String[] materias = {
            "Matemática", "Português", "Ciências", "Arte",
            "Inglês", "História", "Geografia"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoaula);


        drawerLayout = findViewById(R.id.drawer_layout);
        listMaterias = findViewById(R.id.list_materias);
        containerVideos = findViewById(R.id.container_videos);
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        btnVoltarEstudos = findViewById(R.id.btn_voltar_estudos);


        topAppBar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));


        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, materias);
        listMaterias.setAdapter(adapter);


        listMaterias.setOnItemClickListener((parent, view, position, id) -> {
            String materia = materias[position];
            carregarVideos(materia);
            drawerLayout.closeDrawers();
        });


        btnVoltarEstudos.setOnClickListener(v -> {
            Intent intent = new Intent(VideoAulasActivity.this, Estudos.class);
            startActivity(intent);
            finish();
        });
    }


    private void carregarVideos(String materia) {
        containerVideos.removeAllViews();


        TextView titulo = new TextView(this);
        titulo.setText("📘 Vídeos de " + materia);
        titulo.setTextSize(18);
        titulo.setTextColor(getResources().getColor(android.R.color.black));
        titulo.setPadding(0, 20, 0, 20);
        containerVideos.addView(titulo);


        String[][] videos = {
                // Conteúdo de vídeo mantido. Apenas as matérias válidas serão usadas.
                {"Matemática", "https://youtu.be/TBnIizv7leo?si=fXHHk9nrR2use-Vq", "https://youtu.be/4OTuvoFvAC0?si=mxHhjeTJ0RTIN-Xu", "https://youtu.be/Y_jh-Ugpv30?si=WOi1nVXyro5g0inE"},
                {"História", "https://youtu.be/rQMY-Ib-t8s?si=gKWlkBNdvjuN8Vx_", "https://youtu.be/QQtYVp--QXo?si=DtNgaTfxVomtAN7i",},
                {"Português", "https://youtu.be/HsS8LwkYXVM?si=l__Fdflr0sZr-ewp", "https://youtu.be/IKb7NSDN8Og?si=MwtmSvVvTNzsktrF", "https://youtu.be/7T7DyQe8qbo?si=vH0nqymH26mm7wXd", "https://youtu.be/I8Kt8SS_la0?si=p4hxMh0jc91Ml0NX"},
                {"Ciências", "https://youtu.be/3ad2smk2vzU?si=LvSe5raPNMJO1ue6", "https://youtu.be/bdgYTLW4ec4?si=EwJLoLGe8W88e9cf", "https://youtu.be/W6oWScsFJj4?si=m30DSN7gk73HL0O3"},
                {"Inglês", "https://youtu.be/msWeSPKv8nA?si=OD3E1fImMysmkOlo", "https://youtu.be/4YpHh3e0h7U?si=adg1jP1FLuoRoVsV", "https://youtu.be/kCka94jeGTk?si=M6-ZQ4lOpZXx0V6Z", "https://youtu.be/lW5TXrKbsq4?si=m6Zo0ejEeH33y4a1"},
                {"Geografia", "https://youtu.be/59-lvi2pj9c?si=Eg0T0A-BLndqA1Up", "https://youtu.be/nJG96NltMHk?si=1fj2dNwxPPeB6uuO", "https://youtu.be/QM_y9T6sP6E?si=YCSRTiLco44mu6P-", "https://youtu.be/uu8A9wVMWLI?si=2LfD4i9-nB1M-Wih"},
                {"Arte", "https://youtu.be/ylsxCYGqfEs?si=RUhvR4LRP8AMMChg", }
        };


        for (String[] video : videos) {
            if (video[0].equals(materia)) {
                for (int i = 1; i < video.length; i++) {
                    adicionarVideoCard(materia + " - Aula " + (i), video[i]);
                }
                return;
            }
        }
        TextView aviso = new TextView(this);
        aviso.setText("Sem vídeos disponíveis para " + materia + ".");
        aviso.setPadding(0, 40, 0, 40);
        containerVideos.addView(aviso);
    }


    private void adicionarVideoCard(String titulo, String link) {
        MaterialCardView card = new MaterialCardView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 16, 0, 16);
        card.setLayoutParams(params);
        card.setCardElevation(8);
        card.setRadius(16);
        card.setStrokeWidth(2);
        card.setStrokeColor(getResources().getColor(R.color.purple_500));


        card.setCardBackgroundColor(getResources().getColor(android.R.color.white));


        TextView text = new TextView(this);
        text.setText("▶ " + titulo + " — assistir");
        text.setTextSize(16);
        text.setTextColor(getResources().getColor(R.color.purple_700));
        text.setPadding(32, 32, 32, 32);


        card.addView(text);
        card.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            startActivity(intent);
        });


        containerVideos.addView(card);
    }
}