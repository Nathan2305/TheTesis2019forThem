package com.example.appforthem.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.example.appforthem.Clases.BackendlessSettings;
import com.example.appforthem.Clases.LoadingCallback;
import com.example.appforthem.Clases.UserSessionManager;
import com.example.appforthem.R;
import com.example.appforthem.Clases.Validator;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final int REGISTER_REQUEST_CODE = 1;
    EditText emailField;
    EditText passwordField;
    Button loginButton;
    public static BackendlessUser backendlessUser;
    UserSessionManager userSessionManager;
    ProgressBar progressBar;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Backendless.initApp(getApplicationContext(), BackendlessSettings.APPLICATION_ID, BackendlessSettings.ANDROID_SECRET_KEY);
        progressBar = findViewById(R.id.pbarLogin);
        constraintLayout = findViewById(R.id.parent);
        userSessionManager = new UserSessionManager(getApplicationContext());
        if (userSessionManager.isLoggedIn()) {
            backendlessUser = userSessionManager.getBackendlessUser();
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }
        emailField = findViewById(R.id.mail);
        passwordField = findViewById(R.id.clave);
        loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(createLoginButtonListener());
        passwordField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    CharSequence email = emailField.getText();
                    CharSequence password = passwordField.getText();
                    if (isLoginValuesValid(email, password)) {

                        LoadingCallback<BackendlessUser> loginCallback = createLoginCallback(progressBar);
                        loginCallback.showLoading();
                        loginUser(email.toString(), password.toString(), loginCallback);
                    }
                    passwordField.clearFocus();
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(passwordField.getWindowToken(), 0);
                    return true;
                }
                return false;

            }
        });
    }

    public void makeRegistrationLink() {
        SpannableString registrationPrompt = new SpannableString(getString(R.string.register_prompt));

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startRegistrationActivity();
            }
        };

        String linkText = getString(R.string.register_link);
        int linkStartIndex = registrationPrompt.toString().indexOf(linkText);
        int linkEndIndex = linkStartIndex + linkText.length();
        registrationPrompt.setSpan(clickableSpan, linkStartIndex, linkEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView registerPromptView = findViewById(R.id.registerPromptText);
        registerPromptView.setText(registrationPrompt);
        registerPromptView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public View.OnClickListener createLoginButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailField = findViewById(R.id.mail);
                EditText passwordField = findViewById(R.id.clave);

                CharSequence email = emailField.getText();
                CharSequence password = passwordField.getText();

                if (isLoginValuesValid(email, password)) {
                    constraintLayout.animate().alpha(0.3f);
                    LoadingCallback<BackendlessUser> loginCallback = createLoginCallback(progressBar);
                    loginCallback.showLoading();
                    loginUser(email.toString(), password.toString(), loginCallback);
                }
            }
        };
    }


    public void startRegistrationActivity() {
        Intent registrationIntent = new Intent(this, RegistrationActivity.class);
        startActivityForResult(registrationIntent, REGISTER_REQUEST_CODE);
    }

    public boolean isLoginValuesValid(CharSequence email, CharSequence password) {
        return Validator.isEmailValid(this, email) && Validator.isPasswordValid(this, password);
    }

    public LoadingCallback<BackendlessUser> createLoginCallback(ProgressBar progressBar) {
        return new LoadingCallback<BackendlessUser>(this, progressBar, constraintLayout) {
            @Override
            public void handleResponse(BackendlessUser loggedInUser) {
                super.handleResponse(loggedInUser);
                userSessionManager = new UserSessionManager(getApplicationContext());
                userSessionManager.saveLogin();
                userSessionManager.saveBackendlessUser(loggedInUser);
                backendlessUser = loggedInUser;
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        };
    }

    public void loginUser(String email, String password, AsyncCallback<BackendlessUser> loginCallback) {
        Backendless.UserService.login(email, password, loginCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REGISTER_REQUEST_CODE:
                    String email = data.getStringExtra(BackendlessUser.EMAIL_KEY);
                    EditText emailField = findViewById(R.id.emailField);
                    emailField.setText(email);

                    EditText passwordField = findViewById(R.id.passwordField);
                    passwordField.requestFocus();

                    Toast.makeText(this, getString(R.string.info_registered_success), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public View.OnClickListener createLoginWithFacebookButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingCallback<BackendlessUser> loginCallback = createLoginCallback(progressBar);

                loginCallback.showLoading();
                loginFacebookUser(loginCallback);
            }
        };
    }

    public void loginFacebookUser(AsyncCallback<BackendlessUser> loginCallback) {
        Map<String, String> fieldsMappings = new HashMap<>();
        fieldsMappings.put("name", "name");
        Backendless.UserService.loginWithFacebook(this, null, fieldsMappings, Collections.<String>emptyList(), loginCallback);
    }

}
