package com.example.books;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.books.listeners.LivroListener;
import com.example.books.modelo.Livro;
import com.example.books.modelo.SingletonGestorLivros;

public class DetalhesDinamico extends AppCompatActivity implements LivroListener {

    private TextView tvAutor, tvTitulo, tvAno, tvSerie;
    private ImageView Capa, ivRemover;
    private Livro livro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_dinamico);


        tvTitulo = findViewById(R.id.tvTitulo);
        tvSerie = findViewById(R.id.tvSerie);
        tvAutor = findViewById(R.id.tvAutor);
        tvAno = findViewById(R.id.tvAno);
        Capa = findViewById(R.id.Capa);
        ivRemover = findViewById(R.id.ivRemover);
        ivRemover.setVisibility(View.INVISIBLE);

        int idLivro = getIntent().getIntExtra("idLivro", -1);

        livro = SingletonGestorLivros.getInstancia(DetalhesDinamico.this).getLivroById(idLivro);
        SingletonGestorLivros.getInstancia(getApplicationContext()).setLivroListener(this);


        //Mostrar detalhes do livro

        if (livro != null) {
            tvTitulo.setText(livro.getTitulo());
            tvSerie.setText(livro.getSerie());
            tvAutor.setText(livro.getAutor());
            tvAno.setText(String.valueOf(livro.getAno()));
            String nomeCapa = livro.getCapa();

            if(getIntent().getBooleanExtra("isEdit", false)){
                ivRemover.setVisibility(View.VISIBLE);
            }
            int resID = getResources().getIdentifier(nomeCapa, "drawable", getPackageName());

            if (resID != 0) {
                Capa.setImageResource(resID);
            } else {
                // Imagem de recurso caso não encontre (ex: ic_launcher_foreground)
                Capa.setImageResource(android.R.drawable.ic_menu_gallery);
            }
        }

        ivRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetalhesDinamico.this);
                builder.setTitle("Remover Livro");
                builder.setMessage("Tem a certeza que quer remover o livro?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SingletonGestorLivros.getInstancia(getApplicationContext()).removerLivroAPI(livro, getApplicationContext());
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public void onRefreshDetalhes() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}