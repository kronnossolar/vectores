package com.app.vector.vectores.view;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.app.vector.vectores.R;
import com.app.vector.vectores.dao.VectorDao;
import com.app.vector.vectores.entity.Vector;
import com.app.vector.vectores.dao.SessionDB;

import java.util.List;

public class ListVector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_vector);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final RecyclerView recycler;
        vector_adapter adapter;
        RecyclerView.LayoutManager lManager;
        final List<Vector> vectors;
        SessionDB sessionDB = new SessionDB(ListVector.this);
        VectorDao vectorDao = sessionDB.getDaoSession().getVectorDao();
        vectors = vectorDao.loadAll();

        recycler = findViewById(R.id.lst_vectores);
        recycler.setHasFixedSize(true);

        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        adapter = new vector_adapter(ListVector.this,vectors);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListVector.this, EditVector.class);
                intent.putExtra("Id_Vector", vectors.get(recycler.getChildAdapterPosition(view)).getId());
                startActivity(intent);
            }
        });
        recycler.setAdapter(adapter);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        startActivity(new Intent(getBaseContext(), MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        finish();
        return true;
    }
}
