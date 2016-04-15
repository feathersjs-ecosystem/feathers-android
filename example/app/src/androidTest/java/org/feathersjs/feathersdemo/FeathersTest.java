package org.feathersjs.feathersdemo;

import android.os.Handler;
import android.util.Log;

import junit.framework.TestCase;

import org.feathersjs.client.Feathers;
import org.feathersjs.client.plugins.hooks.HookCallback;
import org.feathersjs.client.plugins.hooks.HookDoneCallback;
import org.feathersjs.client.plugins.hooks.HookObject;
import org.feathersjs.client.service.FeathersService;
import org.feathersjs.client.service.Result;
import org.feathersjs.client.service.ServiceEvent;
import org.feathersjs.feathersdemo.models.Message;
import org.junit.Before;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class FeathersTest extends TestCase {
    private static String TAG = "FeathersTest";

    private HookCallback<Message> aSyncBeforeHook = new HookCallback<Message>() {
        @Override
        public void call(final HookObject t, final HookDoneCallback cb) {
            Log.d(TAG, "article:before:find hook 1 " + t.context.params);

            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
//                    t.context.params = "PARAMS 1";
                    cb.onDone(t);
                }
            };
            handler.postDelayed(runnable, 3000);
        }
    };

    private HookCallback<Message> syncBeforeHook = new HookCallback<Message>() {
        @Override
        public void call(HookObject t, HookDoneCallback cb) {
            Log.d(TAG, "article:before:find hook 1 " + t.context.params);
//            t.context.params = "PARAMS 1";
            cb.onDone(t);
        }
    };

    @Before
    public void initialize() {
        Feathers.getInstance()
                .setBaseUrl("http://www.hiphopne.ws/")
//                .configure(new FeathersSocketIO())
                .use("posts", Message.class);
    }

    public void testHooks() {

//        FeathersService articleService = Feathers.getInstance().service("posts");
//        articleService.before(ServiceEvent.FIND, Arrays.asList(syncBeforeHook, aSyncBeforeHook));
//
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("slug", "ante-up-episode-13-stalley");
//
//        articleService.find(params, new FeathersService.FeathersCallback<Result<Message>>() {
//            @Override
//            public void onSuccess(Result<Message> result) {
//                Log.d(TAG, "article:find:success " + result.data.size() + "");
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//                Log.d(TAG, "Article:find:error " + errorMessage);
//            }
//        });
    }

    public void testAsyncHook() {

        //FeathersService.FeathersCallback cb = mock();

//        FeathersService articleService = Feathers.getInstance().service("posts");
//        articleService.before(ServiceEvent.FIND, Collections.singletonList(aSyncBeforeHook));
//
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("slug", "ante-up-episode-13-stalley");

        //verify(cb, timeout(3000)).onSuccess(null);
//        articleService.find(params, cb);
        //        HookDoneCallback hookDone = mock(HookDoneCallback.class);
        //        Sender sender = new Sender(receiver);
        //        sender.post("hello");
    }

    public void testBeforeHooksCanModifyParams() {
        assertThat(false, is(true));
    }

    public void testAfterHooksCanModifyResult() {
        assertThat(false, is(true));
    }

    public void testRestClientFiresEvents() {
        assertThat(false, is(true));
    }

    public void testSocketClientFiresEvents() {
        assertThat(false, is(true));
    }


//    @Test
//    public void async() {
//        HookDoneCallback hookDone = mock(HookDoneCallback.class);

//        Sender sender = new Sender(receiver);
//        sender.post("hello");

//        verify(hookDone, timeout(1000)).onDone(null);
//    }



//    public void testFetching() {
//        Feathers.getInstance()
//                .setBaseUrl("http://www.hiphopne.ws/")
//                .configure(new FeathersSocketIO())
//                .use("posts", Article.class);
//
//        assertThat(false, is(true));
//    }


    //        Context context = Mockito.mock(Context.class);
//        Intent intent = MainActivity.createQuery(context, "query", "value");
//        assertNotNull(intent);
//        Bundle extras = intent.getExtras();
//        assertNotNull(extras);
//        assertEquals("query", extras.getString("QUERY"));
//        assertEquals("value", extras.getString("VALUE"));



//    @Test
//    public void beforeHooksModifyParams() {
//
//        //TODO: Test that params has changed after hooks are called
//        assertThat(true, is(true));
//
//        FeathersService articleService = Feathers.service("posts");
//
//        HookCallback<Article> beforeHook1 = new HookCallback<Article>() {
//            @Override
//            public void call(HookObject t, HookDoneCallback cb) {
//                Log.d(TAG, "article:before:find hook 1 " + t.context.params);
//                t.context.params = "PARAMS 1";
//                cb.onDone(t);
//            }
//        };
//        HookCallback<Article> beforeHook2 = new HookCallback<Article>() {
//            @Override
//            public void call(HookObject t, HookDoneCallback cb) {
//                Log.d(TAG, "article:before:find hook 2 " + t.context.params);
//                t.context.params = "PARAMS 2";
//                cb.onDone(t);
//            }
//        };
//
//        articleService.before(ServiceEvent.FIND, Arrays.asList(beforeHook1, beforeHook2));
//
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("slug", "ante-up-episode-13-stalley");
//
//        articleService.find(params, new FeathersService.FeathersCallback<Result<Article>>() {
//            @Override
//            public void onSuccess(Result<Article> result) {
//                Log.d(TAG, "article:find:success " + result.data.size() + "");
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//                Log.d(TAG, "Article:find:error " + errorMessage);
//            }
//        });
//
//    }
}
