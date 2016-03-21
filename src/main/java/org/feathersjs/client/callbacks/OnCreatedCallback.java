package org.feathersjs.client.callbacks;

public abstract class OnCreatedCallback <T> extends OnEventCallback {
    public abstract void onCreated(T t);
}


