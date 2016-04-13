package org.feathersjs.feathersdemo;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


@RunWith(JUnit4.class)
public class FeathersSocketClientTest  {
    private static String TAG = "FeathersSocketTest";

//    Socket spyOnSocket;

    @Before
    public void initialize() {
//        Log.d(TAG, "BEFORE");
//        try {
//            Socket socket = IO.socket("http://www.hiphopne.ws", new IO.Options());
//            spyOnSocket = spy(socket);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
    }

//    public void testAuthenticationAddsAuthQuery() {
//        assertThat(false, is(true));
//    }

    @Test
    public void testGet() {
        Log.d(TAG, "testGet");
//        Socket spyOnSocket = Mockito.spy(new Foo("argument"));

//        try {
//        FeathersSocketClient<Article> client = new FeathersSocketClient<Article>("http://www.hiphopne.ws", "posts", Article.class, spyOnSocket);
//        client.get("123", null);

//        verify(spyOnSocket, timeout(10000)).emit("one");

//            assertThat(false, is(true));
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        Socket mockSocket = mock(Socket.class);


    }

//    public void testFind() {
//
//        Socket mockSocket = mock(Socket.class);
//        FeathersSocketClient<Article> client = new FeathersSocketClient<Article>("http://www.hiphopne.ws", "posts", Article.class, mockSocket);
//        client.find(new HashMap<String, String>(), null);
//        assertThat(false, is(true));
//    }
//
//    public void testCreate() {
//        Socket mockSocket = mock(Socket.class);
//        FeathersSocketClient<Article> client = new FeathersSocketClient<Article>("http://www.hiphopne.ws", "posts", Article.class, mockSocket);
//        client.create(new Article(), null);
//        assertThat(false, is(true));
//    }
//
//    public void testRemove() {
//        Socket mockSocket = mock(Socket.class);
//        FeathersSocketClient<Article> client = new FeathersSocketClient<Article>("http://www.hiphopne.ws", "posts", Article.class, mockSocket);
//        client.remove("id", null);
//        assertThat(false, is(true));
//    }
//
//    public void testUpdate() {
//        Socket mockSocket = mock(Socket.class);
//        FeathersSocketClient<Article> client = new FeathersSocketClient<Article>("http://www.hiphopne.ws", "posts", Article.class, mockSocket);
//        client.update("id", null, null);
//        assertThat(false, is(true));
//    }
//
//    public void testPatch() {
//        Socket mockSocket = mock(Socket.class);
//        FeathersSocketClient<Article> client = new FeathersSocketClient<Article>("http://www.hiphopne.ws", "posts", Article.class, mockSocket);
//        client.patch("id", null, null);
//    }

}
