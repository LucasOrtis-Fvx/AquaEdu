package com.example.projetoas;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class AcaoAdapter extends RecyclerView.Adapter<AcaoAdapter.AcaoViewHolder> {


    private List<acoes> listaAcoes;
    private Context context;
    private OnAcaoListener onAcaoListener;


    // Interface para lidar com cliques no botão "Saber mais"
    public interface OnAcaoListener {
        void onSaberMaisClick(acoes acao);
    }


    public AcaoAdapter(List<acoes> listaAcoes, Context context, OnAcaoListener onAcaoListener) {
        this.listaAcoes = listaAcoes;
        this.context = context;
        this.onAcaoListener = onAcaoListener;
    }


    @NonNull
    @Override
    public AcaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_acao, parent, false);
        return new AcaoViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AcaoViewHolder holder, int position) {
        acoes acao = listaAcoes.get(position);


        holder.tvEmojiIcon.setText(acao.getEmojiIcon());
        holder.tvTituloAcao.setText(acao.getTitulo());
        holder.tvDescricaoAcao.setText(acao.getDescricao());


        holder.btnSaberMais.setOnClickListener(v -> {
            onAcaoListener.onSaberMaisClick(acao);
        });
    }


    @Override
    public int getItemCount() {
        return listaAcoes.size();
    }


    public static class AcaoViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmojiIcon;
        TextView tvTituloAcao;
        TextView tvDescricaoAcao;
        Button btnSaberMais;


        public AcaoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmojiIcon = itemView.findViewById(R.id.tvEmojiIcon);
            tvTituloAcao = itemView.findViewById(R.id.tvTituloAcao);
            tvDescricaoAcao = itemView.findViewById(R.id.tvDescricaoAcao);
            btnSaberMais = itemView.findViewById(R.id.btnSaberMais);
        }
    }
}
