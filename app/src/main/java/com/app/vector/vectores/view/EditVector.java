package com.app.vector.vectores.view;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.app.vector.vectores.R;
import com.app.vector.vectores.dao.SessionDB;
import com.app.vector.vectores.dao.VectorDao;
import com.app.vector.vectores.entity.Vector;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class EditVector extends AppCompatActivity {
    private VectorDao vectorDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vector);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MobileAds.initialize(this);
        AdView mAdView =  findViewById(R.id.adViewBanner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        final EditText txt_magnitud,txt_angulo;
        txt_angulo = findViewById(R.id.txt_angulo_edit);
        txt_magnitud = findViewById(R.id.txt_magnitud_edit);
        final Vector vector;
        SessionDB sessionDB = new SessionDB(EditVector.this);
        vectorDao = sessionDB.getDaoSession().getVectorDao();
        Bundle bundle = getIntent().getExtras();
        vector = vectorDao.load(bundle.getLong("Id_Vector"));
        txt_magnitud.setText(String.valueOf(vector.getMagnitud()));
        txt_angulo.setText(String.valueOf(vector.getAngulo()));
        final FloatingActionButton update =  findViewById(R.id.update);
        final FloatingActionButton delete =  findViewById(R.id.delete);
        update.setOnClickListener(view -> {
            vector.setMagnitud(Double.parseDouble(txt_magnitud.getText().toString()));
            vector.setAngulo(Double.parseDouble(txt_angulo.getText().toString()));
            vector.calcular_componentes();
            update(vector);
            exit();
        });
        delete.setOnClickListener(v -> {
            delete(vector.getId());
            exit();
        });
    }
    protected void update(Vector vector){
        vectorDao.update(vector);
    }
    protected void delete(Long idVector){
        vectorDao.deleteByKey(idVector);
    }
    protected void exit(){
        startActivity(new Intent(getBaseContext(), MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        finish();
    }
}
