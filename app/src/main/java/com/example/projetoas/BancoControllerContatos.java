package com.example.projetoas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BancoControllerContatos {

    private SQLiteDatabase db;
    private CriaBanco bancoHelper;

    public BancoControllerContatos(Context context) {
        bancoHelper = new CriaBanco(context);
        try {
            db = bancoHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            Log.e("DB_ERROR", "Erro ao obter banco de dados gravável", e);
            e.printStackTrace();
        }
    }

    public long inserirUsuario(String nome, String email, String cpf, String telefone, String genero, String senha) {
        ContentValues valores = new ContentValues();
        valores.put(CriaBanco.COL_USUARIO_NOME, nome);
        valores.put(CriaBanco.COL_USUARIO_EMAIL, email);
        valores.put(CriaBanco.COL_USUARIO_CPF, cpf);
        valores.put(CriaBanco.COL_USUARIO_TELEFONE, telefone);
        valores.put(CriaBanco.COL_USUARIO_GENERO, genero);
        valores.put(CriaBanco.COL_USUARIO_SENHA, senha);
        return db.insert(CriaBanco.TABELA_USUARIOS, null, valores);
    }

    public long loginUsuario(String identificador, String senha) {
        long idUsuario = -1;
        Cursor cursor = null;

        String[] args = {identificador, senha};
        String[] colunasId = {CriaBanco.COL_USUARIO_ID};

        String whereEmail = CriaBanco.COL_USUARIO_EMAIL + " = ? AND " + CriaBanco.COL_USUARIO_SENHA + " = ?";
        cursor = db.query(CriaBanco.TABELA_USUARIOS, colunasId, whereEmail, args, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            idUsuario = cursor.getLong(cursor.getColumnIndexOrThrow(CriaBanco.COL_USUARIO_ID));
        }

        if (idUsuario == -1) {
            if (cursor != null) {
                cursor.close();
            }

            String whereCpf = CriaBanco.COL_USUARIO_CPF + " = ? AND " + CriaBanco.COL_USUARIO_SENHA + " = ?";
            cursor = db.query(CriaBanco.TABELA_USUARIOS, colunasId, whereCpf, args, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                idUsuario = cursor.getLong(cursor.getColumnIndexOrThrow(CriaBanco.COL_USUARIO_ID));
            }
        }

        if (cursor != null) {
            cursor.close();
        }
        return idUsuario;
    }


    public String verificarLogin(String identificador, String senha) {
        String nomeUsuario = null;
        Cursor cursor = null;

        String[] args = {identificador, senha};
        String[] colunasNome = {CriaBanco.COL_USUARIO_NOME};

        String whereEmail = CriaBanco.COL_USUARIO_EMAIL + " = ? AND " + CriaBanco.COL_USUARIO_SENHA + " = ?";
        cursor = db.query(CriaBanco.TABELA_USUARIOS, colunasNome, whereEmail, args, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            nomeUsuario = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.COL_USUARIO_NOME));
        }

        if (nomeUsuario == null) {
            if (cursor != null) {
                cursor.close();
            }
            String whereCpf = CriaBanco.COL_USUARIO_CPF + " = ? AND " + CriaBanco.COL_USUARIO_SENHA + " = ?";
            cursor = db.query(CriaBanco.TABELA_USUARIOS, colunasNome, whereCpf, args, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                nomeUsuario = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.COL_USUARIO_NOME));
            }
        }

        if (cursor != null) {
            cursor.close();
        }
        return nomeUsuario;
    }

    public Cursor carregarUsuarioPorId(long usuarioId) {
        String[] colunas = {
                CriaBanco.COL_USUARIO_NOME,
                CriaBanco.COL_USUARIO_EMAIL,
                CriaBanco.COL_USUARIO_CPF,
                CriaBanco.COL_USUARIO_TELEFONE
        };
        String where = CriaBanco.COL_USUARIO_ID + " = " + usuarioId;
        return db.query(CriaBanco.TABELA_USUARIOS, colunas, where, null, null, null, null);
    }

    public boolean atualizarUsuario(long usuarioId, String novoNome, String novoEmail, String novoTelefone) {
        ContentValues valores = new ContentValues();
        valores.put(CriaBanco.COL_USUARIO_NOME, novoNome);
        valores.put(CriaBanco.COL_USUARIO_EMAIL, novoEmail);
        valores.put(CriaBanco.COL_USUARIO_TELEFONE, novoTelefone);
        String where = CriaBanco.COL_USUARIO_ID + " = " + usuarioId;

        int linhasAfetadas = db.update(CriaBanco.TABELA_USUARIOS, valores, where, null);
        return linhasAfetadas > 0;
    }

    public boolean excluirUsuario(long usuarioId) {
        db.delete(CriaBanco.TABELA_RESPONSAVEIS, CriaBanco.COL_RESP_USUARIO_ID_FK + " = " + usuarioId, null);
        int linhasAfetadas = db.delete(CriaBanco.TABELA_USUARIOS, CriaBanco.COL_USUARIO_ID + " = " + usuarioId, null);
        return linhasAfetadas > 0;
    }

    public long getPrimeiroResponsavelIdPorUsuario(long usuarioId) {
        long responsavelId = -1;
        Cursor cursor = null;

        String[] colunas = {CriaBanco.COL_RESP_ID};
        String where = CriaBanco.COL_RESP_USUARIO_ID_FK + " = ?";
        String[] args = {String.valueOf(usuarioId)};

        try {
            cursor = db.query(CriaBanco.TABELA_RESPONSAVEIS, colunas, where, args, null, null, CriaBanco.COL_RESP_ID + " ASC", "1");

            if (cursor != null && cursor.moveToFirst()) {
                responsavelId = cursor.getLong(cursor.getColumnIndexOrThrow(CriaBanco.COL_RESP_ID));
            }
        } catch (Exception e) {
            Log.e("DB_ERROR", "Erro ao buscar ID do Responsável: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return responsavelId;
    }


    public long inserirResponsavel(long usuarioId, String nome, String email, String cpf, String telefone, String senha, String parentesco) {
        ContentValues valores = new ContentValues();
        valores.put(CriaBanco.COL_RESP_USUARIO_ID_FK, usuarioId);
        valores.put(CriaBanco.COL_RESP_NOME, nome);
        valores.put(CriaBanco.COL_RESP_EMAIL, email);
        valores.put(CriaBanco.COL_RESP_CPF, cpf);
        valores.put(CriaBanco.COL_RESP_TELEFONE, telefone);
        valores.put(CriaBanco.COL_RESP_SENHA, senha);
        valores.put(CriaBanco.COL_RESP_PARENTESCO, parentesco);

        return db.insert(CriaBanco.TABELA_RESPONSAVEIS, null, valores);
    }

    public Cursor carregarResponsavelPorId(long responsavelId) {
        String[] colunas = {
                CriaBanco.COL_RESP_NOME,
                CriaBanco.COL_RESP_EMAIL,
                CriaBanco.COL_RESP_CPF,
                CriaBanco.COL_RESP_TELEFONE
        };
        String where = CriaBanco.COL_RESP_ID + " = " + responsavelId;
        return db.query(CriaBanco.TABELA_RESPONSAVEIS, colunas, where, null, null, null, null);
    }

    public boolean atualizarResponsavel(long responsavelId, String novoNome, String novoEmail, String novoTelefone) {
        ContentValues valores = new ContentValues();
        valores.put(CriaBanco.COL_RESP_NOME, novoNome);
        valores.put(CriaBanco.COL_RESP_EMAIL, novoEmail);
        valores.put(CriaBanco.COL_RESP_TELEFONE, novoTelefone);
        String where = CriaBanco.COL_RESP_ID + " = " + responsavelId;

        int linhasAfetadas = db.update(CriaBanco.TABELA_RESPONSAVEIS, valores, where, null);
        return linhasAfetadas > 0;
    }

    public boolean verificarSenhaResponsavel(long responsavelId, String senhaAtual) {
        String[] colunas = {CriaBanco.COL_RESP_ID};
        String where = CriaBanco.COL_RESP_ID + " = ? AND " + CriaBanco.COL_RESP_SENHA + " = ?";
        String[] args = {String.valueOf(responsavelId), senhaAtual};

        Cursor cursor = db.query(CriaBanco.TABELA_RESPONSAVEIS, colunas, where, args, null, null, null);
        boolean senhaCorreta = cursor.moveToFirst();
        cursor.close();
        return senhaCorreta;
    }

    public boolean atualizarSenhaResponsavel(long responsavelId, String novaSenha) {
        ContentValues valores = new ContentValues();
        valores.put(CriaBanco.COL_RESP_SENHA, novaSenha);
        String where = CriaBanco.COL_RESP_ID + " = " + responsavelId;

        int linhasAfetadas = db.update(CriaBanco.TABELA_RESPONSAVEIS, valores, where, null);
        return linhasAfetadas > 0;
    }

    public long inserirAcao(String titulo, String descricao) {
        ContentValues valores = new ContentValues();
        valores.put(CriaBanco.COL_ACOES_TITULO, titulo);
        valores.put(CriaBanco.COL_ACOES_DESCRICAO, descricao);
        return db.insert(CriaBanco.TABELA_ACOES, null, valores);
    }

    public List<acoes> listarAcoes() {
        List<acoes> lista = new ArrayList<>();
        String[] colunas = {CriaBanco.COL_ACOES_TITULO, CriaBanco.COL_ACOES_DESCRICAO};
        Cursor cursor = db.query(CriaBanco.TABELA_ACOES, colunas, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String titulo = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.COL_ACOES_TITULO));
                String descricao = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.COL_ACOES_DESCRICAO));

            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return lista;
    }
}