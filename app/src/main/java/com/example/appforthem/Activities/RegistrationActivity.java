package com.example.appforthem.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.appforthem.Clases.BackendlessSettings;
import com.example.appforthem.Clases.LoadingCallback;
import com.example.appforthem.Clases.Validator;
import com.example.appforthem.R;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Backendless.initApp(getApplicationContext(), BackendlessSettings.APPLICATION_ID, BackendlessSettings.ANDROID_SECRET_KEY);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Button registerButton = findViewById(R.id.registerButton);
       // View.OnClickListener registerButtonClickListener = createRegisterButtonClickListener();
      //  registerButton.setOnClickListener(registerButtonClickListener);
    }

    public boolean isRegistrationValuesValid(CharSequence name, CharSequence last_name, CharSequence email, CharSequence password,
                                             CharSequence passwordConfirm) {
        return Validator.isNameValid(this, name)
                && Validator.isLastNameValid(this, last_name)
                && Validator.isEmailValid(this, email)
                && Validator.isPasswordValid(this, password)
                && isPasswordsMatch(password, passwordConfirm);
    }

    public boolean isPasswordsMatch(CharSequence password, CharSequence passwordConfirm) {
        if (!TextUtils.equals(password, passwordConfirm)) {
            Toast.makeText(this, getString(R.string.warning_passwords_do_not_match), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public void registerUser(String name, String lastname, String email, String password,
                             AsyncCallback<BackendlessUser> registrationCallback) {
        BackendlessUser user = new BackendlessUser();
        user.setEmail(email);
        user.setPassword(password);
        user.setProperty("name", name);
        user.setProperty("last_name", lastname);

        //Backendless handles password hashing by itself, so we don't need to send hash instead of plain text
        Backendless.UserService.register(user, registrationCallback);
    }
/*
    public LoadingCallback<BackendlessUser> createRegistrationCallback() {
        return new LoadingCallback<BackendlessUser>(this, progre) {
            @Override
            public void handleResponse(BackendlessUser registeredUser) {
                super.handleResponse(registeredUser);
                Toast.makeText(RegistrationActivity.this, String.format(getString(R.string.info_registered), registeredUser.getObjectId()), Toast.LENGTH_LONG).show();
                finish();
            }
        };
    }
*/


/*
    public View.OnClickListener createRegisterButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameField = findViewById(R.id.nameField);
                EditText lastnameField = findViewById(R.id.lastnameField);
                EditText emailField = findViewById(R.id.emailField);
                EditText passwordField = findViewById(R.id.passwordField);
                EditText passwordConfirmField = findViewById(R.id.passwordConfirmField);

                CharSequence name = nameField.getText();
                CharSequence last_name = lastnameField.getText();
                CharSequence email = emailField.getText();
                CharSequence password = passwordField.getText();
                CharSequence passwordConfirmation = passwordConfirmField.getText();

                if (isRegistrationValuesValid(name, last_name, email, password, passwordConfirmation)) {
                    LoadingCallback<BackendlessUser> registrationCallback = createRegistrationCallback();

                    registrationCallback.showLoading();
                    registerUser(name.toString(), last_name.toString(), email.toString(), password.toString(), registrationCallback);
                }
            }
        };
    }*/

}
