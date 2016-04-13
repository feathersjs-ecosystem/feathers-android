package org.feathersjs.client.plugins.storage;

public interface IStorageProvider {
    Object getItem(String key);
    void setItem(String key, Object value);
}
