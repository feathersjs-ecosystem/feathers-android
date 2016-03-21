package org.feathersjs.client;

import org.feathersjs.client.interfaces.IFeathersConfigurable;

public class FeathersAuthentication extends IFeathersConfigurable {
    public FeathersAuthentication() {

    }

    public void authenticate() {
        //TODO: Check the auth type
        //TODO: Get token from storage
    }

    public void logout() {
        //TODO: Clear the auth token
        //TODO: Clear the user
    }
}
