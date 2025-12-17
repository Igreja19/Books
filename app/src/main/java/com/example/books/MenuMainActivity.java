package com.example.books;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

public class MenuMainActivity extends AppCompatActivity  implements
        NavigationView.OnNavigationItemSelectedListener {

    public static final int EDIT = 100, ADD = 200;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private String email;
    private FragmentManager fragmentManager ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.ndOpen, R.string.ndClose);
        toggle.syncState();
        drawer.addDrawerListener(toggle);
        carregarCabecalho(); //TODO:criar método
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();
        carregarFragmentoInicial();
    }

    private boolean carregarFragmentoInicial() {

        Menu menu = navigationView.getMenu();
        MenuItem item = menu.getItem(0); //obter o primeiro item do menu
        item.setChecked(true);
        return onNavigationItemSelected(item);
    }

    //vai carregar o email para a textbox (por baixo do avatar)

    private void carregarCabecalho() {

        email =  getIntent().getStringExtra("email");

        SharedPreferences sharedPrefUser = getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        if (email != null){
            SharedPreferences.Editor editor =sharedPrefUser.edit();
            editor.putString("EMAIL", email);
            editor.apply();
        }
        else
            email = sharedPrefUser.getString("EMAIL", "Sem email");

        View nView = navigationView.getHeaderView(0);
        TextView tvEmail = nView.findViewById(R.id.tvEmail);
        tvEmail.setText(email);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        if (menuItem.getItemId() == R.id.NavEstatico) {
            //System.out.println("-->Nav Estatico");
            fragment = new ListaLivrosFragment();
            setTitle(menuItem.getTitle());
        }
        else if (menuItem.getItemId() == R.id.NavDinamico) {
            //System.out.println("-->Nav Dinamico");
            fragment = new GrelhaLivrosFragment();
            setTitle(menuItem.getTitle());
        }
        else if (menuItem.getItemId() == R.id.NavEmail){
            //System.out.println("--Nav Email");
        }



            if (fragment!= null){
            fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();}

        drawer.closeDrawer ( GravityCompat.START);
        return true;
    }


    
}