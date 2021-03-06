package br.unicamp.ft.r176257.myapplication.layout;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

import br.unicamp.ft.r176257.myapplication.R;
import br.unicamp.ft.r176257.myapplication.database.DatabaseHelper;
import br.unicamp.ft.r176257.myapplication.layout.charts.GraficoDonutFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private FragmentManager fragmentManager;
    private NavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadLocale();
        Toolbar toolbar = (Toolbar) findViewById(R.id.idioma);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigation = navigationView;

        fragmentManager = getSupportFragmentManager();

        if (getTrocouIdioma()) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            IdiomaFragment fragment = new IdiomaFragment();
            fragmentTransaction.replace(R.id.frame, fragment, "opcao_idioma");
            fragmentTransaction.commit();
            this.setTitle(R.string.titulo_tela_idioma);
            Toast.makeText(this, R.string.idioma_trocado, Toast.LENGTH_SHORT).show();
        }

        if (savedInstanceState == null) {
            dbHelper = new DatabaseHelper(this);
            sqLiteDatabase = dbHelper.getReadableDatabase();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (!hasCategoria()) {
                GerenciarCategoriasFragment fragment = new GerenciarCategoriasFragment();
                fragmentTransaction.replace(R.id.frame, fragment, "gerenciar_categorias");
                fragmentTransaction.commit();
                this.setTitle(R.string.titulo_tela_categorias);
                navigation.setCheckedItem(R.id.menu_categoria);
            } else if (!hasDespesa()) {
                GerenciarDespesasFragment fragment = new GerenciarDespesasFragment();
                fragmentTransaction.replace(R.id.frame, fragment, "gerenciar_despesas");
                fragmentTransaction.commit();
                this.setTitle(R.string.titulo_tela_despesas);
                navigation.setCheckedItem(R.id.menu_despesa);
            } else {
                GraficoDonutFragment fragment = new GraficoDonutFragment();
                fragmentTransaction.replace(R.id.frame, fragment, "grafico_donut");
                fragmentTransaction.commit();
                this.setTitle(R.string.titulo_tela_donut);
                navigation.setCheckedItem(R.id.menu_grafico_donut);
            }
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
            String tag = "opcao_idioma";
            int title = R.string.titulo_tela_idioma;
            Fragment fragment = fragmentManager.findFragmentByTag(tag);
            if ((fragment == null) || (getTrocouIdioma())) {
                setTrocouIdioma(false);
                replaceFragment(new IdiomaFragment(), tag, title);
            }
            else {
                replaceFragment(fragment, tag, title);
            }
            return true;
        }
        else if (id == R.id.opcao_sobre) {
            String tag = "opcao_sobre";
            int title = R.string.titulo_tela_sobre;
            Fragment fragment = fragmentManager.findFragmentByTag(tag);
            if ((fragment == null) || (getTrocouIdioma())) {
                setTrocouIdioma(false);
                replaceFragment(new SobreFragment(), tag, title);
            }
            else {
                replaceFragment(fragment, tag, title);
            }
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
            String tag = "gerenciar_categorias";
            int title = R.string.titulo_tela_categorias;
            Fragment fragment = fragmentManager.findFragmentByTag(tag);
            if ((fragment == null) || (getTrocouIdioma())) {
                setTrocouIdioma(false);
                replaceFragment(new GerenciarCategoriasFragment(), tag, title);
            }
            else {
                replaceFragment(fragment, tag, title);
            }
        } else if (id == R.id.menu_despesa) {
            String tag = "gerenciar_despesas";
            int title = R.string.titulo_tela_despesas;
            Fragment fragment = fragmentManager.findFragmentByTag(tag);
            if ((fragment == null) || (getTrocouIdioma())) {
                setTrocouIdioma(false);
                replaceFragment(new GerenciarDespesasFragment(), tag, title);
            }
            else {
                replaceFragment(fragment, tag, title);
            }
        } else if (id == R.id.menu_grafico_donut) {
            String tag = "grafico_donut";
            int title = R.string.titulo_tela_donut;
            Fragment fragment = fragmentManager.findFragmentByTag(tag);
            if ((fragment == null) || (getTrocouIdioma())) {
                setTrocouIdioma(false);
                replaceFragment(new GraficoDonutFragment(), tag, title);
            }
            else {
                replaceFragment(fragment, tag, title);
            }
        } else if (id == R.id.menu_salvar_relatorio) {
            String tag = "salvar_relatorio";
            int title = R.string.titulo_tela_salvar_relatorio;
            Fragment fragment = fragmentManager.findFragmentByTag(tag);
            if ((fragment == null) || (getTrocouIdioma())) {
                setTrocouIdioma(false);
                replaceFragment(new SalvarRelatorioFragment(), tag, title);
            }
            else {
                replaceFragment(fragment, tag, title);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment f, String tag, int title) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, f, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        this.setTitle(title);
    }

    private boolean getTrocouIdioma() {
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("trocou_idioma")) {
                return getIntent().getExtras().getBoolean("trocou_idioma");
            }
        }
        return false;
    }

    private void setTrocouIdioma(boolean b) {
        Bundle extras = this.getIntent().getExtras();
        getIntent().putExtra("trocou_idioma", b);
    }

    private boolean hasCategoria() {
        String sql = "SELECT COUNT(_id) FROM Categoria";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            if (cursor.getInt(0) > 1) {
                cursor.close();
                return true;
            }
        }
        cursor.close();
        return false;
    }

    private boolean hasDespesa() {
        String sql = "SELECT _id FROM Despesa LIMIT 1";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public void loadLocale() {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        changeLang(language);
    }

    public void changeLang(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

    }
}
