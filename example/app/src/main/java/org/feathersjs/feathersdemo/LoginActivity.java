package org.feathersjs.feathersdemo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;

import org.feathersjs.client.Feathers;
import org.feathersjs.client.plugins.authentication.AuthResponse;
import org.feathersjs.client.service.FeathersService;
import org.feathersjs.feathersdemo.models.User;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends Activity {

    @Bind(R.id.username)
    EditText mUsernameEditText;

    @Bind(R.id.password)
    EditText mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mUsernameEditText.setText("testing123@123.com");
        mPasswordEditText.setText("password");
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.loginButton)
    void login() {

        String username = mUsernameEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();


        Feathers.getInstance().authenticate(username, password, new FeathersService.FeathersCallback<AuthResponse<User>>() {
            @Override
            public void onSuccess(AuthResponse<User> t) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(String errorMessage) {
                Log.d("LoginActivity", "onError | " + errorMessage);

                AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
                builder1.setTitle("Error");
                builder1.setMessage(errorMessage);
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        }, User.class);
    }
}