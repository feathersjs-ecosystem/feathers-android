package org.feathersjs.feathersdemo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.feathersjs.client.Feathers;
import org.feathersjs.client.plugins.authentication.AuthenticationOptions;
import org.feathersjs.client.plugins.authentication.FeathersAuthenticationConfiguration;
import org.feathersjs.client.plugins.providers.FeathersRest;
import org.feathersjs.client.plugins.providers.FeathersSocketIO;
import org.feathersjs.client.plugins.storage.SharedPreferencesStorageProvider;



public class DemoApplication extends Application {
    private static final String BASE_URL = "http://10.0.1.2:3030";

    public DemoApplication() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);

        SharedPreferencesStorageProvider storage = new SharedPreferencesStorageProvider(this);
        AuthenticationOptions options = new AuthenticationOptions();
        options.storage = storage;

        Feathers.getInstance()
                .setBaseUrl(BASE_URL)
//                .configure(new FeathersRest())
                .configure(new FeathersSocketIO())
                .configure(new FeathersAuthenticationConfiguration(options));
    }
}





