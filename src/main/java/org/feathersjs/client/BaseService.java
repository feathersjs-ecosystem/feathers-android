package org.feathersjs.client;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.QueryMap;

public interface BaseService<T> {
    @GET("/")
    public void find(
            @QueryMap Map<String, String> options,
            Callback<List<T>> cb
    );

    @GET("/{id}")
    public void get(
            @Path("id") String id,
            Callback<T> cb
    );

    @POST("/")
    void create(
            @Body T request,
            Callback callback);

    @PUT("/{id}")
    public void update(
            @Path("id") String id,
            T request,
            Callback<T> cb
    );

    @PATCH("/{id}")
    public void patch(
            @Path("id") String id,
            T request,
            Callback<T> cb
    );

    @DELETE("/{id}")
    void delete(@Path("id") String id, Callback callback);
}
