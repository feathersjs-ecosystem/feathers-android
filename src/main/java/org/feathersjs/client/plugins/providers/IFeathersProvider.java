package org.feathersjs.client.plugins.providers;

import org.feathersjs.client.interfaces.IFeathersConfigurable;
import org.feathersjs.client.service.FeathersService;
import org.feathersjs.client.service.OnCreatedCallback;
import org.feathersjs.client.service.OnPatchedCallback;
import org.feathersjs.client.service.OnRemovedCallback;
import org.feathersjs.client.service.OnUpdatedCallback;
import org.feathersjs.client.service.Result;
import org.json.JSONObject;

import java.util.Map;


public abstract class IFeathersProvider {

    public abstract <J> void find(String baseUrl, String serviceName, Map<String, String> params, FeathersService.FeathersCallback<Result<J>> cb, Class<J> jClass);

    public abstract <J> void get(String baseUrl, String serviceName, String id, FeathersService.FeathersCallback<J> cb, Class<J> jClass);

    public abstract <J>  void remove(String baseUrl, String serviceName, String id, FeathersService.FeathersCallback<J> cb, Class<J> jClass);

    public abstract <J, K>  void create(String baseUrl, String serviceName, J item, FeathersService.FeathersCallback<K> cb, Class<K> jClass);

    public abstract <J>  void update(String baseUrl, String serviceName, String id, J item, FeathersService.FeathersCallback<J> cb, Class<J> jClass);

    public abstract <J> void patch(String baseUrl, String serviceName, String id, JSONObject item, FeathersService.FeathersCallback<J> cb, Class<J> jClass);


    // Service Events
    public abstract <J> void onCreated(String serviceName, OnCreatedCallback<J> callback);

    public abstract <J> void onUpdated(String serviceName, OnUpdatedCallback<J> callback);

    public abstract <J> void onRemoved(String serviceName, OnRemovedCallback<J> callback);

    public abstract <J> void onPatched(String serviceName, OnPatchedCallback<J> callback);
}
