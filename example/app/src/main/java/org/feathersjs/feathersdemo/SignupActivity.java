package org.feathersjs.feathersdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import org.feathersjs.client.Feathers;
import org.feathersjs.client.service.FeathersService;
import org.feathersjs.feathersdemo.models.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupActivity extends Activity {

    @Bind(R.id.username)
    EditText mUsernameEditText;

    @Bind(R.id.password)
    EditText mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        mUsernameEditText.setText("testing123@123.com");
        mPasswordEditText.setText("password");
    }

    protected void onDestroy() {
        super.onDestroy();
    }


    @OnClick(R.id.signupButton)
    void signup() {

        final User user = new User();
        user.email = mUsernameEditText.getText().toString();
        user.password =  mPasswordEditText.getText().toString();

        Feathers.getInstance().service("users", User.class).create(user, new FeathersService.FeathersCallback<User>() {
            @Override
            public void onSuccess(User newUser) {
                Log.d("LoginActivity", "create:onSuccess | " + newUser.email);
                Feathers.getInstance().authenticate(user.email, user.password, new FeathersService.FeathersCallback() {
                    @Override
                    public void onSuccess(Object t) {
                        Log.d("LoginActivity", "authenticate:onSuccess | " + t.toString());
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Log.d("LoginActivity", "authenticate:onError | " + errorMessage);
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                Log.d("LoginActivity", "create:onError | " + errorMessage);
            }
        });
    }
}