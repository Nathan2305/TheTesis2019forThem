package com.example.appforthem.Clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.appforthem.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterHelpers extends BaseAdapter {
    private Context context;
    private List<BackendlessUser> listHelpers;

    public CustomAdapterHelpers(Context context, List<BackendlessUser> listHelpers) {
        this.context = context;
        this.listHelpers = listHelpers;
    }


    @Override
    public int getCount() {
        return listHelpers.size();
    }

    @Override
    public Object getItem(int position) {
        return listHelpers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listHelpers.get(position).getEmail().hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater.from(context));
            convertView = inflater.inflate(R.layout.list_helpers, null); // inflate the layout
            holder = new ViewHolder();
            //  holder.fotoOpcion = view.findViewById(R.id.fotoOpcion);
            holder.datos = convertView.findViewById(R.id.datos);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.datos.setText(listHelpers.get(position).getProperty("name") + " "
                + listHelpers.get(position).getProperty("last_name"));
        return convertView;
    }

    private static class ViewHolder {
        private ImageView fotoOpcion;
        private TextView datos;

    }
}

