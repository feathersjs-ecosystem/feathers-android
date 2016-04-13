package org.feathersjs.client.plugins.authentication;

import android.os.Handler;
import android.util.Log;

import org.feathersjs.client.Feathers;
import org.feathersjs.client.plugins.hooks.HookCallback;
import org.feathersjs.client.plugins.hooks.HookDoneCallback;
import org.feathersjs.client.plugins.hooks.HookObject;
import org.feathersjs.client.plugins.storage.IStorageProvider;
import org.feathersjs.client.plugins.storage.InMemoryStorageProvider;
import org.feathersjs.client.service.FeathersService;
import org.feathersjs.client.interfaces.IFeathersConfigurable;

import org.json.JSONException;
import org.json.JSONObject;

public class FeathersAuthentication extends IFeathersConfigurable {

    private Feathers mApp;

    public void setApp(Feathers feathers) {
        mApp = feathers;
    }

    public enum AuthenticationType {
        Local,
        Token
    }


    private AuthenticationOptions mOptions;

    private HookCallback populateParams = new HookCallback() {
        @Override
        public void call(final HookObject t, final HookDoneCallback cb) {
//            Log.d(TAG, "article:before:find hook 1 " + t.context.params);
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    t.context.params.put("user", "USER");
                    t.context.params.put("token", "TOKEN");
                    cb.onDone(t);
                }
            };
            handler.postDelayed(runnable, 3000);
        }
    };

    private HookCallback populateHeader = new HookCallback() {
        @Override
        public void call(final HookObject t, final HookDoneCallback cb) {
//            Log.d(TAG, "article:before:find hook 1 " + t.context.params);


            if (t.context.params.containsKey("token")) {

//                hook.params.headers = Object.assign({}, {
//                        [options.header || 'Authorization']: hook.params.token
//                }, hook.params.headers);
            }

//            Handler handler = new Handler();
//            Runnable runnable = new Runnable() {
//                @Override
//                public void run() {
//                    t.context.params.put("TEST2", "TEST2");
//                    cb.onDone(t);
//                }
//            };
//            handler.postDelayed(runnable, 3000);
        }
    };

//    export function populateParams() {
//        return function(hook) {
//            const app = hook.app;
//
//            Object.assign(hook.params, {
//                    user: app.get('user'),
//                    token: app.get('token')
//            });
//        };
//    }

//    export function populateHeader(options = {}) {
//        return function(hook) {
//            if (hook.params.token) {
//                hook.params.headers = Object.assign({}, {
//                        [options.header || 'Authorization']: hook.params.token
//                }, hook.params.headers);
//            }
//        };
//    }


    public IStorageProvider getStorage(IStorageProvider provider) {
        if (provider != null)
            return provider;

        return new InMemoryStorageProvider();
    }

    public String getJWT() {
        return "jwt-token";
    }

    public class AuthenticationOptions {
        public String cookie;
        public String tokenKey;
        public String localEndpoint;
        public String tokenEndpoint;
        public String usernameField;

        public IStorageProvider storage;
    }

    public FeathersAuthentication(Feathers app) {
        this(null, app);
    }

    public FeathersAuthentication(AuthenticationOptions options, Feathers app) {

        mApp = app;
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

        mApp.use(mOptions.localEndpoint, JSONObject.class);
        mApp.use(mOptions.tokenEndpoint, JSONObject.class);

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


        //TODO: Get token from storage and attempt login if present
        //TODO: Use provider to login via server using auth type
        IStorageProvider storage = (IStorageProvider) mApp.get("storage");
        Log.d("feathers-auth", "authenticate | " + storage.getItem("token"));

//        Feathers.service(endPoint).create();

//        mApp.service(endPoint).create(payload, new FeathersService.FeathersCallback<JSONObject>() {
//
//            @Override
//            public void onSuccess(Result<JSONObject> t) {
//
//            }
//
//            @Override
//            public void onSuccess(JSONObject t) {
//                cb.onSuccess(t);
//            }
//
////            @Override
////            public <J> void onSuccess(Result<J> t) {
////
////            }
//
////            @Override
////            public void onSuccess(JSONObject t) {
////                Log.d("feathers-auth", "authenticate:onSuccess | " + t);
////                //TODO: Save token to storage
////            }
////
////            @Override
////            public void onSuccess(Result<JSONObject> t) {
////
////            }
//
//            @Override
//            public void onError(String errorMessage) {
//                Log.e("feathers-auth", "authenticate:onError | " + errorMessage);
//                cb.onError(errorMessage);
//            }
//        });
    }

    public void logout() {
        //TODO: Clear the auth token
        //TODO: Clear the user
        mApp.set("user", null);
        mApp.set("token", null);

        //TODO: Logout the socket
    }
}


//
//
//import errors from 'feathers-errors';
//        import * as hooks from './hooks';
//        import {
//        connected,
//        authenticateSocket,
//        logoutSocket,
//        getJWT,
//        getStorage,
//        clearCookie
//        } from './utils';


