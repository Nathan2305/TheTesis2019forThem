package com.example.appforthem.Clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appforthem.R;

public class CustomAdapterOptions extends BaseAdapter {
    Context context;
    int[] images;
    String[] titulo;
    LayoutInflater inflater;

    public CustomAdapterOptions(Context context, int[] images,String[] titulo) {
        this.context = context;
        this.images = images;
        this.titulo=titulo;

    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            inflater = (LayoutInflater.from(context));
            view = inflater.inflate(R.layout.gridview_home, null); // inflate the layout
            holder = new ViewHolder();
            holder.fotoOpcion = view.findViewById(R.id.fotoOpcion);
            holder.textOpcion = view.findViewById(R.id.textOpcion);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.fotoOpcion.setImageResource(images[position]);
        holder.textOpcion.setText(titulo[position]);
        return view;
    }

    private static class ViewHolder {
        private ImageView fotoOpcion;
        private TextView textOpcion;

    }
}
