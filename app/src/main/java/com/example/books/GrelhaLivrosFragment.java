package com.example.books;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class GrelhaLivrosFragment extends Fragment {




    //TODO TPC implementar a grelha de livros

    public GrelhaLivrosFragment() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_grelha_livros, container, false);



        return view;
    }
}