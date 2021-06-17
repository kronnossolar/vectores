package com.app.vector.vectores.view;

import android.content.Context;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.vector.vectores.R;
import com.app.vector.vectores.entity.Vector;

import java.util.List;

/**
 * Created by Mario on 1/05/2017.
 *
 */
public class vector_adapter extends RecyclerView.Adapter<vector_adapter.VectorViewHolder> implements View.OnClickListener{
    private List<Vector> items;
    private Context context;
    private View.OnClickListener listener;

    public vector_adapter(Context context,List<Vector> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null) listener.onClick(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public static class VectorViewHolder extends RecyclerView.ViewHolder {
            // Campos respectivos de un item
            public TextView lbl_magnitud,lbl_angulo,lbl_titulo;
            public CardView cardView;
            public VectorViewHolder(View v) {
                super(v);
                lbl_magnitud = (TextView) v.findViewById(R.id.lbl_magnitud);
                lbl_angulo = (TextView) v.findViewById(R.id.lbl_angulo);
                lbl_titulo = (TextView) v.findViewById(R.id.lbl_titulo);
                cardView = (CardView) v.findViewById(R.id.cardView);
            }
        }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public VectorViewHolder onCreateViewHolder(ViewGroup viewGroup,int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_vector, viewGroup, false);
        v.setOnClickListener(this);
        return new VectorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final VectorViewHolder viewHolder, int i) {
        viewHolder.lbl_titulo.setText("Vector "+String.valueOf(i+1));
        viewHolder.lbl_magnitud.setText(String.valueOf(items.get(i).getMagnitud()));
        final Vector vector = items.get(i);

        viewHolder.lbl_angulo.setText(String.valueOf(items.get(i).getAngulo()));
    }
}
