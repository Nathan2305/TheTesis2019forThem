package com.example.appforthem.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.appforthem.Clases.BackendlessSettings;
import com.example.appforthem.Clases.CustomAdapterHelpers;
import com.example.appforthem.Clases.CustomAdapterHelpers_toSearch;
import com.example.appforthem.Clases.Helper;
import com.example.appforthem.R;
import com.github.ybq.android.spinkit.style.Circle;

import java.util.List;

import static com.example.appforthem.Activities.LoginActivity.backendlessUser;

public class Protector extends AppCompatActivity {

    private FloatingActionButton findProtector;
    private ProgressBar progressBar;
    private GridView gridHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protector);
        gridHelper = findViewById(R.id.gridHelper);
        findProtector = findViewById(R.id.findProtector);
        progressBar = findViewById(R.id.spin_kit);
        Circle circle = new Circle();
        progressBar.setProgressDrawable(circle);
        loadProtectors();
        findProtector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindHelperDialog();
            }
        });
    }

    public void FindHelperDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Protector.this);
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.finder_dialog_protector, null);
        dialog.setView(view);
        dialog.setTitle("Buscar Protector");
        dialog.setCancelable(false);
        final SearchView searchView = view.findViewById(R.id.search_helper);
        int id = searchView.getContext()
                .getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        TextView textView = searchView.findViewById(id);
        textView.setTextColor(Color.BLACK);
        final RecyclerView recyclerView = view.findViewById(R.id.rview);
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                showHelpersDialog(recyclerView, query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        dialog.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        dialog.create();
        dialog.show();


    }

    private void loadProtectors() {
        StringBuilder whereClause = new StringBuilder();
        whereClause.append("Users[helper_email]");
        whereClause.append(".objectId='").append(backendlessUser.getObjectId()).append("'");
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause.toString());
        Backendless.Data.of(Helper.class).find(queryBuilder, new AsyncCallback<List<Helper>>() {
            @Override
            public void handleResponse(List<Helper> response) {
                int size = response.size();
                if (size > 0) {
                    Backendless.initApp(getApplicationContext(), BackendlessSettings.APPLICATION_ID_HELPERS, BackendlessSettings.ANDROID_SECRET_KEY_HELPERS);
                    DataQueryBuilder dataQueryBuilder = DataQueryBuilder.create();
                    StringBuilder sb = new StringBuilder();
                    for (int k = 0; k < size; k++) {
                        if (k == size - 1) {
                            sb.append("email='" + response.get(k).getEmail() + "'");
                        } else {
                            sb.append("email='" + response.get(k).getEmail() + "'" + " OR ");
                        }
                    }
                    dataQueryBuilder.setWhereClause(sb.toString());
                    Backendless.Data.of(BackendlessUser.class).find(dataQueryBuilder, new AsyncCallback<List<BackendlessUser>>() {
                        @Override
                        public void handleResponse(List<BackendlessUser> response) {
                            if (response.size() > 0) {
                                CustomAdapterHelpers adapter = new CustomAdapterHelpers(getApplicationContext(), response);
                                gridHelper.setAdapter(adapter);
                                progressBar.setVisibility(View.GONE);
                            } else {
                                BackendlessSettings.showToast(getApplicationContext(), "Todavía no tienes protectores agregados");
                                progressBar.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            BackendlessSettings.showToast(getApplicationContext(), "Error trying to get Data.. " + fault.getMessage());
                            progressBar.setVisibility(View.GONE);

                        }
                    });

                } else {
                    BackendlessSettings.showToast(getApplicationContext(), "Todavía no tienes protectores agregados");
                    progressBar.setVisibility(View.GONE);

                }
            }
            @Override
            public void handleFault(BackendlessFault fault) {
                BackendlessSettings.showToast(getApplicationContext(), "Error al cargar protectores : " + fault.getMessage());
                progressBar.setVisibility(View.GONE);

            }
        });
    }


    public void showHelpersDialog(final RecyclerView recyclerView, String dataHelper) {
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        DataQueryBuilder query = DataQueryBuilder.create();
        query.setWhereClause("name like  '" + dataHelper + "%'");
        Backendless.Data.of(Helper.class).find(query, new AsyncCallback<List<Helper>>() {
            @Override
            public void handleResponse(List<Helper> response) {
                RecyclerView.Adapter adapter = new CustomAdapterHelpers_toSearch(getApplicationContext(), response);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
            }
        });
    }

    private void showProgressBar() {
        startActivity(new Intent(getApplicationContext(), CustomProgressBar.class));
    }


}
