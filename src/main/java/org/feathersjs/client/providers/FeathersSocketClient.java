package org.feathersjs.client.providers;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.feathersjs.client.utilities.Serialization;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.feathersjs.client.FeathersService.FeathersCallback;
import org.feathersjs.client.FeathersService.FeathersEventCallback;

public class FeathersSocketClient<T> extends IFeathersProvider<T> {

    private String mServicePath = null;
    private Gson gson = null;
    Class<T> mModelClass;

    private Socket socket;

    public FeathersSocketClient() {

    }

    public FeathersSocketClient(String baseUrl, String servicePath, Class<T> modelClass) {
        mModelClass = modelClass;
        mServicePath = servicePath;
        gson = new GsonBuilder().create();

        configureSocket(baseUrl);
    }


    @Override
    public void find(Map<String, String> params, final FeathersCallback<List<T>> cb) {
        JSONObject obj = new JSONObject(params);
        socket.emit(mServicePath + "::find", obj, new Ack() {
            @Override
            public void call(Object... args) {
                JSONArray array = (JSONArray) args[1];
                List<T> items = Serialization.deserializeArray(array, mModelClass, gson);
                cb.onSuccess(items);
            }
        });
    }

    @Override
    public void get(String id, final FeathersCallback<T> cb) {
        socket.emit(mServicePath + "::get", Integer.parseInt(id), new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject obj = (JSONObject) args[1];
                T item = gson.fromJson(obj.toString(), mModelClass);
                cb.onSuccess(item);
            }
        });
    }

    @Override
    public void remove(String id, final FeathersCallback<T> cb) {
        socket.emit(mServicePath + "::remove", Integer.parseInt(id), new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject obj = (JSONObject) args[1];
                T item = gson.fromJson(obj.toString(), mModelClass);
                cb.onSuccess(item);
            }
        });
    }

    @Override
    public void update(String id, T item, final FeathersCallback<T> cb) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void patch(String id, T item, final FeathersCallback<T> cb) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void create(T item, final FeathersCallback<T> cb) {
        throw new UnsupportedOperationException();
//        String jsonString = gson.toJson(item);
//
//        JSONObject obj = null;
//        try {
//            obj = new JSONObject(jsonString);
//            socket.emit(mServicePath + "::create", obj, new Ack() {
//                @Override
//                public void call(Object... args) {
//                    JSONObject obj = (JSONObject) args[1];
//                    Type todoType = new TypeToken<TodoNew>() {
//                    }.getType();
//                    T item = gson.fromJson(obj.toString(), todoType);
//                    cb.onSuccess(item);
//                }
//            });
//        } catch (JSONException e) {
//
//        }
    }

    @Override
    public void onCreated(final FeathersEventCallback<T> callback) {
        listenForEvent("created", callback);
    }

    @Override
    public void onUpdated(final FeathersEventCallback<T> callback) {
        listenForEvent("updated", callback);
    }

    @Override
    public void onRemoved(final FeathersEventCallback<T> callback) {
        listenForEvent("removed", callback);
    }

    @Override
    public void onPatched(final FeathersEventCallback<T> callback) {
        listenForEvent("patched", callback);
    }

    private void configureSocket(String baseUrl) {
        IO.Options opts = new IO.Options();
        opts.forceNew = true;
        opts.reconnection = true;

        try {
            socket = IO.socket(baseUrl, opts);
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d("SOCKET:", "Connected!");
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d("SOCKET:", "Disconnected!");
                }
            });
            socket.connect();
        } catch (URISyntaxException exception) {
            Log.e("FeathersSocket", "Error parsing URL", exception);
        }
    }

    private void listenForEvent(final String eventName, final FeathersEventCallback<T> callback) {
        socket.on(mServicePath + " " + eventName, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if (args.length == 1) {
                    JSONObject obj = (JSONObject) args[0];
                    T item = gson.fromJson(obj.toString(), mModelClass);
                    Log.d("socket:" + eventName + ":", obj.toString());
                    callback.onSuccess(item);
                }
            }
        });
    }
}
