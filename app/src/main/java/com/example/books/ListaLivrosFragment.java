package com.example.books;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.books.adaptadores.ListaLivrosAdaptador;
import com.example.books.listeners.LivrosListener;
import com.example.books.modelo.Livro;
import com.example.books.modelo.SingletonGestorLivros;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.TestOnly;

import java.util.ArrayList;

public class ListaLivrosFragment extends Fragment implements LivrosListener {

    private ListView lvLivros;
   // private ArrayList<Livro> livros;

    private FloatingActionButton fabLista;
    private SearchView searchView;




    public ListaLivrosFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_lista_livros, container, false);
        setHasOptionsMenu(true);
        lvLivros= view.findViewById(R.id.lvLivros);
        SingletonGestorLivros.getInstancia(getContext()).setLivrosListener(this);
        SingletonGestorLivros.getInstancia(getContext()).getAllLivrosAPI(getContext());

        lvLivros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetalhesLivrosActivity.class);
                intent.putExtra("IDLIVRO", (int) id );
                startActivityForResult(intent, MenuMainActivity.EDIT);
            }
        });

        fabLista = view.findViewById(R.id.fabLista);
        fabLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetalhesLivrosActivity.class);
                startActivityForResult(intent, MenuMainActivity.ADD);
            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == android.app.Activity.RESULT_OK) {
            if (requestCode == MenuMainActivity.ADD) {
                Toast.makeText(getContext(), "Livro adicionado com sucesso", Toast.LENGTH_SHORT).show();
            }
            else if (requestCode == MenuMainActivity.EDIT) {
                // Serve tanto para Editar como para Apagar
                Toast.makeText(getContext(), "Operação realizada com sucesso", Toast.LENGTH_SHORT).show();
            }
            // Atualiza a lista
            SingletonGestorLivros.getInstancia(getContext()).getAllLivrosAPI(getContext());
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_pesquisa, menu);
        MenuItem itemPesquisa = menu.findItem(R.id.itemPesquisa);
        searchView = (SearchView) itemPesquisa.getActionView();
        //listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList <Livro> tempLivros = new ArrayList<>();
                for (Livro l : SingletonGestorLivros.getInstancia(getContext()).getLivros()) {
                    if (l.getTitulo().toLowerCase().contains(s.toLowerCase()))
                        tempLivros.add(l);
                }
                lvLivros.setAdapter(new ListaLivrosAdaptador(tempLivros, getContext()));
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
        });
    }

    @Override
    public void onRefreshListaLivros(ArrayList<Livro> listaLivros) {
        if(listaLivros != null)
            lvLivros.setAdapter(new ListaLivrosAdaptador(listaLivros, getContext()));
    }
}