package org.feathersjs.feathersdemo;

import android.app.Application;
import org.feathersjs.client.Feathers;
import org.feathersjs.client.plugins.authentication.AuthenticationOptions;
import org.feathersjs.client.plugins.authentication.FeathersAuthenticationConfiguration;
import org.feathersjs.client.plugins.providers.FeathersRest;
import org.feathersjs.client.plugins.storage.SharedPreferencesStorageProvider;

public class DemoApplication extends Application {

    public DemoApplication() {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferencesStorageProvider storage = new SharedPreferencesStorageProvider(this);
        AuthenticationOptions options = new AuthenticationOptions();
        options.storage = storage;

        Feathers.getInstance()
                .setBaseUrl("http://192.168.0.10:3030")
                .configure(new FeathersRest())
                .configure(new FeathersAuthenticationConfiguration(options));
    }
}





