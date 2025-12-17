package com.example.books;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class EstaticoFragment extends Fragment {



    public EstaticoFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estatico, container, false);
        TextView tvTitulo = view.findViewById(R.id.tvTitulo);
        TextView tvSerie = view.findViewById(R.id.tvSerie);
        TextView tvAutor = view.findViewById(R.id.tvAutor);
        TextView tvAno = view.findViewById(R.id.tvAno);
        ImageView Capa = view.findViewById(R.id.imgCapa);

        tvTitulo.setText("Programar em Android com Java");
        tvSerie.setText("Android Saga");
        tvAutor.setText("Equipa AMSI");
        tvAno.setText("2025");
        Capa.setImageResource(R.drawable.programarandroid1);

        return view;
    }
}