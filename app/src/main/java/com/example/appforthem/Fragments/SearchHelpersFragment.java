package com.example.appforthem.Fragments;


import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.appforthem.Clases.CustomAdapterHelpers;
import com.example.appforthem.Clases.CustomAdapterHelpers_toSearch;
import com.example.appforthem.Clases.Helper;
import com.example.appforthem.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchHelpersFragment extends Fragment {

    private EditText email_helper;
    private RecyclerView rviewGroup;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public SearchHelpersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_helpers, container, false);
        rviewGroup = view.findViewById(R.id.rview_helpers);
        rviewGroup.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rviewGroup.setLayoutManager(layoutManager);

        email_helper = view.findViewById(R.id.email_helper);
        email_helper.requestFocus();
        email_helper.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String email = email_helper.getText().toString();
                    showHelpers(email);
                    email_helper.clearFocus();
                    InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(email_helper.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    public void showHelpers(String email) {
        StringBuilder whereClause = new StringBuilder();
        whereClause.append("email='").append(email).append("'");
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause.toString());
        Backendless.Data.of(Helper.class).find(queryBuilder, new AsyncCallback<List<Helper>>() {
            @Override
            public void handleResponse(List<Helper> response) {
                mAdapter = new CustomAdapterHelpers_toSearch(getActivity(), response);
                rviewGroup.setAdapter(mAdapter);
            }
            @Override
            public void handleFault(BackendlessFault fault) {
            }
        });
    }

}
