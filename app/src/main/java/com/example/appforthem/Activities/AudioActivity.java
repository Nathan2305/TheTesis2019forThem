package com.example.appforthem.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.appforthem.Clases.AdapterForAudio;
import com.example.appforthem.R;

public class AudioActivity extends AppCompatActivity {
    RecyclerView recycler_audio;
    RecyclerView.LayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        recycler_audio=findViewById(R.id.recycler_audio);
        linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        recycler_audio.setLayoutManager(linearLayoutManager);
        RecyclerView.Adapter adapter=new AdapterForAudio(getApplicationContext());
        recycler_audio.setAdapter(adapter);
    }
}
