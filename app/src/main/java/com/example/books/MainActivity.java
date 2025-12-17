package com.example.books;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btnEstatico, btnDinamico, btnEnviarEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnEstatico = findViewById(R.id.btnEstatico);
        btnDinamico = findViewById(R.id.btnDinamico);
        btnEnviarEmail = findViewById(R.id.btnEnviarEmail);

        //Listener do botão estatico
        btnEstatico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetalhesEstatico.class);
                startActivity(intent);
            }
        });

        //Listener do botão dinamico
        btnDinamico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetalhesDinamico.class);
                intent.putExtra("idLivro", 1); // ou outro ID
                startActivity(intent);
            }
        });

    }
}