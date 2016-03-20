package org.feathersjs.client.providers;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.feathersjs.client.BaseService;
import org.feathersjs.client.FeathersService;
import org.feathersjs.client.utilities.Serialization;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

import org.feathersjs.client.FeathersService.FeathersCallback;

public class FeathersRestClient<T> extends IFeathersProvider<T> {

    private final String mBaseUrl;
    private final String mServiceName;
    private final Gson gson;
//    private final Type mModelType;
    Class<T> mClass;

    private BaseService<T> restService;

    public FeathersRestClient(String baseUrl, String serviceName, Class<T> clazz) {
        mClass = clazz;
        mBaseUrl = baseUrl;
        mServiceName = serviceName;
        gson = new GsonBuilder().create();
//        mModelType = new TypeToken<T>() {
//        }.getType();

//        RestAdapter restAdapter = new RestAdapter.Builder()
//                .setEndpoint(baseUrl + mServiceName)
//                .build();
//        restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);

//        restService = (BaseService<T>) restAdapter.create(BaseService.class);
//        ;
    }


    @Override
    public void find(Map<String, String> params, final FeathersCallback<List<T>> cb) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(this.mBaseUrl + this.mServiceName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray array) {
                try {
                    Log.d("rest:find:onSuccess", array.toString(2));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                List<T> items = Serialization.deserializeArray(array, mClass, gson);
                cb.onSuccess(items);
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                AsyncHttpClient.log.w("FeathersRest:find", "onFailure(int, Header[], Throwable, JSONObject) was not overriden, but callback was received", throwable);
                cb.onError(throwable.getMessage());
            }
        });
    }

    @Override
    public void get(String id, final FeathersCallback<T> cb) {

        String url = this.mBaseUrl + this.mServiceName + "/" + id;
        Log.d("REST:GET", url);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                T item = gson.fromJson(object.toString(), mClass);
                cb.onSuccess(item);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                AsyncHttpClient.log.w("FeathersRest:get", "onFailure(int, Header[], Throwable, JSONObject) was not overriden, but callback was received", throwable);
                cb.onError(throwable.getMessage());
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                AsyncHttpClient.log.w("FeathersRest:get", "onFailure(int, Header[], Throwable, JSONArray) was not overriden, but callback was received", throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                AsyncHttpClient.log.w("FeathersRest:get", "onFailure(int, Header[], String, Throwable) was not overriden, but callback was received", throwable);
            }


        });
    }

    @Override
    public void remove(String id, final FeathersCallback<T> cb) {
//        BaseService<T> typedRestService = restService;
//        typedRestService.delete(id, new Callback<T>() {
//            @Override
//            public void success(T item, Response response) {
//                cb.onSuccess(item);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                cb.onError(error.getMessage());
//            }
//        });
    }

    @Override
    public void create(T item, final FeathersCallback<T> cb) {
        throw new UnsupportedOperationException();
//        BaseService<T> typedRestService = restService;
//        typedRestService.create(item, new Callback<T>() {
//            @Override
//            public void success(T item, Response response) {
//                cb.onSuccess(item);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                cb.onError(error.getMessage());
//            }
//        });
    }

    @Override
    public void update(String id, T item, final FeathersCallback<T> cb) {
        throw new UnsupportedOperationException();
//        BaseService<T> typedRestService = restService;
//        typedRestService.update(id, item, new Callback<T>() {
//            @Override
//            public void success(T item, Response response) {
//                cb.onSuccess(item);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                cb.onError(error.getMessage());
//            }
//        });
    }

    @Override
    public void patch(String id, T item, final FeathersCallback<T> cb) {
        throw new UnsupportedOperationException();
//        BaseService<T> typedRestService = restService;
//        typedRestService.patch(id, item, new Callback<T>() {
//            @Override
//            public void success(T item, Response response) {
//                cb.onSuccess(item);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                cb.onError(error.getMessage());
//            }
//        });
    }


    @Override
    public void onCreated(final FeathersService.FeathersEventCallback<T> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onUpdated(final FeathersService.FeathersEventCallback<T> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onRemoved(final FeathersService.FeathersEventCallback<T> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onPatched(final FeathersService.FeathersEventCallback<T> callback) {
        throw new UnsupportedOperationException();
    }


}
