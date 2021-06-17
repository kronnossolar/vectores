package com.app.vector.vectores.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AlertDialog;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.app.vector.vectores.R;
import com.app.vector.vectores.dao.ConfigurationDao;
import com.app.vector.vectores.dao.SessionDB;
import com.app.vector.vectores.dao.VectorDao;
import com.app.vector.vectores.entity.Configuration;
import com.app.vector.vectores.entity.Vector;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private EditText txt_magnitud,txt_angulo;
    private VectorDao vectorDao;
    private Configuration configuration;
    private ConfigurationDao configurationDao;
    private TextInputLayout tilMagnitud, tilAngulo;
    private boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doubleBackToExitPressedOnce = false;

        MobileAds.initialize(this);
        SessionDB sessionDB = new SessionDB(MainActivity.this);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        ImageButton btn_agregar =  findViewById(R.id.btn_agregar);
        ImageButton btn_editar =  findViewById(R.id.btn_editar);
        ImageButton btn_calcular =  findViewById(R.id.btn_calcular);
        Button btn_visualizar =  findViewById(R.id.btn_visualizar);
        txt_angulo = findViewById(R.id.txt_angulo);
        txt_magnitud = findViewById(R.id.txt_magnitud);
        tilAngulo = findViewById(R.id.tilAngulo);
        tilMagnitud = findViewById(R.id.tilMagnitud);
        setSupportActionBar(toolbar);
        AdView mAdView =  findViewById(R.id.adViewBanner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        vectorDao = sessionDB.getDaoSession().getVectorDao();
        configurationDao = sessionDB.getDaoSession().getConfigurationDao();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        btn_visualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,viewVector.class);
                intent.putExtra("resultado",0);
                startActivity(intent);
            }
        });
        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_magnitud.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),getString(R.string.toast_error),Toast.LENGTH_SHORT).show();
                    txt_magnitud.requestFocus();
                }else if(txt_angulo.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),getString(R.string.toast_error),Toast.LENGTH_SHORT).show();
                    txt_angulo.requestFocus();
                }else{
                    keyboard();
                    crear_vector(Double.parseDouble(txt_magnitud.getText().toString()),Double.parseDouble(txt_angulo.getText().toString()));
                    txt_magnitud.setText("");
                    txt_angulo.setText("");
                }
            }
        });
        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListVector.class);
                startActivity(intent);
            }
        });
        btn_calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,viewVector.class);
                intent.putExtra("resultado",1);
                startActivity(intent);
            }
        });
        configuration = crear_configuracion();
        load_view(configuration.getTipoCoordenada());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.exit), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
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
        int id = item.getItemId();
        if(id == R.id.action_reload){
            vectorDao.deleteAll();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.decimales) {
            dialog_decimal(R.string.decimales);
        } else if (id == R.id.escala) {
            dialog_escala(R.string.escala);
        } else if (id == R.id.coordenadas) {
            dialog_coordenada(R.string.coordenada);
        } else if(id == R.id.view){
            dialog_view(R.string.vista);
        }/*else if (id == R.id.contacto) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void keyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    public void dialog_view(int title){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setIcon(R.mipmap.ic_launcher);
        builderSingle.setTitle(title);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add(getString(R.string.plano));
        arrayAdapter.add(getString(R.string.poligono));
        builderSingle.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                if(strName == null) strName = "";
                switch (which){
                    case 0:
                        configuration.setTipoView(0);
                        configurationDao.update(configuration);
                        break;
                    case 1:
                        configuration.setTipoView(1);
                        configurationDao.update(configuration);
                        break;
                }
            }
        });
        builderSingle.show();
    }
    public void dialog_decimal(int title){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setIcon(R.mipmap.ic_launcher);
        builderSingle.setTitle(title);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("0");
        arrayAdapter.add("1");
        arrayAdapter.add("2");
        arrayAdapter.add("3");
        arrayAdapter.add("4");

        builderSingle.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                if(strName == null) strName = "";
                switch (which){
                    case 0:
                        configuration.setCantDecimal(Cantidad_Decimales.cero);
                        configurationDao.update(configuration);
                        break;
                    case 1:
                        configuration.setCantDecimal(Cantidad_Decimales.uno);
                        configurationDao.update(configuration);
                        break;
                    case 2:
                        configuration.setCantDecimal(Cantidad_Decimales.dos);
                        configurationDao.update(configuration);
                        break;
                    case 3:
                        configuration.setCantDecimal(Cantidad_Decimales.tres);
                        configurationDao.update(configuration);
                        break;
                    case 4:
                        configuration.setCantDecimal(Cantidad_Decimales.cuatro);
                        configurationDao.update(configuration);
                        break;
                }
            }
        });
        builderSingle.show();
    }
    public void dialog_escala(int title){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setIcon(R.mipmap.ic_launcher);
        builderSingle.setTitle(title);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("0.5 N");
        arrayAdapter.add("1 N");
        arrayAdapter.add("5 N");
        arrayAdapter.add("10 N");
        arrayAdapter.add("50 N");
        arrayAdapter.add("100 N");
        arrayAdapter.add("1000 N");
        arrayAdapter.add("10000 N");

        builderSingle.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                if(strName == null) strName = "";
                switch (which){
                    case 0:
                        configuration.setEscala(Escala._05N);
                        configurationDao.update(configuration);
                        break;
                    case 1:
                        configuration.setEscala(Escala._1N);
                        configurationDao.update(configuration);
                        break;
                    case 2:
                        configuration.setEscala(Escala._5N);
                        configurationDao.update(configuration);
                        break;
                    case 3:
                        configuration.setEscala(Escala._10N);
                        configurationDao.update(configuration);
                        break;
                    case  4:
                        configuration.setEscala(Escala._50N);
                        configurationDao.update(configuration);
                        break;
                    case 5:
                        configuration.setEscala(Escala._100N);
                        configurationDao.update(configuration);
                        break;
                    case 6:
                        configuration.setEscala(Escala._1000N);
                        configurationDao.update(configuration);
                        break;
                    case 7:
                        configuration.setEscala(Escala._10000N);
                        configurationDao.update(configuration);
                        break;
                }
            }
        });
        builderSingle.show();

    }
    private void dialog_coordenada(int title){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setIcon(R.mipmap.ic_launcher);
        builderSingle.setTitle(title);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add(getString(R.string.cartesiana));
        arrayAdapter.add(getString(R.string.polar));
        builderSingle.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                if(strName == null) strName = "";

                switch (which){
                    case 0:
                        configuration.setTipoCoordenada(1);
                        configurationDao.update(configuration);
                        load_view(configuration.getTipoCoordenada());
                        break;
                    case 1:
                        configuration.setTipoCoordenada(0);
                        configurationDao.update(configuration);
                        load_view(configuration.getTipoCoordenada());
                        break;
                }
            }
        });
        builderSingle.show();
    }
    public void crear_vector(double magnitud,double angulo){

        Vector vector = new Vector();
        Log.i("Tipo Coordenada",String.valueOf(configuration.getTipoCoordenada()));
        if(configuration.getTipoCoordenada() == 0){
            vector.setAngulo(angulo);
            vector.setMagnitud(magnitud);
            vector.calcular_componentes();
        }else {
            vector.setCompX((float)(magnitud));
            vector.setCompY((float)(angulo));
            vector.setMagnitud();
            vector.setAngulo();
        }
        vectorDao.insert(vector);

    }
    private Configuration crear_configuracion(){
        List<Configuration> configurations = configurationDao.loadAll();
        Configuration configuration;
        Log.i("Config","Crear config");
        if(configurations.isEmpty()){
            Log.i("Config","Sin config");
            configuration = new Configuration();
            configurationDao.insert(configuration);
            return configuration;
        }else{
            Log.i("Config","Config");
            configuration = configurations.get(0);
            return configuration;
        }
    }
    private void load_view(int tipoCoordenada){
        if(tipoCoordenada == 0){
            tilMagnitud.setHint(getString(R.string.magnitud));
            tilAngulo.setHint(getString(R.string.angulo));
            /*se validan caracteres admitidos*/
            txt_magnitud.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
            txt_angulo.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
        }else{
            tilMagnitud.setHint(getString(R.string.Fx));
            tilAngulo.setHint(getString(R.string.Fy));
            /*se validan caracteres admitidos*/
            txt_magnitud.setKeyListener(DigitsKeyListener.getInstance("0123456789.-"));
            txt_angulo.setKeyListener(DigitsKeyListener.getInstance("0123456789.-"));
        }
    }
}
