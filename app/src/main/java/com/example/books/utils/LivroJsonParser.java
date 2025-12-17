package com.example.books.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.books.modelo.Livro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LivroJsonParser {

    public static ArrayList<Livro> parserJsonLivros(JSONArray response) {
        ArrayList<Livro> livros = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject livro = (JSONObject) response.get(i);
                int id = livro.getInt("id");
                String titulo = livro.getString("titulo");
                String serie = livro.getString("serie");
                String autor = livro.getString("autor");
                int ano = livro.getInt("ano");
                String capa = livro.getString("capa");
                Livro auxLivro = new Livro(id, ano, titulo, serie, autor, capa);
                livros.add(auxLivro);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
        return livros;
    }


    public static Livro parserJsonLivro(String response) {
        Livro auxLivro = null;
        try {
            JSONObject livro = new JSONObject(response);
            int id = livro.getInt("id");
            String titulo = livro.getString("titulo");
            String serie = livro.getString("serie");
            String autor = livro.getString("autor");
            int ano = livro.getInt("ano");
            String capa = livro.getString("capa");
            auxLivro = new Livro(id, ano, titulo, serie, autor, capa);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return auxLivro;
    }


    public static String parserJsonLogin (){
        // TODO devolve um token
        return null;
    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm!= null){
            NetworkInfo ni = cm.getActiveNetworkInfo();
            return ni!= null && ni.isConnected();
        }
        return false;
    }
}
