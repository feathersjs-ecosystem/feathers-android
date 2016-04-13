package org.feathersjs.client;

import android.util.Log;

import org.feathersjs.client.plugins.authentication.FeathersAuthentication;
import org.feathersjs.client.plugins.authentication.FeathersAuthenticationConfiguration;
import org.feathersjs.client.providers.FeathersRestClient;
import org.feathersjs.client.providers.FeathersSocketClient;
import org.feathersjs.client.providers.FeathersSocketIO;
import org.feathersjs.client.providers.IProviderConfiguration;
import org.feathersjs.client.service.FeathersService;
import org.feathersjs.client.utilities.StringUtilities;
import org.json.JSONObject;

import java.util.HashMap;

public class Feathers {

    private static Feathers instance;
    private HashMap<String, FeathersService> registeredServices;
    private HashMap<String, Object> settings = new HashMap<>();
    private String mBaseUrl;
//    private IFeathersProvider mProvider;
    private IProviderConfiguration mProviderConfiguration;
    private FeathersAuthentication mFeathersAuthentication;

    public synchronized static Feathers getInstance() {
        if (instance == null) {
            instance = new Feathers();
        }
        return instance;
    }

    public synchronized static Feathers newInstance() {
        return new Feathers();
    }

    /*

    Settings

     */
    public Object get(String key) {
        return settings.get(key);
    }

    public Object set(String key, Object value) {
        return settings.put(key, value);
    }

    public Feathers() {
        registeredServices = new HashMap<>();
    }

    public <J> Feathers use(String serviceName, Class<J> clazz) {
        return this.use(serviceName, clazz, getBaseUrl());
    }

    public <J> Feathers use(String serviceName, Class<J> clazz, String baseUrl) {
        if (baseUrl == null) {
            Log.e("Feathers", "You must set pass in a baseUrl or call setBaseUrl on Feathers before registering a service.");
        }
        serviceName = StringUtilities.trimSlashesFromStartAndEnd(serviceName);
        FeathersService<J> service = new FeathersService<J>(baseUrl, serviceName, clazz);
        registeredServices.put(serviceName, service);
        return this;
    }

    public Feathers configure(IProviderConfiguration providerConfig) {
        mProviderConfiguration = providerConfig;
        mProviderConfiguration.setApp(this);
        return this;
    }

    public Feathers configure(FeathersAuthenticationConfiguration authentication) {
        //TODO: Pass through options
        mFeathersAuthentication = new FeathersAuthentication(this);
        return this;
    }


    public Feathers setBaseUrl(String baseUrl) {
       mBaseUrl = StringUtilities.trimSlashesFromEnd(baseUrl);
        return this;
    }

    public String getBaseUrl() {
        return mBaseUrl;
    }

    public <J> FeathersService service(String name) {
        name = StringUtilities.trimSlashesFromStartAndEnd(name);
        if (registeredServices.containsKey(name)) {
            return getServiceWithProvider(name);
        } else {
            use(name, JSONObject.class);

            // Add a provider to the newly fetched service
            return getServiceWithProvider(name);
        }
    }

    private <J> FeathersService getServiceWithProvider(String name) {
        FeathersService service =registeredServices.get(name);
        if (service != null && service.getProvider() == null) {
            if (mProviderConfiguration instanceof FeathersSocketIO) {
                service.setProvider(new FeathersSocketClient<J>(service.getBaseUrl(), service.getName(), service.getModelClass(), null));
            } else {

                // Use rest by default
                service.setProvider(new FeathersRestClient<J>(service.getBaseUrl(), service.getName(), service.getModelClass(), null));
            }
        }
        return service;
    }



    public void authenticate(FeathersAuthentication.AuthenticationType authType, String identifier, String password, FeathersService.FeathersCallback cb) {
        mFeathersAuthentication.authenticate(authType, identifier, password, cb);
    }

    public void authenticate(String identifier, String password, FeathersService.FeathersCallback cb) {
        mFeathersAuthentication.authenticate(FeathersAuthentication.AuthenticationType.Local, identifier, password, cb);
    }

    public void logout() {
        mFeathersAuthentication.logout();
    }
}