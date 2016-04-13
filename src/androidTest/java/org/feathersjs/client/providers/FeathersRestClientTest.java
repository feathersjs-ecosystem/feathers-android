package org.feathersjs.client.providers;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.feathersjs.client.service.FeathersService;
import org.feathersjs.client.service.Result;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FeathersRestClientTest {
    private static String TAG = "FeathersSocketTest";

    private class TestClass {
        public String title;
        public String description;
    }

    AsyncHttpClient mockRestClient;
    FeathersService.FeathersCallback<Result<TestClass>> spyCallback;
    FeathersRestClient<TestClass> client;
    TestClass testClass;
    JSONObject expectedJsonObject;

    @Before
    public void initialize() {
        mockRestClient = mock(AsyncHttpClient.class);

        testClass = new TestClass();
        testClass.title = "The Title";
        testClass.description = "The description";

        expectedJsonObject = new JSONObject();

        try {
            expectedJsonObject.put("title", testClass.title);
            expectedJsonObject.put("description", testClass.description);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        spyCallback = new FeathersService.FeathersCallback<Result<TestClass>>() {
//            @Override
//            public void onSuccess(Result<TestClass> t) {
//
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//
//            }
//        };

        client = new FeathersRestClient<TestClass>("http://www.hiphopne.ws/", "posts", TestClass.class, mockRestClient);
    }

    @Test
    public void test_find() {
        client.find(new HashMap<String, String>(), spyCallback);
        verify(mockRestClient).get(eq("http://www.hiphopne.ws/posts"), any(JsonHttpResponseHandler.class));
    }

    @Test
    public void test_get() {
        client.get("123", null);
        verify(mockRestClient).get(eq("http://www.hiphopne.ws/posts/123"), any(JsonHttpResponseHandler.class));
    }

    @Test
    public void test_remove() {
        client.remove("123", null);
        verify(mockRestClient).delete(eq("http://www.hiphopne.ws/posts/123"), any(JsonHttpResponseHandler.class));
    }

    @Test
    public void test_create() {
        client.create(testClass, null);
        verify(mockRestClient).post(any(Context.class), eq("http://www.hiphopne.ws/posts"), any(RequestParams.class), any(JsonHttpResponseHandler.class));
    }


    @Test
    public void test_update() {
        client.update("123", testClass, null);
        verify(mockRestClient).put(eq("http://www.hiphopne.ws/posts/123"), any(RequestParams.class), any(JsonHttpResponseHandler.class));
    }

    @Test
    public void test_patch() {
        client.patch("123", testClass, null);
        verify(mockRestClient).patch(eq("http://www.hiphopne.ws/posts/123"), any(RequestParams.class), any(JsonHttpResponseHandler.class));
    }
}
