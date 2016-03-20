package org.feathersjs.client;

import org.feathersjs.client.providers.FeathersRestClient;
import org.feathersjs.client.providers.FeathersSocketClient;
import org.feathersjs.client.providers.IFeathersProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeathersService<T> implements IFeathersService<T> {

//    private final FeathersSocketClient<T> mSocketProvider;
//    private final FeathersRestClient<T> mRESTProvider;
    private final IFeathersProvider <T> mProvider;
    private final String mServiceName;
    private final String mBaseUrl;

    public interface FeathersCallback<T> {
        void onSuccess(T t);
        void onError(String errorMessage);
    }

    public interface FeathersEventCallback<T> {
        void onSuccess(T t);
    }

    public FeathersService(String baseUrl, String serviceName, Class<T> modelClass) {
        mBaseUrl = baseUrl;
        mServiceName = serviceName;

//        this.mSocketProvider = new FeathersSocketClient<T>(baseUrl, serviceName, modelClass);
        //this.mRESTProvider = new FeathersRestClient<T>(baseUrl, serviceName, modelClass);

        this.mProvider = new FeathersSocketClient<T>(baseUrl, serviceName, modelClass);
    }

    @Override
    public void find(Map<String, String> params, FeathersCallback<List<T>> cb) {
        mProvider.find(params, cb);
    }

    // Support passing no params
    @Override
    public void find(FeathersCallback<List<T>> cb) {
        mProvider.find(new HashMap<String, String>(), cb);
    }

    @Override
    public void get(String id, FeathersCallback<T> cb) {
        mProvider.get(id, cb);
    }

    @Override
    public void remove(String id, FeathersCallback<T> cb) {
        mProvider.remove(id, cb);
    }

    @Override
    public void create(T item, FeathersCallback<T> cb) {
        mProvider.create(item, cb);
    }

    @Override
    public void update(String id, T item, FeathersCallback<T> cb) {
        mProvider.update(id, item, cb);
    }

    @Override
    public void patch(String id, T item, FeathersCallback<T> cb) {
        mProvider.patch(id, item, cb);
    }


    /* Events */
    public void onCreated(final FeathersEventCallback<T> callback) {
        mProvider.onCreated(callback);
    }

    public void onUpdated(final FeathersEventCallback<T> callback) {
        mProvider.onUpdated(callback);
    }

    public void onRemoved(final FeathersEventCallback<T> callback) {
        mProvider.onRemoved(callback);
    }

    public void onPatched(final FeathersEventCallback<T> callback) {
        mProvider.onPatched(callback);
    }
}