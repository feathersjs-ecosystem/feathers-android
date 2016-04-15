package org.feathersjs.client.providers;

import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import static org.feathersjs.client.JSONObjectMatcher.jsonEq;

@RunWith(MockitoJUnitRunner.class)
public class FeathersSocketClientTest {
    private static String TAG = "FeathersSocketTest";

    private class TestClass {
        public String title;
        public String description;
    }

    Socket mockSocket;
//    FeathersSocketClient<Object> client;

    TestClass testClass;
    JSONObject testJson;

    @Before
    public void initialize() {
        mockSocket = mock(Socket.class);
//        client = new FeathersSocketClient<Object>("http://www.hiphopne.ws", "posts", Object.class, mockSocket);

        testClass = new TestClass();
        testClass.title = "The Title";
        testClass.description = "The description";

        testJson = new JSONObject();
        try {
            testJson.put("title", testClass.title);
            testJson.put("description", testClass.description);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_find() {
//        client.find(new HashMap<String, String>(), null);
        verify(mockSocket).emit(eq("posts::find"), any(JSONObject.class), any());
    }

    @Test
    public void test_get() {
//        client.get("123", null);
        verify(mockSocket).emit(eq("posts::get"), eq("123"), any());
    }

    @Test
    public void test_remove() {
//        client.remove("123", null);
        verify(mockSocket).emit(eq("posts::remove"), eq("123"), any());
    }

    @Test
    public void test_create() {
//        client.create(testClass, null);
        verify(mockSocket).emit(eq("posts::create"), jsonEq(testJson), any());
    }

    @Test
    public void test_update() {
//        client.update("123", testClass, null);
        verify(mockSocket).emit(eq("posts::update"), eq("123"), jsonEq(testJson), any());
    }

    @Test
    public void test_patch() {
//        client.patch("123", testClass, null);
        verify(mockSocket).emit(eq("posts::patch"), eq("123"), jsonEq(testJson), any());
    }
}
