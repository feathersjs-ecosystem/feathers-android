package org.feathersjs.client.hooks;

import org.feathersjs.client.interfaces.IFeathersConfiguration;


    public interface HookCallback<T> {
        void call(HookObject<T> t);
    }

