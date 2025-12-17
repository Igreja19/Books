package com.example.books;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetalhesEstatico extends AppCompatActivity {

    private TextView tvAutor, tvTitulo, tvAno, tvSerie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalhes_estatico);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setTitle("Livro Estático");

        tvTitulo = findViewById(R.id.tvTitulo);
        tvSerie = findViewById(R.id.tvSerie);
        tvAutor = findViewById(R.id.tvAutor);
        tvAno = findViewById(R.id.tvAno);

        tvTitulo.setText("Programar em Android com Java");
        tvSerie.setText("Android Saga");
        tvAutor.setText("Equipa AMSI");
        tvAno.setText("2025");

    }
}