package org.feathersjs.client.plugins.providers;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

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

import java.io.UnsupportedEncodingException;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class FeathersRestClient<T> extends IFeathersProvider {

    private final String TAG = "feathers-rest";
    private final String mBaseUrl;
    private final String mServiceName;
    private final String mServiceUrl;

    private final Gson gson;
    Class<T> mClass;
    private AsyncHttpClient mClient;


    public FeathersRestClient(String baseUrl, String serviceName, Class<T> clazz, AsyncHttpClient client) {
        mClass = clazz;
        mBaseUrl = baseUrl;
        mServiceName = serviceName;
        gson = new GsonBuilder().create();
        mClient = client;

        mServiceUrl = this.mBaseUrl + "/" + this.mServiceName;

        if (client == null) {
            mClient = new AsyncHttpClient();
        }
    }

    private <J> JsonHttpResponseHandler getHandler(final FeathersCallback<J> cb, final ServiceEvent serviceEvent) {
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                Log.d(TAG, serviceEvent + ":onSuccess:" + object);

//                if (isList) {
//                    Result<T> result = (Result<T>) Serialization.deserializeArray(object, mClass, gson);
//                    Log.d("getHandler", serviceEvent + " | LIST | " + result);
//                    cb.onSuccess(result);
//                } else {

                String objectAsString = gson.toJson(object);
                T item = gson.fromJson(objectAsString, mClass);
//                    cb.onSuccess(item);
//                    J item
//                    Log.d("getHandler", serviceEvent + " | OBJ | " + item);

//                }
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

    private <J> JsonHttpResponseHandler getListHandler(final FeathersCallback<Result<J>> cb, final ServiceEvent serviceEvent) {
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                Log.d(TAG, serviceEvent + ":onSuccess:" + object);
                Result<J> result = (Result<J>) Serialization.deserializeArray(object, mClass, gson);
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
        mClient.addHeader("Accept", "application/json");
        return mClient;
    }

//    @Override
//    public void find(Map params, FeathersCallback cb) {
//
//    }

    @Override
    public <J> void find(Map<String, String> params, final FeathersCallback<Result<J>> cb) {
        Log.d("REST:FIND", mServiceUrl);
        getClientWithHeaders().get(mServiceUrl, getListHandler(cb, ServiceEvent.FIND));
    }

    @Override
    public <J> void get(String id, final FeathersCallback<J> cb) {
        String url = getURLForResource(id);
        Log.d("REST:GET", url);
        getClientWithHeaders().get(url, getHandler(cb, ServiceEvent.GET));
    }

    @Override
    public <J> void remove(String id, final FeathersCallback<J> cb) {
        String url = getURLForResource(id);
        Log.d("REST:DELETE", url);
        getClientWithHeaders().delete(url, getHandler(cb, ServiceEvent.REMOVE));
    }

    @Override
    public <J> void create(J item, final FeathersCallback<J> cb) {

        Log.d("REST:CREATE", mServiceUrl);

        String json = gson.toJson(item);
        StringEntity entity = null;
        try {
            entity = new StringEntity(json);
            getClientWithHeaders().post(null, mServiceUrl, entity, "application/json", getHandler(cb, ServiceEvent.CREATE));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <J> void update(String id, J item, final FeathersCallback<J> cb) {
        String url = getURLForResource(id);
        Log.d("REST:UPDATE", url);
        getClientWithHeaders().put(url, getHandler(cb, ServiceEvent.UPDATE));
    }

    @Override
    public <J> void patch(String id, J item, final FeathersCallback<J> cb) {
        String url = getURLForResource(id);
        Log.d("REST:UPDATE", url);
        getClientWithHeaders().patch(url, getHandler(cb, ServiceEvent.PATCH));
    }


    private String getURLForResource(String id) {
        return this.mServiceUrl + "/" + id;
    }


    /*

    Service events

     */

    //@Override
    public <J> void onCreated(final OnCreatedCallback<J> callback) {
        throw new UnsupportedOperationException();
    }

    //@Override
    public <J> void onUpdated(final OnUpdatedCallback<J> callback) {
        throw new UnsupportedOperationException();
    }

   // @Override
    public <J> void onRemoved(final OnRemovedCallback<J> callback) {
        throw new UnsupportedOperationException();
    }

    //@Override
    public <J> void onPatched(final OnPatchedCallback<J> callback) {
        throw new UnsupportedOperationException();
    }


}
