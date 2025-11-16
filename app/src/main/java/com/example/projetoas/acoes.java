package com.example.projetoas;


public class acoes {


    private String titulo;
    private String descricao;
    private String emojiIcon;


    public acoes (String titulo, String descricao, String emojiIcon) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.emojiIcon = emojiIcon;
    }


    public String getTitulo() {
        return titulo;
    }


    public String getDescricao() {
        return descricao;
    }


    public String getEmojiIcon() {
        return emojiIcon;
    }
}