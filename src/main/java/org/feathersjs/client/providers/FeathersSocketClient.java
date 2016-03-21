package org.feathersjs.client.providers;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.feathersjs.client.Result;
import org.feathersjs.client.callbacks.OnCreatedCallback;
import org.feathersjs.client.callbacks.OnPatchedCallback;
import org.feathersjs.client.callbacks.OnRemovedCallback;
import org.feathersjs.client.callbacks.OnUpdatedCallback;
import org.feathersjs.client.interfaces.IFeathersProvider;
import org.feathersjs.client.utilities.Serialization;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Map;

import org.feathersjs.client.FeathersService.FeathersCallback;

public class FeathersSocketClient<T> extends IFeathersProvider<T> {

    private String mServicePath = null;
    private Gson gson = null;
    private boolean setup = false;
    private FeathersSocketIO.Options mOptions = new FeathersSocketIO.Options();
    Class<T> mModelClass;

    private Socket socket;

//    public FeathersSocketClient(FeathersSocketConfiguration.Options options) {
//        gson = new GsonBuilder().create();
//        mOptions = options;
//
//    }

    public FeathersSocketClient(String baseUrl, String servicePath, Class<T> modelClass) {
        mModelClass = modelClass;
        mServicePath = servicePath;
        gson = new GsonBuilder().create();

        configureSocket(baseUrl, mOptions);
    }


    public void setup() {
//        configureSocket(Feathers.getInstance().getBaseUrl(), mOptions);
    }

    @Override
    public void find(Map<String, String> params, final FeathersCallback<Result<T>> cb) {
        JSONObject obj = new JSONObject(params);
        socket.emit(mServicePath + "::find", obj, new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject array = (JSONObject) args[1];
                Result<T> result = Serialization.deserializeArray(array, mModelClass, gson);
                cb.onSuccess(result);
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
    public void onCreated(final OnCreatedCallback<T> callback) {
        final String eventName = "created";
        socket.on(mServicePath + " " + eventName, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if (args.length == 1) {
                    JSONObject obj = (JSONObject) args[0];
                    T item = gson.fromJson(obj.toString(), mModelClass);
                    Log.d("socket:" + eventName + ":", obj.toString());
                    callback.onCreated(item);
                }
            }
        });
    }

    @Override
    public void onUpdated(final OnUpdatedCallback<T> callback) {
        final String eventName = "updated";
        socket.on(mServicePath + " " + eventName, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if (args.length == 1) {
                    JSONObject obj = (JSONObject) args[0];
                    T item = gson.fromJson(obj.toString(), mModelClass);
                    Log.d("socket:" + eventName + ":", obj.toString());
                    callback.onUpdated(item);
                }
            }
        });
    }

    @Override
    public void onRemoved(final OnRemovedCallback<T> callback) {
        final String eventName = "removed";
        socket.on(mServicePath + " " + eventName, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if (args.length == 1) {
                    JSONObject obj = (JSONObject) args[0];
                    T item = gson.fromJson(obj.toString(), mModelClass);
                    Log.d("socket:" + eventName + ":", obj.toString());
                    callback.onRemoved(item);
                }
            }
        });
    }

    @Override
    public void onPatched(final OnPatchedCallback<T> callback) {
        final String eventName = "patched";
        socket.on(mServicePath + " " + eventName, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if (args.length == 1) {
                    JSONObject obj = (JSONObject) args[0];
                    T item = gson.fromJson(obj.toString(), mModelClass);
                    Log.d("socket:" + eventName + ":", obj.toString());
                    callback.onPatched(item);
                }
            }
        });
    }

    private void configureSocket(String baseUrl, FeathersSocketIO.Options options) {
        IO.Options opts = new IO.Options();
        opts.forceNew = options.forceNew;
        opts.reconnection = options.reconnection;

        try {
            socket = IO.socket(baseUrl, opts);
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d("SOCKET:", "Connected");
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d("SOCKET:", "Disconnected");
                }
            });
            socket.connect();
        } catch (URISyntaxException exception) {
            Log.e("FeathersSocket", "Error parsing URL", exception);
        }
    }

//    private void listenForEvent(final String eventName, final OnEventCallback<T> callback) {
//        socket.on(mServicePath + " " + eventName, new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                if (args.length == 1) {
//                    JSONObject obj = (JSONObject) args[0];
//                    T item = gson.fromJson(obj.toString(), mModelClass);
//                    Log.d("socket:" + eventName + ":", obj.toString());
//                    callback.onSuccess(item);
//                }
//            }
//        });
//    }


}
