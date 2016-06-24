package org.feathersjs.client.interfaces;

import org.feathersjs.client.Feathers;

public class IFeathersConfiguration extends IFeathersConfigurable {
    private Feathers mApp;

    public void setApp(Feathers feathers) {
        mApp = feathers;
    }
}
