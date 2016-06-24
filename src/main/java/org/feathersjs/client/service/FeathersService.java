package org.feathersjs.client.service;

import org.feathersjs.client.plugins.providers.IFeathersProvider;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FeathersService<T> extends IFeathersService<T> {

    private final String TAG = "!FeathersService";

    private IFeathersProvider mProvider;
    private final String mServiceName;
    private final String mBaseUrl;
    private final Class<T> mModelClass;

    public interface FeathersCallback<N> {
        void onError(String errorMessage);
        void onSuccess(N t);
    }

    public FeathersService(String baseUrl, String serviceName, Class<T> modelClass) {

        mBaseUrl = baseUrl;
        mServiceName = serviceName;
        mModelClass = modelClass;
    }

    public String getName() {
        return mServiceName;
    }

    public Class getModelClass() {
        return mModelClass;
    }

    public String getBaseUrl() {
        return mBaseUrl;
    }

    public void setProvider(final IFeathersProvider provider) {
        mProvider = provider;
    }

    public IFeathersProvider getProvider() {
        return mProvider;
    }

    @Override
    public void find(final Map<String, String> params, final FeathersCallback<Result<T>> cb) {
            mProvider.find(mBaseUrl, mServiceName, params, cb, mModelClass);
    }

//     Support passing no params
    @Override
    public void find(FeathersCallback<Result<T>> cb) {
        find(new HashMap<String, String>(), cb);
    }

    @Override
    public void get(String id, FeathersCallback<T> cb) {
        mProvider.get(mBaseUrl, mServiceName, id, cb, mModelClass);
    }

    @Override
    public void remove(String id, FeathersCallback<T> cb) {
        mProvider.remove(mBaseUrl, mServiceName, id, cb, mModelClass);
    }

    @Override
    public <J> void create(J item, FeathersCallback<T> cb) {
        mProvider.create(mBaseUrl, mServiceName, item, cb, mModelClass);
    }

    @Override
    public void update(String id, T item, FeathersCallback<T> cb) {
        mProvider.update(mBaseUrl, mServiceName, id, item, cb, mModelClass);
    }

    @Override
    public void patch(String id, JSONObject item, FeathersCallback<T> cb) {
        mProvider.patch(mBaseUrl, mServiceName, id, item, cb, mModelClass);
    }


    /* Events */
    public <J> FeathersService onCreated(final OnCreatedCallback<J> callback) {
        mProvider.onCreated(mServiceName, callback);
        return this;
    }

    public <J> FeathersService onUpdated(final OnUpdatedCallback<J> callback) {
        mProvider.onUpdated(mServiceName, callback);
        return this;
    }

    public <J> FeathersService onRemoved(final OnRemovedCallback<J> callback) {
        mProvider.onRemoved(mServiceName, callback);
        return this;
    }

    public <J> FeathersService onPatched(final OnPatchedCallback<J> callback) {
        mProvider.onPatched(mServiceName, callback);
        return this;
    }
}