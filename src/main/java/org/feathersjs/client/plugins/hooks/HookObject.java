package org.feathersjs.client.plugins.hooks;

import rx.Observable;


public class HookObject <T> {
    public HookContext context;
    public Observable<HookContext> hookObservable;
    public HookCallback hookCallback;

    public String toString() {
        if (context != null) {
            return context.type + "::" + context.method.name() + "::" + context.params;
        }
        return super.toString();
    }

}
