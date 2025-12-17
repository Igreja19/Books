package com.example.books.modelo;

import static com.example.books.utils.LivroJsonParser.isConnectionInternet;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.books.R;
import com.example.books.listeners.LivroListener;
import com.example.books.listeners.LivrosListener;
import com.example.books.utils.LivroJsonParser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SingletonGestorLivros {

    private static SingletonGestorLivros instancia = null;

    private ArrayList<Livro> livros;
    private LivroDBHelper livrosbd = null;
    private static RequestQueue volleyQueue = null;
    private static String mUrlAPILivros = "http://amsi.dei.estg.ipleiria.pt/api/livros";
    private static final String TOKEN = "AMSI-TOKEN";
    private LivrosListener livrosListener;
    private LivroListener livroListener;

    public static synchronized SingletonGestorLivros getInstancia(Context context) {
        if (instancia == null) {
            instancia = new SingletonGestorLivros(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instancia;
    }

    private SingletonGestorLivros(Context context) {
        livros = new ArrayList<>();
        livrosbd=new LivroDBHelper(context);
    }

    public void setLivrosListener (LivrosListener livrosListener) {
        this.livrosListener = livrosListener;
    }

    public void setLivroListener (LivroListener livroListener) {
        this.livroListener = livroListener;
    }

    public ArrayList<Livro> getLivros() {
        if (livros.isEmpty()){
            livros = livrosbd.getAllLivrosBD();
        }
        return livros;
    }

    public Livro getLivroById(int id) {
        for (Livro livro : getLivros()) {
            if (livro.getId() == id) {
                return livro;
            }
        }
        return null;
    }

    public Livro getLivro (int id) {
        for (Livro l:livros)
            if (l.getId() == id)
                return l;
        return null;
    }

    public void removerLivroBD (int idLivro) {
        Livro l = getLivro(idLivro);
        if (l != null)
            livrosbd.removerLivroBD(idLivro);

    }

    public void adicionarLivroBD(Livro livro) {
        livrosbd.adicionarLivroBD(livro);
    }

    public void editarLivroBD (Livro livro) {
        Livro l = getLivro(livro.getId());
        if (l != null)
            livrosbd.editarLivrosBD(livro);
    }

    public void adicionarLivrosBD(ArrayList<Livro> livros){
        livrosbd.removerALLLivroBD();
        for (Livro l:livros)
            adicionarLivroBD(l);
    }

    //Region crud api

    public void adicionarLivroAPI(final Livro livro, final Context context){
        if (!LivroJsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "Sem ligação à internet", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest req = new StringRequest(Request.Method.POST, mUrlAPILivros, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    adicionarLivroBD(LivroJsonParser.parserJsonLivro(s));
                    if (livroListener!=null)
                        livroListener.onRefreshDetalhes();
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(context, volleyError.getMessage(), Toast.LENGTH_LONG).show();
                }
            })

            {
                @Override
                        protected Map<String,String> getParams(){
                        Map<String, String> params = new HashMap<>();
                        params.put("token", TOKEN);
                        params.put("ano", String.valueOf(livro.getAno()));
                        params.put("titulo", livro.getTitulo());
                        params.put("autor", livro.getAutor());
                        params.put("serie", livro.getSerie());
                        params.put("capa", livro.getCapa());
                         return params;
                        }

            };

            volleyQueue.add(req);
        }
        
    }

    public void editarLivroAPI(final Livro livro, final Context context){
        if (!LivroJsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "Sem ligação à internet", Toast.LENGTH_SHORT).show();
        } else {
            // Nota: Verifica se a API aceita o ID na URL assim
            StringRequest req = new StringRequest(Request.Method.PUT, mUrlAPILivros + "/" + livro.getId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    editarLivroBD(livro);
                    if (livroListener!=null)
                        livroListener.onRefreshDetalhes();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(context, "Erro: " + volleyError.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String,String> getParams(){
                    Map <String, String> params = new HashMap<>();
                    params.put("token", TOKEN);
                    params.put("ano", String.valueOf(livro.getAno()));
                    params.put("titulo", livro.getTitulo());
                    params.put("autor", livro.getAutor());
                    params.put("serie", livro.getSerie());
                    params.put("capa", livro.getCapa());
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    
    public void removerLivroAPI(final Livro livro, final Context context){
        if (!LivroJsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "Sem ligação à internet", Toast.LENGTH_SHORT).show();
        } else {
            // Nota: Verifica se a API aceita o ID na URL assim
            StringRequest req = new StringRequest(Request.Method.DELETE, mUrlAPILivros + "/" + livro.getId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    removerLivroBD(livro.getId());
                    if (livroListener!=null)
                        livroListener.onRefreshDetalhes();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(context, volleyError.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String,String> getParams(){
                    Map <String, String> params = new HashMap<>();
                    params.put("token", TOKEN);
                    return params;
                }
            };
            volleyQueue.add(req);
        }
        
    }
    
    public void getAllLivrosAPI(final Context context){

        if (!LivroJsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "Sem ligação à internet", Toast.LENGTH_SHORT).show();
            if(livrosListener!=null)
                livrosListener.onRefreshListaLivros(livrosbd.getAllLivrosBD());
        } else {
            // Nota: Verifica se a API aceita o ID na URL assim
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPILivros , null,  new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    livros = LivroJsonParser.parserJsonLivros(jsonArray);
                    adicionarLivrosBD(livros);

                    if(livrosListener!=null)
                        livrosListener.onRefreshListaLivros(livros);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(context, volleyError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            volleyQueue.add(req);
        }

    }

    
    //EndRegion crud api


}
