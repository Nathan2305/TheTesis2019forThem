package com.example.appforthem.Clases;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alespero.expandablecardview.ExpandableCardView;
import com.example.appforthem.R;

import java.util.List;
import java.util.Random;

public class CustomAdapterHelpers_toSearch extends RecyclerView.Adapter<CustomAdapterHelpers_toSearch.ViewHolder> {
    List<Helper> lista_helpers;
    static Context context;


    public CustomAdapterHelpers_toSearch(Context context, List<Helper> lista_helpers) {
        this.context = context;
        this.lista_helpers = lista_helpers;

    }

    @Override
    public long getItemId(int position) {
        return lista_helpers.get(position).getEmail().hashCode();
    }

    @Override
    public CustomAdapterHelpers_toSearch.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {// rellena el layout CardViewHolder
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_helpers_to_search, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomAdapterHelpers_toSearch.ViewHolder viewHolder, int i) {
        Helper helper = lista_helpers.get(i);
        // viewHolder.foto_helper.set(item.getName());
       // viewHolder.datos_helper.setText(helper.getName() + " " + helper.getLast_name());
        viewHolder.itemView.setTag(helper);
    }

    @Override
    public int getItemCount() {
        return lista_helpers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //   public ImageView foto_helper;
        public TextView datos_helper;
        public CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            datos_helper = itemView.findViewById(R.id.datos_helper);
            cardView=itemView.findViewById(R.id.card);
           // cardView.setCardBackgroundColor(context.getResources().getColor(getRandomColor()));

        }
    }

    public static int getRandomColor(){
        int color;
        Random random=new Random();
        int colors[]=new int[]{R.color.colorAccent,R.color.colorLila,R.color.colorbuttonLogin,
                                R.color.colorAmarillo,R.color.colorTwitter,R.color.colorNaranja};
        color= random.nextInt(6);
        return colors[color];
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        animateCircularReveal(holder.cardView);
    }

    public void animateCircularReveal(View view){
        int centerX=0;
        int centerY=0;
        int startRadius=0;
        int endRadius=Math.max(view.getWidth(),view.getHeight());
        Animator animator= ViewAnimationUtils.createCircularReveal(view,centerX,centerY,startRadius,endRadius);
        view.setVisibility(View.VISIBLE);
        animator.start();
    }
}
