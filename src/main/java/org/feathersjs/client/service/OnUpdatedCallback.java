package org.feathersjs.client.service;

public abstract class OnUpdatedCallback<T> extends OnEventCallback {
    public abstract void onUpdated(T t);
}