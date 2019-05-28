package com.example.appforthem.Clases;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;

import com.example.appforthem.R;


public class AdapterForSheetOption extends RecyclerView.Adapter<AdapterForSheetOption.ViewHolder> {
    Context context;
    int opc[];
    int img[];
    private OnItemClickListener mListener;

    public AdapterForSheetOption(Context context) {
        this.context = context;
        opc = new int[]{R.string.pin, R.string.clave, R.string.patron};
        img = new int[]{R.drawable.campana, R.drawable.password_icon, R.drawable.patron_icon};

    }

    @Override
    public long getItemId(int position) {
        return opc[position];
    }

    @Override
    public AdapterForSheetOption.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {// rellena el layout CardViewHolder
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sheet_recycler, viewGroup, false);
        return new ViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(AdapterForSheetOption.ViewHolder viewHolder, int i) {
        viewHolder.imageView.setImageResource(img[i]);
        viewHolder.textView.setText(opc[i]);
        viewHolder.itemView.setTag(opc);
    }

    @Override
    public int getItemCount() {
        return opc.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public ViewHolder(View itemView,final OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.icon_sheet);
            textView = itemView.findViewById(R.id.text_sheet);
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

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

}
