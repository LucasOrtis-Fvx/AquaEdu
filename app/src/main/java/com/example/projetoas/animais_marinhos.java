package com.example.projetoas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class animais_marinhos extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AnimalAdapter adapter;
    private List<Animal> listaCompleta = new ArrayList<>();
    private Button btnNaoExtincao, btnExtincao;
    private ImageButton btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animais_marinhos);

        recyclerView = findViewById(R.id.recyclerAnimais);
        btnNaoExtincao = findViewById(R.id.btnNaoExtincao);
        btnExtincao = findViewById(R.id.btnExtincao);
        btnVoltar = findViewById(R.id.btnVoltar);

        btnVoltar.setOnClickListener(v -> {
            finish();
        });

        carregarDados();

        adapter = new AnimalAdapter(this, filtrarLista(false));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        configurarBotoes();
    }

    private void configurarBotoes() {
        btnNaoExtincao.setOnClickListener(v -> {
            adapter.atualizarLista(filtrarLista(false));
            estilizarBotoes(btnNaoExtincao, btnExtincao, "#00ACC1");
        });

        btnExtincao.setOnClickListener(v -> {
            adapter.atualizarLista(filtrarLista(true));
            estilizarBotoes(btnExtincao, btnNaoExtincao, "#D32F2F");
        });

        // Estilo inicial: "Geral" ativo
        estilizarBotoes(btnNaoExtincao, btnExtincao, "#00ACC1");
    }

    private void estilizarBotoes(Button ativo, Button inativo, String corAtivaHex) {
        ativo.setBackgroundColor(Color.parseColor(corAtivaHex));
        ativo.setTextColor(Color.WHITE);
        inativo.setBackgroundColor(Color.parseColor("#ECEFF1"));
        inativo.setTextColor(Color.parseColor("#455A64"));
    }

    private List<Animal> filtrarLista(boolean mostrarApenasExtincao) {
        List<Animal> filtrados = new ArrayList<>();
        for (Animal a : listaCompleta) {
            if (a.emExtincao == mostrarApenasExtincao) {
                filtrados.add(a);
            }
        }
        return filtrados;
    }

    private void carregarDados() {
        listaCompleta.add(new Animal("Baleia-Azul", "O maior animal que já existiu, em perigo crítico.", true, "🐋"));
        listaCompleta.add(new Animal("Vaquita", "O cetáceo mais ameaçado do mundo.", true, "🐬"));
        listaCompleta.add(new Animal("Foca-monge", "Só vive no Havaí, corre risco de extinção.", true, "🦭"));
        listaCompleta.add(new Animal("Tartaruga-Verde", "Ameaçada pela poluição plástica.", true, "🐢"));
        listaCompleta.add(new Animal("Tubarão-Martelo", "Caçado ilegalmente por suas barbatanas.", true, "🦈"));
        listaCompleta.add(new Animal("Lontra-marinha", "Mamífero essencial para o equilíbrio.", true, "🦦"));
        listaCompleta.add(new Animal("Coral", "Ameaçados pelo aquecimento global.", true, "🪸"));
        listaCompleta.add(new Animal("Iguana Marinha", "Único lagarto que nada no mar.", true, "🦎"));

        listaCompleta.add(new Animal("Peixe-Palhaço", "Vive protegido entre anêmonas.", false, "🐠"));
        listaCompleta.add(new Animal("Cirurgião-patela", "Peixe de cor azul vibrante (Dory).", false, "🐟"));
        listaCompleta.add(new Animal("Baiacu", "Infla o corpo como defesa.", false, "🐡"));
        listaCompleta.add(new Animal("Polvo", "Mestre dos disfarces e inteligência.", false, "🐙"));
        listaCompleta.add(new Animal("Lula", "Solta tinta para fugir.", false, "🦑"));
        listaCompleta.add(new Animal("Água-viva", "Corpo gelatinoso, pode ter veneno.", false, "🪼"));
        listaCompleta.add(new Animal("Caracol Marinho", "Protegido por uma concha dura.", false, "🐚"));
        listaCompleta.add(new Animal("Caranguejo", "Caminha de lado e tem pinças.", false, "🦀"));
        listaCompleta.add(new Animal("Lagosta", "Vive em tocas e fendas rochosas.", false, "🦞"));
        listaCompleta.add(new Animal("Camarão", "Base da alimentação de muitos animais.", false, "🦐"));
    }

    public static class Animal {
        private String nome, descricao, emoji;
        private boolean emExtincao;

        public Animal(String nome, String descricao, boolean emExtincao, String emoji) {
            this.nome = nome; this.descricao = descricao; this.emExtincao = emExtincao; this.emoji = emoji;
        }
        public String getNome() { return nome; }
        public String getDescricao() { return descricao; }
        public boolean isEmExtincao() { return emExtincao; }
        public String getEmoji() { return emoji; }
    }

    public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.ViewHolder> {
        private Context context;
        private List<Animal> lista;

        public AnimalAdapter(Context context, List<Animal> lista) {
            this.context = context; this.lista = lista;
        }

        public void atualizarLista(List<Animal> novaLista) {
            this.lista = novaLista; notifyDataSetChanged();
        }

        @NonNull @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_animal, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Animal animal = lista.get(position);
            holder.txtNome.setText(animal.getNome());
            holder.txtDescricao.setText(animal.getDescricao());
            holder.txtEmoji.setText(animal.getEmoji());

            if (animal.isEmExtincao()) {
                holder.txtStatus.setText("CRÍTICO ⚠️");
                holder.txtStatus.setTextColor(Color.parseColor("#D32F2F"));
                holder.txtStatus.setBackgroundColor(Color.parseColor("#FFEBEE"));
                holder.cardEmojiBg.setCardBackgroundColor(Color.parseColor("#FFEBEE"));
            } else {
                holder.txtStatus.setText("ESTÁVEL ✅");
                holder.txtStatus.setTextColor(Color.parseColor("#388E3C"));
                holder.txtStatus.setBackgroundColor(Color.parseColor("#E8F5E9"));
                holder.cardEmojiBg.setCardBackgroundColor(Color.parseColor("#E0F7FA"));
            }

            holder.itemView.setOnClickListener(v ->
                    Toast.makeText(context, "Você selecionou: " + animal.getNome(), Toast.LENGTH_SHORT).show()
            );
        }

        @Override public int getItemCount() { return lista.size(); }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView txtNome, txtStatus, txtDescricao, txtEmoji;
            CardView cardEmojiBg;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtNome = itemView.findViewById(R.id.txtNome);
                txtStatus = itemView.findViewById(R.id.txtStatus);
                txtDescricao = itemView.findViewById(R.id.txtDescricao);
                txtEmoji = itemView.findViewById(R.id.txtEmoji);
                cardEmojiBg = itemView.findViewById(R.id.emojiContainer);
            }
        }
    }
}