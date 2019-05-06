package com.example.appforthem.Clases;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appforthem.R;

import java.util.ArrayList;

public class AdapterForAjustes extends RecyclerView.Adapter<AdapterForAjustes.ViewHolder> {
    private Context context;
    ArrayList<Ajuste> listaAjustes;
    private OnItemClickListener mListener;

    public AdapterForAjustes(Context context) {
        this.context = context;
        initList();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lista_ajustes, viewGroup, false);
        return new ViewHolder(v,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.item.setText(listaAjustes.get(i).getTitle());
        viewHolder.icon.setImageResource(listaAjustes.get(i).getIcon());
        viewHolder.itemView.setTag(listaAjustes);
    }

    @Override
    public int getItemCount() {
        return listaAjustes.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        //   public ImageView foto_helper;
        public TextView item;
        public ImageView icon;

        public ViewHolder(View itemView,final OnItemClickListener listener) {
            super(itemView);
            item = itemView.findViewById(R.id.title);
            icon = itemView.findViewById(R.id.icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }


    private void initList() {
        listaAjustes = new ArrayList<>();
        listaAjustes.add(new Ajuste("Alerta", R.drawable.alerta));
        listaAjustes.add(new Ajuste("Contactos", R.drawable.map_icon));
        listaAjustes.add(new Ajuste("Otro", R.drawable.key_icon));
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
