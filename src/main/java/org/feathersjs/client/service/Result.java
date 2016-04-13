package org.feathersjs.client.service;

import com.google.gson.Gson;

import java.util.List;

public class Result<T> {
    public int total;
    public int limit;
    public int skip;
    public List<T> data;

    public String toString() {
        return new Gson().toJson(this);
    }
}
