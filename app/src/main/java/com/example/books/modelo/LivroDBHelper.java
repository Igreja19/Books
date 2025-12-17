package com.example.books.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.PublicKey;
import java.util.ArrayList;

public class LivroDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "livros.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_LIVROS = "livros";
    private final SQLiteDatabase db;


    // Nomes da Tabela e Colunas
    public static final String ID = "id";
    public static final String ANO = "ano";
    public static final String TITULO = "titulo";
    public static final String AUTOR = "autor";
    public static final String CAPA = "capa";
    public static final String SERIE = "serie";


    public LivroDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.db = getWritableDatabase();

    }

    //metodos onCreate e onUpgrade
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_LIVROS + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ANO + " INTEGER, " +
                TITULO + " TEXT, " +
                AUTOR + " TEXT, " +
                CAPA + " TEXT, " +
                SERIE + " TEXT)";
        db.execSQL(createTable);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIVROS);
        onCreate(db);
    }


    public Livro adicionarLivroBD(Livro l){
        ContentValues values = new ContentValues();
        values.put(ID, l.getId()); // <--- ESTA LINHA É O SEGREDO
        values.put(ANO, l.getAno());
        values.put(TITULO, l.getTitulo());
        values.put(AUTOR, l.getAutor());
        values.put(CAPA, l.getCapa());
        values.put(SERIE, l.getSerie());

        // Inserir com o ID correto
        this.db.insert(TABLE_LIVROS, null , values);
        return l;
    }


    public boolean editarLivrosBD(Livro l){
        ContentValues values = new ContentValues();
        values.put(ANO, l.getAno());
        values.put(TITULO, l.getTitulo());
        values.put(AUTOR, l.getAutor());
        values.put(CAPA, l.getCapa());
        values.put(SERIE, l.getSerie());
        int numLinhas = this.db.update(TABLE_LIVROS, values, ID + "=?", new String[]{l.getId()+""});
        return numLinhas>0;
    }

    public boolean removerLivroBD(int id) {
        int numLinhas = this.db.delete(TABLE_LIVROS, ID + "=?", new String[]{id + ""});
        return numLinhas > 0;
    }

    public void removerALLLivroBD() {
        this.db.delete(TABLE_LIVROS,null, null  );
    }

    public ArrayList<Livro> getAllLivrosBD() {
        ArrayList<Livro> livros = new ArrayList<>();
        Cursor cursor = this.db.query(TABLE_LIVROS, new String[]{ID, ANO, TITULO, AUTOR,
                CAPA, SERIE}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do{
                Livro auxLivro = new Livro (
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(5),
                        cursor.getString(3),
                        cursor.getString(4)
                );livros.add(auxLivro);

            }while (cursor.moveToNext());
            cursor.close();
        }
        return livros;
    }
}
