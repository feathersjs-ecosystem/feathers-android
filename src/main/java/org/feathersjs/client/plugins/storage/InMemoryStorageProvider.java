package org.feathersjs.client.plugins.storage;

import java.util.HashMap;

public class InMemoryStorageProvider implements IStorageProvider {

    private HashMap<String, Object> mStorageHash;

    public InMemoryStorageProvider() {
        mStorageHash = new HashMap<>();
    }

    public Object getItem(String key) {
        return mStorageHash.get(key);
    }

    public void setItem(String key, Object value) {
        mStorageHash.put(key, value);
    }

}
