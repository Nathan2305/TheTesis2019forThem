package com.example.appforthem.Clases;

import android.animation.Animator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alespero.expandablecardview.ExpandableCardView;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.property.UserProperty;
import com.example.appforthem.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomAdapterHelpers extends BaseAdapter {
    Context context;
    List<Helper> list;
    List<BackendlessUser> listHelpers;

    public CustomAdapterHelpers(Context context, List<Helper> list) {
        this.context = context;
        this.list = list;
        listHelpers = findHelpersFromBackend(list);
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

    private List<BackendlessUser> findHelpersFromBackend(List<Helper> list) {
        Backendless.initApp(context, BackendlessSettings.APPLICATION_ID_HELPERS, BackendlessSettings.ANDROID_SECRET_KEY_HELPERS);
        //Backendless.Data.mapTableToClass("Users", BackendlessUser.class);

        List<BackendlessUser> listaHelper = new ArrayList<>();
        for (Helper helper:list){
            Backendless.Data.of(BackendlessUser.class).findById(helper.getObbjectId(), new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser response) {
                    System.out.println("DATA -" + response.getEmail());
                }

                @Override
                public void handleFault(BackendlessFault fault) {

                }
            });
        }

        return listaHelper;
    }
}


