package org.feathersjs.client.interfaces;

import org.feathersjs.client.FeathersService;
import org.feathersjs.client.Result;

import java.util.List;
import java.util.Map;

public interface IFeathersService<T> {
    void find(final FeathersService.FeathersCallback<Result<T>> cb);

    void find(Map<String, String> params, final FeathersService.FeathersCallback<Result<T>> cb);

    void get(String id, final FeathersService.FeathersCallback<T> cb);

    void remove(String id, final FeathersService.FeathersCallback<T> cb);

    void create(T item, final FeathersService.FeathersCallback<T> cb);

    void update(String id, T item, final FeathersService.FeathersCallback<T> cb);

    void patch(String id, T item, final FeathersService.FeathersCallback<T> cb);
}

