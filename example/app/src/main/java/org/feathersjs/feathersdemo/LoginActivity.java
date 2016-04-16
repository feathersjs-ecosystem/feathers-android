package org.feathersjs.feathersdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import org.feathersjs.client.Feathers;
import org.feathersjs.client.service.FeathersService;

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

        Feathers.getInstance().authenticate(mUsernameEditText.getText().toString(), mPasswordEditText.getText().toString(), new FeathersService.FeathersCallback() {
            @Override
            public void onSuccess(Object t) {
                Log.d("LoginActivity", "onSuccess | " + t.toString());
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(String errorMessage) {
                Log.d("LoginActivity", "onError | " + errorMessage);
            }
        });
    }
}