package org.feathersjs.client.plugins.authentication;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import org.feathersjs.client.Feathers;
import org.feathersjs.client.plugins.storage.IStorageProvider;
import org.feathersjs.client.plugins.storage.InMemoryStorageProvider;
import org.feathersjs.client.service.FeathersService;
import org.feathersjs.client.interfaces.IFeathersConfigurable;

import org.json.JSONException;
import org.json.JSONObject;

public class FeathersAuthentication extends IFeathersConfigurable {

    private Feathers mApp;
    private Context mContext;
    private IStorageProvider mStorage;

    public void setApp(Feathers feathers) {
        mApp = feathers;
    }

    public enum AuthenticationType {
        Local,
        Token
    }

    private AuthenticationOptions mOptions;

    //TODO: Provide access to serialized user model and token?



    public IStorageProvider getStorage(IStorageProvider provider) {
        if (provider != null)
            return provider;

        return new InMemoryStorageProvider();
    }

    public String getJWT() {
        if(mStorage.getItem(mOptions.tokenKey) != null) {
            return (String)mStorage.getItem(mOptions.tokenKey);
        }
        return null;
    }



    public FeathersAuthentication(Feathers app) {
        this(null, app);
    }

    public FeathersAuthentication(AuthenticationOptions options, Feathers app) {

        mApp = app;
//        mContext = context;
        mOptions = new AuthenticationOptions();
        mOptions.cookie = "feathers-jwt";
        mOptions.tokenKey = "feathers-jwt";
        mOptions.localEndpoint = "/auth/local";
        mOptions.tokenEndpoint = "/auth/token";
        mOptions.usernameField = "email";

        if (options != null) {
            if (options.cookie != null) {
                mOptions.cookie = options.cookie;
            }

            if (options.tokenKey != null) {
                mOptions.tokenKey = options.tokenKey;
            }

            if (options.localEndpoint != null) {
                mOptions.localEndpoint = options.localEndpoint;
            }

            if (options.tokenEndpoint != null) {
                mOptions.tokenEndpoint = options.tokenEndpoint;
            }

            if (options.usernameField != null) {
                mOptions.usernameField = options.usernameField;
            }

            //TODO: If no storage set, use the passed in storage
            if (mApp.get("storage") == null) {
                mApp.set("storage", getStorage(options.storage));
            }
        } else {
            if (mApp.get("storage") == null) {
                mApp.set("storage", getStorage(null));
            }
        }

        mStorage = (IStorageProvider) mApp.get("storage");

//        mApp.use(mOptions.localEndpoint, JSONObject.class);
//        mApp.use(mOptions.tokenEndpoint, JSONObject.class);

        //TODO: Add hooks to populate header and params
    }

    public void authenticate(AuthenticationType authType, String identifier, String password, final FeathersService.FeathersCallback cb) {

        JSONObject payload = new JSONObject();
        try {
            payload.put("type", authType.name().toLowerCase());
            payload.put(mOptions.usernameField, identifier);
            payload.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String endPoint;
        if (authType == AuthenticationType.Local) {
            endPoint = mOptions.localEndpoint;
        } else if (authType == AuthenticationType.Token) {
            endPoint = mOptions.tokenEndpoint;
        } else {
            throw new UnsupportedOperationException("Unsupported authentication 'type':" + authType);
        }

        Log.d("feathers-auth", "authenticate | " + mStorage.getItem(mOptions.tokenKey));

        //TODO: Get token from storage and attempt login if present

        mApp.service(endPoint, JSONObject.class).create(payload, new FeathersService.FeathersCallback<JSONObject>() {

            @Override
            public void onSuccess(JSONObject t) {
                Log.d("feathers-auth", "authenticate:onSuccess | " + t);
                String token = t.optString("token");
                mStorage.setItem(mOptions.tokenKey, token);
                cb.onSuccess(t);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("feathers-auth", "authenticate:onError | " + errorMessage);
                cb.onError(errorMessage);
            }
        });
    }

    public void logout() {
        //TODO: Clear the auth token
        //TODO: Clear the user
        mApp.set("user", null);
        mApp.set("token", null);

        //TODO: Logout the socket
    }
}