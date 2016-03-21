package org.feathersjs.feathersdemo;

import android.app.Application;
import android.util.Log;

import org.feathersjs.feathersdemo.models.Article;

import org.feathersjs.client.Feathers;
import org.feathersjs.client.FeathersAuthentication;
import org.feathersjs.client.FeathersService;
import org.feathersjs.client.ServiceEvent;
import org.feathersjs.client.callbacks.OnCreatedCallback;
import org.feathersjs.client.hooks.HookCallback;
import org.feathersjs.client.hooks.HookObject;
import org.feathersjs.client.Result;
import org.feathersjs.client.providers.FeathersSocketIO;
import org.feathersjs.client.providers.FeathersSocketIO.Options;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DemoApplication extends Application {

    private final String TAG = "!DemoApp";
    public DemoApplication() {
//       testBasic();

//        Feathers.authenticate();
//        Feathers.logout();

       testHooks();
    }

    private void testBasic() {

        Feathers.getInstance()
                .setBaseUrl("http://www.hiphopne.ws/")
                .configure(new FeathersSocketIO())
                .use("posts", Article.class);

        FeathersService<Article> articleService = Feathers.service("posts");
        articleService.find(new FeathersService.FeathersCallback<Result<Article>>() {
            @Override
            public void onSuccess(Result<Article> result) {
                Log.d("Article:find:success", result.toString());
            }

            @Override
            public void onError(String errorMessage) {
                Log.d("Article:find:error", errorMessage);
            }
        });
    }

    private void testAuthentication() {

        String[] transports = {"websocket"};
        Options socketOptions = new Options();
        socketOptions.forceNew = true;
        socketOptions.transports = transports;
        Feathers.getInstance()
                .setBaseUrl("http://www.hiphopne.ws/")
                .configure(new FeathersSocketIO(socketOptions))
                .configure(new FeathersAuthentication())
                .use("posts", Article.class);
    }

    private void testHooks() {

        Feathers.getInstance()
                .setBaseUrl("http://www.hiphopne.ws/")
                .configure(new FeathersSocketIO())
                .use("posts", Article.class);

        FeathersService<Article> articleService = Feathers.service("posts");

        HookCallback<Article> beforeHook1 = new HookCallback<Article>() {
            @Override
            public void call(HookObject<Article> hookObject) {
                Log.d(TAG, "article:before:find hook 1 " + hookObject.params);
                hookObject.params = "PARAMS 1";
//                Log.d(TAG, "article:before:find hook 1 " + hookObject.params.toString());
            }
        };

        HookCallback<Article> beforeHook2 = new HookCallback<Article>() {
            @Override
            public void call(HookObject<Article> hookObject) {
                Log.d(TAG, "article:before:find hook 2 " + hookObject.params);
                hookObject.params = "PARAMS 2";

            }
        };

//        Observable<HookObject<Article>> hook1Observer = Observable.create(
//                new Observable.OnSubscribe<HookObject<Article>>() {
//                    @Override
//                    public void call(Subscriber<? super HookObject<Article>> subscriber) {
//
////                        sub.onNext("Hello, world!");
////                        sub.onCompleted();
//                    }
//                }
//        );


        ArrayList<HookCallback<Article>> beforeHooks = new ArrayList<HookCallback<Article>>(
                Arrays.asList(beforeHook1, beforeHook2));
        articleService.before(ServiceEvent.FIND, beforeHooks);

        Map<String, String> params = new HashMap<String, String>();
        params.put("slug", "ante-up-episode-13-stalley");

        articleService.find(params, new FeathersService.FeathersCallback<Result<Article>>() {
            @Override
            public void onSuccess(Result<Article> result) {
                Log.d(TAG, "article:find:success " + result.data.size() + "");
            }

            @Override
            public void onError(String errorMessage) {
                Log.d(TAG, "Article:find:error " + errorMessage);
            }
        });

        articleService.onCreated(new OnCreatedCallback<Article>() {
            @Override
            public void onCreated(Article article) {

            }
        });
    }
}




//    .configure(hooks())
//    .configure(authentication({ storage: window.localStorage }));

// This `before` hook checks if a user is set
//        articleService.before({
//                find: function(hook) {
//            // If no user is set, throw an error
//            if(!hook.params.user) {
//                throw new Error('You are not authorized. Set the ?user=username parameter.');
//            }
//        }
//        });

// This `after` hook sets the username for each Todo
//        todoService.after({
//                find: function(hook) {
////            hook.result.forEach(function(todo) {
////                todo.user = hook.params.user.name;
////            });
//        }
//        });
