package com.example.appforthem.Clases;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class AdapterForAgresores extends RecyclerView.Adapter<AdapterForAgresores.ViewHolder> {

    BackendlessGeoUtils backendlessSettings = new BackendlessGeoUtils();
    List<Agresor> agresorList;
    Context context;

    public AdapterForAgresores(Context context) {
        this.context=context;
        agresorList = backendlessSettings.getAgresores();
    }

    @NonNull
    @Override
    public AdapterForAgresores.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForAgresores.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
