package org.feathersjs.client.plugins.hooks;

import org.feathersjs.client.service.ServiceEvent;

import java.util.HashMap;

public class HookContext {
    public Object data;
    public HashMap<String, Object> params;
    public HookType type;
    public ServiceEvent method;

    public HookContext() {
        params = new HashMap<>();
    }

}
