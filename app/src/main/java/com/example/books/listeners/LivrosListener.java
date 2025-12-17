package com.example.books.listeners;

import com.example.books.modelo.Livro;

import java.util.ArrayList;

public interface LivrosListener {
    void onRefreshListaLivros(ArrayList<Livro> listaLivros);

}
