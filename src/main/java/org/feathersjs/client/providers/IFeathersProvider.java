package org.feathersjs.client.providers;

import org.feathersjs.client.FeathersService;

import java.util.List;
import java.util.Map;


public abstract class IFeathersProvider<T> {

//    void find(final Feathers.FeathersCallback<List<T>> cb);

    public abstract void find(Map<String, String> params, FeathersService.FeathersCallback<List<T>> cb);

    public abstract void get(String id, FeathersService.FeathersCallback<T> cb);

    public abstract void remove(String id, FeathersService.FeathersCallback<T> cb);

    public abstract void create(T item, FeathersService.FeathersCallback<T> cb);

    public abstract void update(String id, T item, FeathersService.FeathersCallback<T> cb);

    public abstract void patch(String id, T item, FeathersService.FeathersCallback<T> cb);

    public abstract void onCreated(FeathersService.FeathersEventCallback<T> callback);

    public abstract void onUpdated(FeathersService.FeathersEventCallback<T> callback);

    public abstract void onRemoved(FeathersService.FeathersEventCallback<T> callback);

    public abstract void onPatched(FeathersService.FeathersEventCallback<T> callback);
}
