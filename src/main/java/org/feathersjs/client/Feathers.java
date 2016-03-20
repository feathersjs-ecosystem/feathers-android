package org.feathersjs.client;

import android.util.Log;
import java.util.HashMap;

public class Feathers {

    private static Feathers instance;
    private HashMap<String, FeathersService> mServices;
    private String mBaseUrl;

    public synchronized static Feathers getInstance() {
        if (instance == null) {
            instance = new Feathers();
        }
        return instance;
    }

    public Feathers() {
        mServices = new HashMap<>();
    }

    public static <T> void use(String serviceName, Class<T> clazz) {
        if(getInstance().mBaseUrl == null) {
            Log.e("Feathers", "You must set call setBaseUrl before registering a service.");
        } else {
            FeathersService<T> service = new FeathersService<T>(getInstance().mBaseUrl, serviceName, clazz);
            getInstance().mServices.put(serviceName, service);
        }
    }

    public static <T> void use(String serviceName, Class<T> clazz, String baseUrl) {
        // Use passed in URL
        FeathersService<T> service = new FeathersService<T>(baseUrl, serviceName, clazz);
        getInstance().mServices.put(serviceName, service);
    }

    public static void setBaseUrl(String baseUrl) {
        getInstance().mBaseUrl = baseUrl;
    }

    public static <T> FeathersService<T> service(String name) {
        if(getInstance().mServices.containsKey(name)) {
            //TODO: Fix this unchecked assignment
            return getInstance().mServices.get(name);
        }
        return null;
    }
}