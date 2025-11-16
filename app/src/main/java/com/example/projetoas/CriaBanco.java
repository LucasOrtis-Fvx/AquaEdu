package com.example.projetoas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CriaBanco extends SQLiteOpenHelper {

    // 1. Constantes do Banco
    private static final String NOME_BANCO = "aquaedu.db";
    // ATENÇÃO: Mudei a versão para 3, pois alteramos as tabelas
    private static final int VERSAO_BANCO = 3;

    // 2. Constantes da Tabela USUARIOS (Aluno)
    public static final String TABELA_USUARIOS = "usuarios";
    public static final String COL_USUARIO_ID = "_id";
    public static final String COL_USUARIO_NOME = "nome_usuario";
    public static final String COL_USUARIO_EMAIL = "email_usuario";
    public static final String COL_USUARIO_SENHA = "senha_usuario";
    public static final String COL_USUARIO_CPF = "cpf_usuario";
    public static final String COL_USUARIO_TELEFONE = "telefone_usuario";
    public static final String COL_USUARIO_GENERO = "genero_usuario";

    // 3. Constantes da Tabela RESPONSAVEIS
    public static final String TABELA_RESPONSAVEIS = "responsaveis";
    public static final String COL_RESP_ID = "_id";
    public static final String COL_RESP_NOME = "nome_responsavel";
    public static final String COL_RESP_EMAIL = "email_responsavel";
    public static final String COL_RESP_TELEFONE = "telefone_responsavel";
    public static final String COL_RESP_CPF = "cpf_responsavel";
    public static final String COL_RESP_SENHA = "senha_responsavel";
    public static final String COL_RESP_PARENTESCO = "parentesco_responsavel";
    public static final String COL_RESP_USUARIO_ID_FK = "usuario_id"; // Chave para ligar ao aluno

    // 4. Constantes da Tabela ACOES
    public static final String TABELA_ACOES = "acoes";
    public static final String COL_ACOES_ID = "_id";
    public static final String COL_ACOES_TITULO = "titulo_acao";
    public static final String COL_ACOES_DESCRICAO = "descricao_acao";

    // Construtor
    public CriaBanco(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabela USUARIOS
        String sqlCriaTabelaUsuarios = "CREATE TABLE " + TABELA_USUARIOS + " ("
                + COL_USUARIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_USUARIO_NOME + " TEXT NOT NULL, "
                + COL_USUARIO_EMAIL + " TEXT NOT NULL UNIQUE, "
                + COL_USUARIO_CPF + " TEXT NOT NULL UNIQUE, " // CPF do aluno
                + COL_USUARIO_TELEFONE + " TEXT, "
                + COL_USUARIO_GENERO + " TEXT, "
                + COL_USUARIO_SENHA + " TEXT NOT NULL"
                + ")";
        db.execSQL(sqlCriaTabelaUsuarios);

        // Tabela RESPONSAVEIS
        String sqlCriaTabelaResponsaveis = "CREATE TABLE " + TABELA_RESPONSAVEIS + " ("
                + COL_RESP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_RESP_NOME + " TEXT NOT NULL, "
                + COL_RESP_EMAIL + " TEXT NOT NULL, "
                + COL_RESP_TELEFONE + " TEXT, "
                + COL_RESP_CPF + " TEXT NOT NULL UNIQUE, " // CPF do responsável
                + COL_RESP_SENHA + " TEXT NOT NULL, "
                + COL_RESP_PARENTESCO + " TEXT, "
                + COL_RESP_USUARIO_ID_FK + " INTEGER, " // ID do aluno
                + "FOREIGN KEY(" + COL_RESP_USUARIO_ID_FK + ") REFERENCES "
                + TABELA_USUARIOS + "(" + COL_USUARIO_ID + ")"
                + ")";
        db.execSQL(sqlCriaTabelaResponsaveis);

        // Tabela ACOES
        String sqlCriaTabelaAcoes = "CREATE TABLE " + TABELA_ACOES + " ("
                + COL_ACOES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ACOES_TITULO + " TEXT NOT NULL, "
                + COL_ACOES_DESCRICAO + " TEXT"
                + ")";
        db.execSQL(sqlCriaTabelaAcoes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Política de atualização simples: apaga tudo e recria
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_ACOES);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_RESPONSAVEIS);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_USUARIOS);
        onCreate(db);
    }
}