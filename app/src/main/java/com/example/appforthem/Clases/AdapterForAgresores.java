package com.example.appforthem.Clases;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.appforthem.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterForAgresores extends RecyclerView.Adapter<AdapterForAgresores.ViewHolder> {

    List<Agresor> agresorList;
    Context context;

    public AdapterForAgresores(Context context,List<Agresor> agresorList) {
        this.context = context;
        this.agresorList=agresorList;

    }

    @NonNull
    @Override
    public AdapterForAgresores.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_agresores, viewGroup, false);
        return new AdapterForAgresores.ViewHolder(v);
    }

    public int getItemCount() {
        return agresorList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForAgresores.ViewHolder viewHolder, int i) {
        //Picasso.get().load(agresorList.get(i).getPhoto_agresor()).transform(new CircleTransform()).into(viewHolder.imageView);
        Picasso.get().load(agresorList.get(i).getPhoto_agresor()).into(viewHolder.imageView);
        viewHolder.textView.setText(agresorList.get(i).getName_agresor());
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.photo_agresor);
            textView = itemView.findViewById(R.id.nombre_agresor);
        }
    }
}
