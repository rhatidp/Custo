package br.unicamp.ft.r176257.myapplication;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private NavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.idioma);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            GerenciarCategoriasFragment fragment = new GerenciarCategoriasFragment();
            fragmentTransaction.replace(R.id.frame, fragment, "gerenciar_categorias");
            fragmentTransaction.commit();
            this.setTitle(R.string.titulo_tela_categorias);

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(this.getCurrentFocus() != null && this.getCurrentFocus() instanceof EditText){
            InputMethodManager imm = (InputMethodManager) getSystemService(this.getApplicationContext().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.opcao_idioma) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            IdiomaFragment fragment = new IdiomaFragment();
            fragmentTransaction.replace(R.id.frame, fragment, "gerenciar_categorias");
            fragmentTransaction.commit();
            this.setTitle(R.string.titulo_tela_idioma);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(this.getCurrentFocus() != null && this.getCurrentFocus() instanceof EditText){
            InputMethodManager imm = (InputMethodManager) getSystemService(this.getApplicationContext().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        if (id == R.id.menu_categoria) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            GerenciarCategoriasFragment fragment = new GerenciarCategoriasFragment();
            fragmentTransaction.replace(R.id.frame, fragment, "gerenciar_categorias");
            fragmentTransaction.commit();
            this.setTitle(R.string.titulo_tela_categorias);
        } else if (id == R.id.menu_despesa) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            GerenciarDespesasFragment fragment = new GerenciarDespesasFragment();
            fragmentTransaction.replace(R.id.frame, fragment, "gerenciar_despesas");
            fragmentTransaction.commit();
            this.setTitle(R.string.titulo_tela_despesas);
        } else if (id == R.id.menu_grafico_donut) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            GraficoDonutFragment fragment = new GraficoDonutFragment();
            fragmentTransaction.replace(R.id.frame, fragment, "grafico_donut");
            fragmentTransaction.commit();
            this.setTitle(R.string.titulo_tela_donut);
        } else if (id == R.id.menu_grafico_linha) {/*
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            GraficoLinhas fragment = new GraficoLinhas();
            fragmentTransaction.replace(R.id.frame, fragment, "grafico_linhas");
            fragmentTransaction.commit();
            this.setTitle(R.string.titulo_tela_linhas);*/
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
