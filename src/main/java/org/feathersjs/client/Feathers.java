package org.feathersjs.client;

import android.util.Log;

import org.feathersjs.client.plugins.authentication.AuthResponse;
import org.feathersjs.client.plugins.authentication.FeathersAuthentication;
import org.feathersjs.client.plugins.authentication.FeathersAuthenticationConfiguration;
import org.feathersjs.client.plugins.providers.FeathersRestClient;
//import org.feathersjs.client.plugins.providers.FeathersSocketClient;
import org.feathersjs.client.plugins.providers.FeathersSocketClient;
import org.feathersjs.client.plugins.providers.FeathersSocketIO;
import org.feathersjs.client.plugins.providers.IFeathersProvider;
import org.feathersjs.client.plugins.providers.IProviderConfiguration;
import org.feathersjs.client.service.FeathersService;
import org.feathersjs.client.utilities.StringUtilities;
import org.json.JSONObject;

import java.util.HashMap;

public class Feathers {

    private static Feathers instance;
    private HashMap<String, FeathersService> registeredServices;
    private HashMap<String, Object> settings = new HashMap<>();
    private String mBaseUrl;
    private IFeathersProvider mProvider;
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

    public Feathers configure(FeathersAuthenticationConfiguration authenticationConfig) {
        //TODO: Pass through options
        mFeathersAuthentication = new FeathersAuthentication(authenticationConfig.getOptions(), this);
        return this;
    }


    public Feathers setBaseUrl(String baseUrl) {
        mBaseUrl = StringUtilities.trimSlashesFromEnd(baseUrl);
        return this;
    }

    public String getBaseUrl() {
        return mBaseUrl;
    }

    public IFeathersProvider getProvider() {

        if (mProvider == null) {
            if (mProviderConfiguration instanceof FeathersSocketIO) {
                mProvider = new FeathersSocketClient(this, mFeathersAuthentication, null);
            } else {
                // Use rest by default
                mProvider = new FeathersRestClient(this, mFeathersAuthentication, null);
            }
        }

        return mProvider;
    }

//    public <J> FeathersService service(String name) {
//        name = StringUtilities.trimSlashesFromStartAndEnd(name);
//        if (registeredServices.containsKey(name)) {
//            return getServiceWithProvider(name);
//        } else {
//            // Service isn't registered so register it
//            use(name, JSONObject.class);
//            return getServiceWithProvider(name);
//        }
//    }

    public <J> FeathersService<J> service(String name, Class<J> jClass) {
        name = StringUtilities.trimSlashesFromStartAndEnd(name);
        if (registeredServices.containsKey(name)) {
            return getServiceWithProvider(name, jClass);
        } else {
            use(name, jClass);

            // Add a provider to the newly fetched service
            return getServiceWithProvider(name, jClass);
        }
    }

    private <J> FeathersService<J> getServiceWithProvider(String name, Class<J> jClass) {
        FeathersService<J> service = registeredServices.get(name);



        if (service != null && service.getProvider() == null) {
            service.setProvider(mProvider);
        }
        return service;
    }


    public <J> void authenticate(FeathersAuthentication.AuthenticationType authType, String identifier, String password, FeathersService.FeathersCallback cb, Class<J> jClass) {
        mFeathersAuthentication.authenticate(authType, identifier, password, cb, jClass);
    }

    public <J> void authenticate(String identifier, String password, FeathersService.FeathersCallback<AuthResponse<J>> cb, Class<J> jClass) {
        mFeathersAuthentication.authenticate(FeathersAuthentication.AuthenticationType.Local, identifier, password, cb, jClass);
    }

    public void logout() {
        mFeathersAuthentication.logout();
    }
}