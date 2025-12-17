package com.example.books;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.books.adaptadores.ListaLivrosAdaptador;
import com.example.books.listeners.LivroListener;
import com.example.books.listeners.LivrosListener;
import com.example.books.modelo.Livro;
import com.example.books.modelo.SingletonGestorLivros;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DetalhesLivrosActivity extends AppCompatActivity implements LivroListener {

    private EditText etTitulo, etSerie, etAutor, etAno;
    private FloatingActionButton fabGuardar;
    private ImageView imgCapa;
    private Livro livro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_livros);

        // Recebe o ID do Intent (o padrão é 0 se for novo)
        int id = getIntent().getIntExtra("IDLIVRO", -1);

        // Nota: Se comentaste a lista no Singleton, isto pode vir null,
        // mas para já vamos manter a lógica para corrigir o erro de compilação.
        livro = SingletonGestorLivros.getInstancia(DetalhesLivrosActivity.this).getLivroById(id);

        etTitulo = findViewById(R.id.etTitulo);
        etSerie = findViewById(R.id.etSerie);
        etAutor = findViewById(R.id.etAutor);
        etAno = findViewById(R.id.etAno);
        imgCapa = findViewById(R.id.Capa);
        fabGuardar = findViewById(R.id.fabGuardar);
        SingletonGestorLivros.getInstancia(this).setLivroListener(this);

        if (livro != null) {
            carregarLivro();
        } else {
            setTitle("Adicionar Livro");
            fabGuardar.setImageResource(R.drawable.ic_action_adicionar);
        }

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idLivroValido()) {
                    if (livro != null) {
                        // MODO EDIÇÃO
                        livro.setTitulo(etTitulo.getText().toString());
                        livro.setAutor(etAutor.getText().toString());
                        livro.setSerie(etSerie.getText().toString());
                        livro.setAno(Integer.parseInt(etAno.getText().toString()));
                        SingletonGestorLivros.getInstancia(DetalhesLivrosActivity.this).editarLivroAPI(livro, DetalhesLivrosActivity.this );
                    } else {
                        livro = new Livro(
                                0,
                                Integer.parseInt(etAno.getText().toString()),
                                etTitulo.getText().toString(),
                                etSerie.getText().toString(),
                                etAutor.getText().toString(),
                                "sem_capa"
                        );
                        SingletonGestorLivros.getInstancia(DetalhesLivrosActivity.this).adicionarLivroAPI(livro, DetalhesLivrosActivity.this);
                    }
                    }else {
                        Toast.makeText(DetalhesLivrosActivity.this, "Dados inválidos", Toast.LENGTH_SHORT).show();
                    }

            }
        });
    }

    private boolean idLivroValido() {
        String titulo = etTitulo.getText().toString().trim();
        String serie = etSerie.getText().toString().trim();
        String autor = etAutor.getText().toString().trim();
        String ano = etAno.getText().toString().trim();
        int auxAno;

        if (titulo.length() < 3 || serie.length() < 3 || autor.length() < 3 || ano.length() != 4) {
            return false;
        }
        try {
            auxAno = Integer.parseInt(ano);
        } catch (NumberFormatException e) {
            return false;
        }
        if (auxAno < 1000 || auxAno > 2025) {
            return false;
        }
        return true;
    }


    private void carregarLivro() {
        etTitulo.setText(livro.getTitulo());
        etSerie.setText(livro.getSerie());
        etAutor.setText(livro.getAutor());
        etAno.setText(String.valueOf(livro.getAno()));

        String nomeCapa = livro.getCapa();

        if (nomeCapa != null && !nomeCapa.isEmpty()) {
            Context context = DetalhesLivrosActivity.this;
            // Procura na pasta 'drawable' um ficheiro com o nome igual à String 'nomeCapa'
            int resID = context.getResources().getIdentifier(nomeCapa, "drawable", context.getPackageName());

            if (resID != 0) {
                // Se encontrou a imagem, define-a
                imgCapa.setImageResource(resID);
            } else {
                // Se não encontrou, mete uma imagem padrão do sistema ou uma tua
                imgCapa.setImageResource(android.R.drawable.ic_menu_gallery);
            }
        } else {
            imgCapa.setImageResource(android.R.drawable.ic_menu_gallery);
        }

    }


    //Injetar o menu na activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (livro != null)
            getMenuInflater().inflate(R.menu.menu_apagar, menu);
        return true;
    }

    //acões dos itens do menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemApagar) {
            dialogRemover();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogRemover() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Remover livro")
                .setMessage("Tem a certeza que quer eliminar o livro?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SingletonGestorLivros.getInstancia(DetalhesLivrosActivity.this).removerLivroAPI(livro, DetalhesLivrosActivity.this);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_delete)
                .show();
    }

    public void onRefreshDetalhes() {
        setResult(RESULT_OK);
        finish();
    }


}