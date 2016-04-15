package org.feathersjs.client;

import com.github.nkzawa.socketio.client.Socket;

//import org.feathersjs.client.providers.FeathersSocketClient;
import org.feathersjs.client.service.FeathersService;
import org.feathersjs.client.service.Result;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FeathersServiceTest {
    FeathersService<JSONObject> service;

    private static String TAG = "FeathersServiceTest";

    Socket spyOnSocket;

//    @Spy FeathersSocketClient<JSONObject> provider = new FeathersSocketClient<>("http://www.hiphopne.ws", "posts", JSONObject.class, null);

    @Before
    public void initialize() {

        service = new FeathersService<JSONObject>("http://www.hiphopne.ws", "posts", JSONObject.class);

//        FeathersSocketClient<JSONObject> mockFoo = (FeathersSocketClient<JSONObject>) mock(FeathersSocketClient.class);
//        when(mockFoo.getValue).thenReturn(new Bar());

//        FeathersSocketClient<JSONObject> provider = new FeathersSocketClient<>("http://www.hiphopne.ws", "posts", JSONObject.class, null);

//        FeathersSocketClient<JSONObject> mockProvider = mock(provider.getClass());
//        FeathersSocketClient<JSONObject> spyProvider = spy(provider);
//        service.setProvider(spyProvider);
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
    public void testFind() {

        service.find(new FeathersService.FeathersCallback<Result<JSONObject>>() {
            @Override
            public void onSuccess(Result<JSONObject> t) {

            }

            @Override
            public void onError(String errorMessage) {

            }
        });
//        Log.d(TAG, "testGet");
//        Socket spyOnSocket = Mockito.spy(new Foo("argument"));
//
//        try {
//        FeathersSocketClient<Article> client = new FeathersSocketClient<Article>("http://www.hiphopne.ws", "posts", Article.class, spyOnSocket);
//        client.get("123", null);

//        verify(provider, timeout(10000)).find(null, null);

//            assertThat(false, is(true));
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        Socket mockSocket = mock(Socket.class);

    }

}
