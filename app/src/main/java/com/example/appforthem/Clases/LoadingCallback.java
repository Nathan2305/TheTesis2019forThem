package com.example.appforthem.Clases;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.github.ybq.android.spinkit.style.Circle;

public class LoadingCallback<T> implements AsyncCallback<T> {

    private Context context;
    private ProgressBar progressBar;
    private Circle circle;

    /**
     * Create an instance with message "Loading...".
     *
     * @param context context to which ProgressDialog should be attached
     */


    /**
     * Creates an instance with given message.
     *
     * @param context context to which ProgressDialog should be attached
     */
    public LoadingCallback(Context context, ProgressBar aux_progressBar) {
        this.context = context;
        this.circle = new Circle();
        this.progressBar = aux_progressBar;
        // progressDialog = new ProgressDialog( context );
        //progressDialog.setMessage( loadingMessage );
    }

    @Override
    public void handleResponse(T response) {
        //progressDialog.dismiss();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void handleFault(BackendlessFault fault) {
        progressBar.setVisibility(View.GONE);
        DialogHelper.createErrorDialog(context, "BackendlessFault", fault.getMessage()).show();
    }

    /**
     * Shows ProgressDialog.
     */
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgressDrawable(circle);
        //progressDialog.show();
    }

    /**
     * Hides ProgressDialog.
     */
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }
}
