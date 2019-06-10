package com.example.appforthem.Clases;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appforthem.R;

import java.io.File;
import java.util.ArrayList;

import static com.example.appforthem.Activities.HomeActivity.FOLDER_AUDIO;

public class AdapterForAudio extends RecyclerView.Adapter<AdapterForAudio.ViewHolder> {
    Context context;
    File file = new File(FOLDER_AUDIO);
    ArrayList<String> list_audios;

    public AdapterForAudio(Context context) {
        this.context=context;
        loadAudios(file);
    }

    @Override
    public AdapterForAudio.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View containerAudios = LayoutInflater.from(context).inflate(R.layout.container_audio, viewGroup, false);
        return new ViewHolder(containerAudios);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForAudio.ViewHolder viewHolder, int i) {
        viewHolder.name_audio.setText(list_audios.get(i));

    }

    @Override
    public int getItemCount() {
        return list_audios.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_audio;

        public ViewHolder(View itemView) {
            super(itemView);
            name_audio = itemView.findViewById(R.id.name_audio);
        }
    }

    private void loadAudios(File file) {
        if (file.exists()) {
            File[] filesInside = file.listFiles();
            if (filesInside != null) {
                list_audios = new ArrayList<>();
                for (File eachFile : filesInside) {
                    list_audios.add(eachFile.getName());
                }
            }
        }
    }
}
