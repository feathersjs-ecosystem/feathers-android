package org.feathersjs.client.plugins.authentication;

import org.feathersjs.client.plugins.storage.IStorageProvider;

public class AuthenticationOptions {
    public String cookie;
    public String tokenKey;
    public String localEndpoint;
    public String tokenEndpoint;
    public String usernameField;

    public IStorageProvider storage;
}
