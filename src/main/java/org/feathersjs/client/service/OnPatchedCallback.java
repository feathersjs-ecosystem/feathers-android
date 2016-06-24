package org.feathersjs.client.service;

public abstract class OnPatchedCallback<T> extends OnEventCallback{
    public abstract void onPatched(T t);
}