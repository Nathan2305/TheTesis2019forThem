package com.example.appforthem.Clases;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.backendless.BackendlessUser;
import com.example.appforthem.R;
import com.squareup.picasso.Picasso;

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
            holder.datos = convertView.findViewById(R.id.datos);
            holder.fotoOpcion = convertView.findViewById(R.id.fotoHelper);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (listHelpers.get(position).getProperty("photo") != null) {
            if (!"".equalsIgnoreCase(listHelpers.get(position).getProperty("photo").toString())) {
                Picasso.get().load(listHelpers.get(position).getProperty("photo").toString()).transform(new CircleTransform()).into(holder.fotoOpcion);
            }
        }
        holder.datos.setText(listHelpers.get(position).getProperty("name").toString());
        return convertView;
    }

    private static class ViewHolder {
        private ImageView fotoOpcion;
        private TextView datos;

    }
}


