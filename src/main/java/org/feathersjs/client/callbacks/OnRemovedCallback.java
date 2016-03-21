package org.feathersjs.client.callbacks;

public abstract class OnRemovedCallback <T> extends OnEventCallback {
    public abstract void onRemoved(T t);
}