package org.feathersjs.client.service;

public abstract class OnCreatedCallback <T> extends OnEventCallback {
    public abstract void onCreated(T t);
}


