package com.example.books;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.books.modelo.Livro;
import com.example.books.modelo.SingletonGestorLivros;

import java.util.ArrayList;

public class DinamicoFragment extends Fragment {



    private TextView tvTitulo, tvSerie, tvAutor , tvAno;
    private ImageView Capa;

    public DinamicoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_dinamico, container, false);
         tvTitulo = view.findViewById(R.id.tvTitulo);
         tvSerie = view.findViewById(R.id.tvSerie);
         tvAutor= view.findViewById(R.id.tvAutor);
         tvAno = view.findViewById(R.id.tvAno);
         Capa = view.findViewById(R.id.Capa);
        carregarLivro();
        return view;
    }

    private void carregarLivro() {
        if (getContext() == null) return;

        ArrayList<Livro> livros = SingletonGestorLivros.getInstancia(getContext()).getLivros();
        Livro livro;

        //Garantir que a lista de livros tem um livro
        if(livros != null && livros.size() >0) {
            livro = livros.get(0); //obter o primeiro livro da lista
            tvTitulo.setText(livro.getTitulo());
            tvSerie.setText(livro.getSerie());
            tvAutor.setText(livro.getAutor());
            tvAno.setText(String.valueOf(livro.getAno()));

            String nomeCapa = livro.getCapa();
            int resID = getResources().getIdentifier(nomeCapa, "drawable", getContext().getPackageName());

            if (resID != 0) {
                Capa.setImageResource(resID);
            } else {
                Capa.setImageResource(android.R.drawable.ic_menu_gallery);
            }

        }
    }

}