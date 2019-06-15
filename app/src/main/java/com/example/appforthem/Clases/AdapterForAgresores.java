package com.example.appforthem.Clases;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;
import com.example.appforthem.R;
import java.util.ArrayList;


public class AdapterForAgresores extends RecyclerView.Adapter<AdapterForAgresores.ViewHolder> {

    //List<Agresor> agresorList;
    Context context;
    ArrayList<String> listurlVideo;

    public AdapterForAgresores(Context context/*,List<Agresor> agresorList*/) {
        this.context = context;
        listurlVideo = new ArrayList<>();
        Uri uri = Uri.parse("https://backendlessappcontent.com/2E3D890E-D735-D791-FF48-139CA7949A00/3C7C3F6F-AF65-8F0C-FFA1-48D60886BA00/files/videos/xvideos.com_6acb909fb7b7113241d60b2511e35fe8.mp4");
        listurlVideo.add(uri.getPath());
        // this.agresorList=agresorList;

    }

    @NonNull
    @Override
    public AdapterForAgresores.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_agresores, viewGroup, false);
        return new AdapterForAgresores.ViewHolder(v);
    }

    public int getItemCount() {
        return listurlVideo.size();
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForAgresores.ViewHolder viewHolder, int i) {
        //Picasso.get().load(agresorList.get(i).getPhoto_agresor()).transform(new CircleTransform()).into(viewHolder.imageView);
        //Picasso.get().load(listurlVideo.get(i).getPhoto_agresor()).into(viewHolder.imageView);
        viewHolder.videoView.setVideoPath(listurlVideo.get(i));
        MediaController mediaController = new MediaController(context);
        mediaController.setAnchorView(viewHolder.videoView);
        viewHolder.videoView.setMediaController(mediaController);
        viewHolder.videoView.start();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        /* public ImageView imageView;
         public TextView textView;*/
        VideoView videoView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            //textView = itemView.findViewById(R.id.nombre_agresor);
        }
    }
}
