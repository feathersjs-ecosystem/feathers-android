package org.feathersjs.client.events;

public class CreatedEvent<T> {
    private T mItem;
    public CreatedEvent(T item) {
        mItem = item;
    }
    public T getItem() {
        return mItem;
    }
}

