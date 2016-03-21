package org.feathersjs.client;

import android.util.Log;

import org.feathersjs.client.providers.FeathersSocketClient;
import org.feathersjs.client.providers.FeathersSocketIO;
import org.feathersjs.client.interfaces.IFeathersProvider;
import org.feathersjs.client.interfaces.IProviderConfiguration;

import java.util.HashMap;

public class Feathers {

    private static Feathers instance;
    private HashMap<String, FeathersService> registeredServices;
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

    public Feathers() {
        registeredServices = new HashMap<>();
    }

    public <T> Feathers use(String serviceName, Class<T> clazz) {
        return this.use(serviceName, clazz, getBaseUrl());
    }

    public <T> Feathers use(String serviceName, Class<T> clazz, String baseUrl) {
        if (baseUrl == null) {
            Log.e("Feathers", "You must set pass in a baseUrl or call setBaseUrl on Feathers before registering a service.");
        }
        FeathersService<T> service = new FeathersService<T>(baseUrl, serviceName, clazz);
        getInstance().registeredServices.put(serviceName, service);
        return getInstance();
    }

    public <T> Feathers configure(IProviderConfiguration providerConfig) {
        mProviderConfiguration = providerConfig;
        return getInstance();
    }

    public <T> Feathers configure(FeathersAuthentication authentication) {
        mFeathersAuthentication = authentication;
        return getInstance();
    }


    public Feathers setBaseUrl(String baseUrl) {
        getInstance().mBaseUrl = baseUrl;
        return getInstance();
    }

    public String getBaseUrl() {
        return mBaseUrl;
    }

    public static <T> FeathersService<T> service(String name) {
        if (getInstance().registeredServices.containsKey(name)) {
            FeathersService<T> service = (FeathersService<T>)getInstance().registeredServices.get(name);
            if (service != null && service.getProvider() == null) {
                if(getInstance().mProviderConfiguration instanceof FeathersSocketIO) {
                    service.setProvider(new FeathersSocketClient<T>(service.getBaseUrl(), service.getName(),service.getModelClass()));
                }
            }
            return service;
        }
        throw new NullPointerException();
    }



    /* Authentication */
    public static void authenticate() {
        getInstance().mFeathersAuthentication.authenticate();
    }

    public static void logout() {
        getInstance().mFeathersAuthentication.logout();
    }
}