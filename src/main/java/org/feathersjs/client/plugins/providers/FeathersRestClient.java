package org.feathersjs.client.plugins.providers;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.feathersjs.client.plugins.authentication.FeathersAuthentication;
import org.feathersjs.client.service.FeathersService;
import org.feathersjs.client.service.FeathersService.FeathersCallback;
import org.feathersjs.client.service.OnCreatedCallback;
import org.feathersjs.client.service.OnPatchedCallback;
import org.feathersjs.client.service.OnRemovedCallback;
import org.feathersjs.client.service.OnUpdatedCallback;
import org.feathersjs.client.service.Result;
import org.feathersjs.client.service.ServiceEvent;
import org.feathersjs.client.utilities.Serialization;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class FeathersRestClient extends IFeathersProvider {

    private final String TAG = "feathers-rest";
    private final String mBaseUrl;
    private final String mServiceName;
    private final String mServiceUrl;
    private final FeathersAuthentication mAuthentication;

    private OnCreatedCallback mOnCreatedCallback;
    private OnRemovedCallback mOnRemovedCallback;
    private OnUpdatedCallback mOnUpdatedCallback;
    private OnPatchedCallback mOnPatchedCallback;

    private final Gson gson;
    private AsyncHttpClient mClient;


    public FeathersRestClient(String baseUrl, String serviceName, FeathersAuthentication authentication, AsyncHttpClient client) {
        mBaseUrl = baseUrl;
        mServiceName = serviceName;
        gson = new GsonBuilder().create();
        mClient = client;
        mAuthentication = authentication;

        mServiceUrl = this.mBaseUrl + "/" + this.mServiceName;

        if (client == null) {
            mClient = new AsyncHttpClient();
        }
    }

    private <J> void sendEvent(ServiceEvent event, J item) {
        try {
            if (event == ServiceEvent.CREATE) {
                if (mOnCreatedCallback != null) {
                    mOnCreatedCallback.onCreated(item);
                }
            } else if (event == ServiceEvent.UPDATE) {
                if (mOnUpdatedCallback != null) {
                    mOnUpdatedCallback.onUpdated(item);
                }
            } else if (event == ServiceEvent.REMOVE) {
                if (mOnRemovedCallback != null) {
                    mOnRemovedCallback.onRemoved(item);
                }
            } else if (event == ServiceEvent.PATCH) {
                if (mOnPatchedCallback != null) {
                    mOnPatchedCallback.onPatched(item);
                }
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error sending event:" + event, ex);
        }

    }

    private <J> JsonHttpResponseHandler getHandler(final FeathersCallback<J> cb, final ServiceEvent serviceEvent, final Class<J> jClass) {
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                Log.d(TAG, serviceEvent + ":onSuccess:" + object);
                J item = null;
                if (jClass.isInstance(object)) {
                    item = (J) object;
                } else {
                    item = gson.fromJson(object.toString(), jClass);
                }
                cb.onSuccess(item);

                sendEvent(serviceEvent, item);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(TAG, serviceEvent + ":onFailure1:" + errorResponse);
                cb.onError(throwable.getMessage());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d(TAG, serviceEvent + ":onFailure2:" + errorResponse);
                cb.onError(throwable.getMessage());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, serviceEvent + ":onFailure3:" + responseString);
                cb.onError(throwable.getMessage());
            }
        };
    }

    private <J> JsonHttpResponseHandler getListHandler(final FeathersCallback<Result<J>> cb, final ServiceEvent serviceEvent, final Class<J> jClass) {
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                Log.d(TAG, serviceEvent + ":onSuccess:" + object);
                Result<J> result = Serialization.deserializeArray(object, jClass, gson);
                Log.d("getHandler", serviceEvent + " | LIST | " + result);
                cb.onSuccess(result);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(TAG, serviceEvent + ":onFailure1:" + errorResponse);
                cb.onError(throwable.getMessage());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d(TAG, serviceEvent + ":onFailure2:" + errorResponse);
                cb.onError(throwable.getMessage());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, serviceEvent + ":onFailure3:" + responseString);
                cb.onError(throwable.getMessage());
            }
        };
    }


    private AsyncHttpClient getClientWithHeaders() {
        //TODO: Set custom headers from params
//        AsyncHttpClient client = new SyncHttpClient();
//        client.addHeader("Content-Type", "application/json");
        if (mAuthentication != null && mAuthentication.getJWT() != null) {
            mClient.removeHeader("Authorization");
            mClient.addHeader("Authorization", mAuthentication.getJWT());
        }

        mClient.addHeader("Accept", "application/json");
        return mClient;
    }

    @Override
    public <J> void find(Map<String, String> params, final FeathersCallback<Result<J>> cb, final Class<J> jClass) {
        Log.d("REST:FIND", mServiceUrl);
        getClientWithHeaders().get(mServiceUrl, getListHandler(cb, ServiceEvent.FIND, jClass));
    }

    @Override
    public <J> void get(String id, final FeathersCallback<J> cb, Class<J> jClass) {
        String url = getURLForResource(id);
        Log.d("REST:GET", url);
        getClientWithHeaders().get(url, getHandler(cb, ServiceEvent.GET, jClass));
    }

    @Override
    public <J> void remove(String id, final FeathersCallback<J> cb, final Class<J> jClass) {
        String url = getURLForResource(id);
        Log.d("REST:DELETE", url);
        getClientWithHeaders().delete(url, getHandler(cb, ServiceEvent.REMOVE, jClass));
    }

    @Override
    public <J, K> void create(J item, final FeathersCallback<K> cb, final Class<K> jClass) {
        Log.d("REST:CREATE", mServiceUrl);

        StringEntity entity = Serialization.getEntityForObject(item, gson);
        logEntity(entity);
        getClientWithHeaders().post(null, mServiceUrl, entity, "application/json", getHandler(cb, ServiceEvent.CREATE, jClass));
    }

    @Override
    public <J> void update(String id, J item, final FeathersCallback<J> cb, final Class<J> jClass) {
        String url = getURLForResource(id);
        Log.d("REST:UPDATE", url);
        getClientWithHeaders().put(url, getHandler(cb, ServiceEvent.UPDATE, jClass));
    }

    @Override
    public <J> void patch(String id, JSONObject item, final FeathersCallback<J> cb, final Class<J> jClass) {
        String url = getURLForResource(id);
        Log.d("REST:PATCH", url);

        StringEntity entity = Serialization.getEntityForObject(item, gson);
        logEntity(entity);

        getClientWithHeaders().patch(null, url, entity, "application/json", getHandler(cb, ServiceEvent.PATCH, jClass));
    }

    private void logEntity(StringEntity entity) {
        try {
            Log.d("REST:CREATE", entity.getContent().toString());
            String inputLine;
            BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
            try {
                while ((inputLine = br.readLine()) != null) {
                    System.out.println(inputLine);
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getURLForResource(String id) {
        return this.mServiceUrl + "/" + id;
    }



    /*
        Service events
    */

    //@Override
    public <J> void onCreated(final OnCreatedCallback<J> callback) {
        mOnCreatedCallback = callback;
    }

    //@Override
    public <J> void onUpdated(final OnUpdatedCallback<J> callback) {
        mOnUpdatedCallback = callback;
    }

    // @Override
    public <J> void onRemoved(final OnRemovedCallback<J> callback) {
        mOnRemovedCallback = callback;
    }

    //@Override
    public <J> void onPatched(final OnPatchedCallback<J> callback) {
        mOnPatchedCallback = callback;
    }
}
