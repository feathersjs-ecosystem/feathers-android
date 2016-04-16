package org.feathersjs.client.service;

import org.json.JSONObject;

import java.util.Map;

public abstract class IFeathersService <T> {
    abstract void find(final FeathersService.FeathersCallback<Result<T>> cb);

    abstract void find(Map<String, String> params, final FeathersService.FeathersCallback<Result<T>> cb);

    abstract void get(String id, final FeathersService.FeathersCallback<T> cb);

    abstract void remove(String id, final FeathersService.FeathersCallback<T> cb);

    abstract <J> void create(J item, final FeathersService.FeathersCallback<T> cb);

    abstract void update(String id, T item, final FeathersService.FeathersCallback<T> cb);

    abstract void patch(String id, JSONObject item, final FeathersService.FeathersCallback<T> cb);
}

