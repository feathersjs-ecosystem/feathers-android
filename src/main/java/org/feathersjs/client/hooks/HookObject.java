package org.feathersjs.client.hooks;

import org.feathersjs.client.ServiceEvent;

public class HookObject <T> {
    public HookType type;
    public ServiceEvent method;
    public Object data;
    public Object params;

    public HookCallback<T> hookCallback;
}
