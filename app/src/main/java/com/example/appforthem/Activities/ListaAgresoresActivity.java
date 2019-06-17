package com.example.appforthem.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.example.appforthem.Clases.AdapterForAgresores;
import com.example.appforthem.R;
import com.github.ybq.android.spinkit.style.FadingCircle;

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
        agresores.setLayoutManager(layoutManager);
        //progressBar.setVisibility(View.VISIBLE);
        RecyclerView.Adapter adapter = new AdapterForAgresores(getApplicationContext())/*, response)*/;
        agresores.setAdapter(adapter);
        /*Backendless.Data.of(Agresor.class).find(new AsyncCallback<List<Agresor>>() {
            @Override
            public void handleResponse(List<Agresor> response) {
                int size = response.size();
                if (size > 0) {
                    RecyclerView.Adapter adapter = new AdapterForAgresores(getApplicationContext(), response);
                    agresores.setLayoutManager(layoutManager);
                    agresores.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                } else {
                    Utils.showToast(getApplicationContext(),"No se han registrado agresores aún ");
                    progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void handleFault(BackendlessFault fault) {
                Utils.showToast(getApplicationContext(),"Erros mostrando agresores - "+fault.getMessage());
            }
        });*/
    }
}
