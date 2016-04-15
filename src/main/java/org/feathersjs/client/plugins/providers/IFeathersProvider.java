package org.feathersjs.client.plugins.providers;

import org.feathersjs.client.interfaces.IFeathersConfigurable;
import org.feathersjs.client.service.FeathersService;
import org.feathersjs.client.service.OnCreatedCallback;
import org.feathersjs.client.service.OnPatchedCallback;
import org.feathersjs.client.service.OnRemovedCallback;
import org.feathersjs.client.service.OnUpdatedCallback;
import org.feathersjs.client.service.Result;

import java.util.Map;


public abstract class IFeathersProvider extends IFeathersConfigurable {

    public abstract <J> void find(Map<String, String> params, FeathersService.FeathersCallback<Result<J>> cb, Class<J> jClass);

    public abstract <J> void get(String id, FeathersService.FeathersCallback<J> cb, Class<J> jClass);

    public abstract <J>  void remove(String id, FeathersService.FeathersCallback<J> cb, Class<J> jClass);

    public abstract <J, K>  void create(J item, FeathersService.FeathersCallback<K> cb, Class<K> jClass);

    public abstract <J>  void update(String id, J item, FeathersService.FeathersCallback<J> cb, Class<J> jClass);

    public abstract <J> void patch(String id, J item, FeathersService.FeathersCallback<J> cb, Class<J> jClass);


    // Service Events
    public abstract <J> void onCreated(OnCreatedCallback<J> callback);

    public abstract <J> void onUpdated(OnUpdatedCallback<J> callback);

    public abstract <J> void onRemoved(OnRemovedCallback<J> callback);

    public abstract <J> void onPatched(OnPatchedCallback<J> callback);
}
