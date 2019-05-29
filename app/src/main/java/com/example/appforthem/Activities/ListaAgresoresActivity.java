package com.example.appforthem.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.appforthem.Clases.AdapterForAgresores;
import com.example.appforthem.Clases.Agresor;
import com.example.appforthem.Clases.BackendlessSettings;
import com.example.appforthem.R;
import com.github.ybq.android.spinkit.style.FadingCircle;

import java.util.List;

public class ListaAgresoresActivity extends AppCompatActivity {
    RecyclerView agresores;
    RecyclerView.LayoutManager layoutManager;
    ProgressBar progressBar;
    FadingCircle fadingCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_agresores);
        progressBar = findViewById(R.id.pbAgresores);
        fadingCircle = new FadingCircle();
        progressBar.setProgressDrawable(fadingCircle);
        agresores = findViewById(R.id.agresores);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        progressBar.setVisibility(View.VISIBLE);
        Backendless.Data.of(Agresor.class).find(new AsyncCallback<List<Agresor>>() {
            @Override
            public void handleResponse(List<Agresor> response) {
                int size = response.size();
                if (size > 0) {
                    RecyclerView.Adapter adapter = new AdapterForAgresores(getApplicationContext(), response);
                    agresores.setLayoutManager(layoutManager);
                    agresores.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                } else {
                    BackendlessSettings.showToast(getApplicationContext(),"No se han registrado agresores aún ");
                    progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void handleFault(BackendlessFault fault) {
                BackendlessSettings.showToast(getApplicationContext(),"Erros mostrando agresores - "+fault.getMessage());
            }
        });
    }
}
