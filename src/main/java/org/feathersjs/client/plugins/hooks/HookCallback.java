package org.feathersjs.client.plugins.hooks;


public interface HookCallback<T> {
        void call(HookObject t, HookDoneCallback cb);
    }

