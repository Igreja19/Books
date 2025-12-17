package com.example.books;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button  btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        //Listener do botão de login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                String emailValidationResult = isEmailValid(email);
                String passwordValidationResult = isPasswordValid(password);

               //Passar para MainActivity
                if (emailValidationResult.equals("Email válido") && passwordValidationResult.equals("Password válida")) {
                    Toast.makeText(LoginActivity.this, "Login bem-sucedido", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MenuMainActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login falhou: " + emailValidationResult + " e " + passwordValidationResult, Toast.LENGTH_LONG).show();
                }
            }
        });
    }


        //verificar se email é valido
    public String isEmailValid(String email) {
        if (email.contains("@") && email.contains(".com")) {
            return "Email válido";
        } else {
            return "Email inválido";
        }
    }

    public String isPasswordValid(String password) {
        if (password.length() >= 4) {
            return "Password válida";
        } else {
            return "Password inválida";
        }
    }


}