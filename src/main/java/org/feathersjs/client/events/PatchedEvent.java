package org.feathersjs.client.events;

public class PatchedEvent<T> {
    private T mItem;
    public PatchedEvent(T item) {
        mItem = item;
    }
    public T getItem() {
        return mItem;
    }
}

