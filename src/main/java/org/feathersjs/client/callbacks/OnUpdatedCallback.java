package org.feathersjs.client.callbacks;

public abstract class OnUpdatedCallback<T> extends OnEventCallback {
    public abstract void onUpdated(T t);
}