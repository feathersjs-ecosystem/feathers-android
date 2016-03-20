package org.feathersjs.client.events;

public class RemovedEvent<T> {
    private T mItem;
    public RemovedEvent(T item) {
        mItem = item;
    }
    public T getItem() {
        return mItem;
    }
}

