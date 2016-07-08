package com.example.emerson.agenda2.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.widget.Switch;

import com.example.emerson.agenda2.modelo.Aluno;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emerson on 28/05/16.
 */
public class AlunoDAO extends SQLiteOpenHelper {
    public AlunoDAO(Context context)   {
        super(context, "Agenda", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Alunos (id INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, endereco TEXT, " +
                "telefone TEXT, " +
                "site TEXT, " +
                "nota REAL, caminhoFoto TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql ="";

        switch(oldVersion){
            case 1:
                sql = "Alter Table Alunos ADD COLUMN caminhoFoto TEXT ;";
                db.execSQL(sql);


        }





    }

    public void insere(Aluno aluno) {
    SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosDoAluno(aluno);
        db.insert("Alunos",null,dados);

    }

    @NonNull
    private ContentValues pegaDadosDoAluno(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        dados.put("caminhoFoto", aluno.getCaminhoFoto());
        return dados;
    }

    public List<Aluno> buscaAlunos() {
       String sql = "Select * From Alunos;";
       SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql,null);
        List<Aluno> lista = new ArrayList<Aluno>();
        while( c.moveToNext()){
            Aluno aluno = new Aluno();
            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));
            lista.add(aluno);
        }
        c.close();

        return  lista;
    }

    public void deleta(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {aluno.getId().toString()};
        db.delete("Alunos","id = ?",params);

    }

    public void altera(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosDoAluno(aluno);
        String [] params = {aluno.getId().toString()};
        db.update("Alunos",dados,"id = ?",params);



    }

    /*private ContentValues montaContentValues(){
        Usar array de fields para pegar o nome do campo e concatenar com seu valor
        e retornar o content field pronto
    }*/
}
