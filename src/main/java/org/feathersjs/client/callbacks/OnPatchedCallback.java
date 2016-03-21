package org.feathersjs.client.callbacks;

public abstract class OnPatchedCallback<T> extends OnEventCallback{
    public abstract void onPatched(T t);
}