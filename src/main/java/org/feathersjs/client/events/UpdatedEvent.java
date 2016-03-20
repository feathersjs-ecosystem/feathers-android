package org.feathersjs.client.events;

public class UpdatedEvent<T> {
    private T mItem;
    public UpdatedEvent(T item) {
        mItem = item;
    }
    public T getItem() {
        return mItem;
    }
}

