package org.feathersjs.client.interfaces;

import org.feathersjs.client.FeathersService;
import org.feathersjs.client.callbacks.OnCreatedCallback;
import org.feathersjs.client.callbacks.OnPatchedCallback;
import org.feathersjs.client.callbacks.OnRemovedCallback;
import org.feathersjs.client.callbacks.OnUpdatedCallback;
import org.feathersjs.client.interfaces.IFeathersConfigurable;
import org.feathersjs.client.Result;

import java.util.Map;


public abstract class IFeathersProvider<T> extends IFeathersConfigurable {

//    void find(final Feathers.FeathersCallback<List<T>> cb);

    public abstract void find(Map<String, String> params, FeathersService.FeathersCallback<Result<T>> cb);

    public abstract void get(String id, FeathersService.FeathersCallback<T> cb);

    public abstract void remove(String id, FeathersService.FeathersCallback<T> cb);

    public abstract void create(T item, FeathersService.FeathersCallback<T> cb);

    public abstract void update(String id, T item, FeathersService.FeathersCallback<T> cb);

    public abstract void patch(String id, T item, FeathersService.FeathersCallback<T> cb);

    public abstract void onCreated(OnCreatedCallback<T> callback);

    public abstract void onUpdated(OnUpdatedCallback<T> callback);

    public abstract void onRemoved(OnRemovedCallback<T> callback);

    public abstract void onPatched(OnPatchedCallback<T> callback);
}
