package org.feathersjs.client.plugins.providers;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.feathersjs.client.Feathers;
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

public class FeathersRestClient extends FeathersBaseClient {

    private final String TAG = "feathers-rest";
    private AsyncHttpClient mClient;

    public FeathersRestClient(Feathers feathers, FeathersAuthentication authentication, AsyncHttpClient client) {
        mFeathers = feathers;
//        mBaseUrl = baseUrl;
//        mServiceName = serviceName;
        gson = new GsonBuilder().create();
        mClient = client;
        mAuthentication = authentication;

//        mServiceUrl = this.mBaseUrl + "/" + this.mServiceName;

//        if (client == null) {
//            mClient = new AsyncHttpClient();
//        }
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
        AsyncHttpClient client = new AsyncHttpClient();
//        client.addHeader("Content-Type", "application/json");
        if (mAuthentication != null && mAuthentication.getJWT() != null) {
//            client.removeHeader("Authorization");
            client.addHeader("Authorization", mAuthentication.getJWT());
        }

        client.addHeader("Accept", "application/json");
        return client;
    }

    @Override
    public <J> void find(String baseUrl, String serviceName, Map<String, String> params, final FeathersCallback<Result<J>> cb, final Class<J> jClass) {
        String url = baseUrl + "/" + serviceName;
        Log.d("REST:FIND", url);
        getClientWithHeaders().get(url, getListHandler(cb, ServiceEvent.FIND, jClass));
    }

    @Override
    public <J> void get(String baseUrl, String serviceName, String id, final FeathersCallback<J> cb, Class<J> jClass) {
        String url = getURLForResource(baseUrl, serviceName, id);
        Log.d("REST:GET", url);
        getClientWithHeaders().get(url, getHandler(cb, ServiceEvent.GET, jClass));
    }

    @Override
    public <J> void remove(String baseUrl, String serviceName, String id, final FeathersCallback<J> cb, final Class<J> jClass) {
        String url = getURLForResource(baseUrl, serviceName, id);
        Log.d("REST:DELETE", url);
        getClientWithHeaders().delete(url, getHandler(cb, ServiceEvent.REMOVE, jClass));
    }

    @Override
    public <J, K> void create(String baseUrl, String serviceName, J item, final FeathersCallback<K> cb, final Class<K> jClass) {
        String url = baseUrl + "/" + serviceName;
        Log.d("REST:CREATE", url);
        StringEntity entity = Serialization.getEntityForObject(item, gson);
        logEntity(entity);
        getClientWithHeaders().post(null, url, entity, "application/json", getHandler(cb, ServiceEvent.CREATE, jClass));
    }

    @Override
    public <J> void update(String baseUrl, String serviceName, String id, J item, final FeathersCallback<J> cb, final Class<J> jClass) {
        String url = getURLForResource(baseUrl, serviceName, id);
        Log.d("REST:UPDATE", url);

        StringEntity entity = Serialization.getEntityForObject(item, gson);
        logEntity(entity);

        getClientWithHeaders().put(null, url, entity, "application/json", getHandler(cb, ServiceEvent.UPDATE, jClass));
    }

    @Override
    public <J> void patch(String baseUrl, String serviceName, String id, JSONObject item, final FeathersCallback<J> cb, final Class<J> jClass) {
        String url = getURLForResource(baseUrl, serviceName, id);
        Log.d("REST:PATCH", url);

        StringEntity entity = Serialization.getEntityForObject(item, gson);
        logEntity(entity);

        getClientWithHeaders().patch(null, url, entity, "application/json", getHandler(cb, ServiceEvent.PATCH, jClass));
    }

    private void logEntity(StringEntity entity) {
        try {
            Log.d("logEntity", entity.getContent().toString());
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

    private String getURLForResource(String baseUrl, String serviceName, String id) {
        return baseUrl + "/" + serviceName + "/" + id;
    }




}
