package org.feathersjs.client.service;

import java.util.Map;

public interface IFeathersService {
    <J> void find(final FeathersService.FeathersCallback<Result<J>> cb);

    <J> void find(Map<String, String> params, final FeathersService.FeathersCallback<Result<J>> cb);

    <J> void get(String id, final FeathersService.FeathersCallback<J> cb);

    <J> void remove(String id, final FeathersService.FeathersCallback<J> cb);

    <J> void create(J item, final FeathersService.FeathersCallback<J> cb);

    <J> void update(String id, J item, final FeathersService.FeathersCallback<J> cb);

    <J> void patch(String id, J item, final FeathersService.FeathersCallback<J> cb);
}

