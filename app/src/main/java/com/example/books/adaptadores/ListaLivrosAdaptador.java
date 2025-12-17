package com.example.books.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.books.R;
import com.example.books.modelo.Livro;

import java.util.ArrayList;

public class ListaLivrosAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Livro> livros;

    public ListaLivrosAdaptador(ArrayList<Livro> livros , Context context) {
        this.context = context;
        this.livros = livros;
    }


    @Override
    public int getCount() {
        return livros.size();
    }

    @Override
    public Object getItem(int i) {
       return livros.get(i);
    }

    @Override
    public long getItemId(int i) {
        return livros.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        viewHolderLista viewHolder;

        //  Inflacionar o layout apenas se não houver view reutilizável
        if (view == null) {
            if (inflater == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            // CORREÇÃO CRÍTICA: Use o ID do layout do item da lista.
            view = inflater.inflate(R.layout.item_lista_livro, viewGroup, false);

            // Cria e anexa o ViewHolder
            viewHolder = new viewHolderLista(view);
            view.setTag(viewHolder);
        } else {
            // Reutiliza o ViewHolder da View existente
            viewHolder = (viewHolderLista) view.getTag();
        }

        //  Atualiza o conteúdo do item (isto corre sempre e resolve o crash na linha 84)
        viewHolder.update(livros.get(i));
        return view;
    }


    private class viewHolderLista {

        private TextView tvTitulo, tvAutor, tvAno, tvSerie;
        private ImageView imgCapa;

        public viewHolderLista(View view) {
            tvTitulo = view.findViewById(R.id.tvTitulo);
            tvAutor = view.findViewById(R.id.tvAutor);
            tvAno = view.findViewById(R.id.tvAno);
            tvSerie = view.findViewById(R.id.tvSerie);
            imgCapa = view.findViewById(R.id.imgCapa);
        }

        public void update(Livro livro) {
            tvTitulo.setText(livro.getTitulo());
            tvAutor.setText(livro.getAutor());
            tvAno.setText(String.valueOf(livro.getAno()));
            tvSerie.setText(livro.getSerie());

            Glide.with(context)
                    .load(livro.getCapa()) // URL da imagem
                    .placeholder(R.drawable.logoipl) // Imagem enquanto carrega (certifique-se que existe)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgCapa);
        }


    }

}
