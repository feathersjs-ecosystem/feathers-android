package org.feathersjs.client.authentication;

import org.feathersjs.client.Feathers;
import org.feathersjs.client.plugins.authentication.FeathersAuthenticationConfiguration;
import org.feathersjs.client.plugins.providers.FeathersRest;
import org.feathersjs.client.service.FeathersService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FeathersAuthenticationTest {
    //TODO: Properly calls authenticate
    //TODO: Properly calls logout
    //TODO: Properly configures storage
    //TODO: Properly configures a rest provider
    //TODO: Removes slashes when registering / looking up a service

//    Feathers app;

    @Before
    public void initialize() {
//        app = Feathers.newInstance();
//        app.setBaseUrl("http://BASE.URL/");
        //.use("posts", Article.class);
    }

    @Test
    public void REST_authentication() {

        Feathers app = Feathers.newInstance()
                .setBaseUrl("http://BASE.URL/");
        app.configure(new FeathersRest());
        app.configure(new FeathersAuthenticationConfiguration());

        // Log in
        app.authenticate("username", "password", new FeathersService.FeathersCallback() {
            @Override
            public void onSuccess(Object t) {

            }

            @Override
            public void onError(String errorMessage) {

            }
        });

        //TODO: Verify that socket emits correct data
    }

    @Test
    public void test_socket_authentication() {
        Feathers app = Feathers.newInstance();
        app.setBaseUrl("http://BASE.URL/");
//        app.configure(new FeathersSocketIO());
        app.configure(new FeathersAuthenticationConfiguration());

        // Log in
//        app.authenticate(FeathersAuthentication.AuthenticationType.Local, "username", "password");

        //TODO: Verify that socket emits correct data


        //TODO: Try to use service that requires auth
        //TODO: Verify that query token is set correctly
    }


//    should properly set token for REST
//    - Log in
//    - Make call to service
//    - Verify that Socket query is set correctly
//
//    should properly set token for Socket.io
//    - Log in
//    - Make call to service
//    - Verify that REST header is set correctly


}
