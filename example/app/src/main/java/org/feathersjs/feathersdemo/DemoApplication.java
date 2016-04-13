package org.feathersjs.feathersdemo;

import android.app.Application;

import org.feathersjs.client.Feathers;
import org.feathersjs.client.plugins.authentication.FeathersAuthenticationConfiguration;
import org.feathersjs.client.providers.FeathersRest;
import org.feathersjs.feathersdemo.models.Message;

public class DemoApplication extends Application {

    private final String TAG = "!DemoApp";

    public DemoApplication() {
        Feathers.getInstance()
                .setBaseUrl("http://10.0.1.2:3030")
//                .configure(new FeathersSocketIO(opts))
                .configure(new FeathersRest())
                .configure(new FeathersAuthenticationConfiguration())
                .use("messages", Message.class);
    }
}





