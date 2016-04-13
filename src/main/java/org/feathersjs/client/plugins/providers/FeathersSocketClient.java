package org.feathersjs.client.plugins.providers;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import org.feathersjs.client.service.Result;
import org.feathersjs.client.service.OnCreatedCallback;
import org.feathersjs.client.service.OnPatchedCallback;
import org.feathersjs.client.service.OnRemovedCallback;
import org.feathersjs.client.service.OnUpdatedCallback;
import org.feathersjs.client.utilities.Serialization;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.Map;

import org.feathersjs.client.service.FeathersService.FeathersCallback;

public class FeathersSocketClient<T> extends IFeathersProvider {

    private String mServicePath = null;
    private Gson gson = null;
    private boolean setup = false;
    private FeathersSocketIO.Options mOptions = new FeathersSocketIO.Options();
    Class mModelClass;

    private Socket mSocket;

    public FeathersSocketClient(String baseUrl, String servicePath, Class modelClass, Socket socket) {
        mModelClass = modelClass;
        mServicePath = servicePath;
        gson = new GsonBuilder().create();
        mOptions = new FeathersSocketIO.Options();

        IO.Options opts = new IO.Options();
//        opts.forceNew = mOptions.forceNew;
//        opts.reconnection = options.reconnection;

        if (socket != null) {
            mSocket = socket;
        } else {

            try {
                mSocket = IO.socket(baseUrl, opts);
                mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
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
                mSocket.connect();
            } catch (URISyntaxException exception) {
                Log.e("FeathersSocket", "Error parsing URL", exception);
            }
        }
    }

    @Override
    public void find(Map<String, String> params, final FeathersCallback<Result<T>> cb) {
        JSONObject obj = new JSONObject(params);
        mSocket.emit(mServicePath + "::find", obj, new Ack() {
            @Override
            public void call(Object... args) {
                handleCallbackList(args, cb);
            }
        });
    }


    private <J> void handleCallback(Object[] args, FeathersCallback<J> cb) {
        // If theres only one argument then it was an error
        if(args.length == 1) {
            JSONObject error = (JSONObject) args[0];
//                    Result result = Serialization.deserializeArray(array, mModelClass, gson);
            cb.onError(error.optString("message", error.toString()));
        } else {
            JSONObject array = (JSONObject) args[1];
//            Result result = Serialization.deserializeArray(array, mModelClass, gson);
//            cb.onSuccess(result);
        }
    }

    private void handleCallbackList(Object[] args, FeathersCallback<Result<T>> cb) {
        // If theres only one argument then it was an error
        if(args.length == 1) {
            JSONObject error = (JSONObject) args[0];
//                    Result result = Serialization.deserializeArray(array, mModelClass, gson);
            cb.onError(error.optString("message", error.toString()));
        } else {
            JSONObject array = (JSONObject) args[1];
            Result<T> result = Serialization.deserializeArray(array, mModelClass, gson);
            cb.onSuccess(result);
        }
    }


    @Override
    public <J> void get(String id, final FeathersCallback<J> cb) {
        mSocket.emit(mServicePath + "::get", id, new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject obj = (JSONObject) args[1];
//                J item = gson.fromJson(obj.toString(), mModelClass);
//                cb.onSuccess(item);
            }
        });
    }

    @Override
    public <J> void remove(String id, final FeathersCallback<J> cb) {
        mSocket.emit(mServicePath + "::remove", id, new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject obj = (JSONObject) args[1];
//                T item = gson.fromJson(obj.toString(), mModelClass);
//                cb.onSuccess(item);
            }
        });
    }

    @Override
    public <J> void update(String id, J item, final FeathersCallback<J> cb) {

        mSocket.emit(mServicePath + "::update", id, gson.toJson(item), new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject obj = (JSONObject) args[1];
//                T item = gson.fromJson(obj.toString(), mModelClass);
//                cb.onSuccess(item);
            }
        });
//        throw new UnsupportedOperationException();
    }

    @Override
    public <J> void patch(String id, J item, final FeathersCallback<J> cb) {
        mSocket.emit(mServicePath + "::patch", id, gson.toJson(item), new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject obj = (JSONObject) args[1];
//                T item = gson.fromJson(obj.toString(), mModelClass);
//                cb.onSuccess(item);
            }
        });
    }

    @Override
    public <J> void create(J item, final FeathersCallback<J> cb) {
//        throw new UnsupportedOperationException();
        String jsonString = gson.toJson(item);
//
        JSONObject obj = null;
        try {
            obj = new JSONObject(jsonString);
            mSocket.emit(mServicePath + "::create", obj, new Ack() {
                @Override
                public void call(Object... args) {
                    handleCallback(args, cb);
//                    Log.d("SOCKETCREATE!!", args.length + "");
//                    Log.d("SOCKETCREATE!!", args[0].toString());
//                    JSONObject obj = (JSONObject) args[1];
//                    Type todoType = new TypeToken<J>() {
//                    }.getType();
//                    J item = gson.fromJson(obj.toString(), todoType);
//                    cb.onSuccess(item);
                }
            });
        } catch (JSONException e) {
            Log.e("create:", e.getMessage(), e);
        }
    }

//    @Override
    public <J> void onCreated(final OnCreatedCallback<J> callback) {
        final String eventName = "created";
        mSocket.on(mServicePath + " " + eventName, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if (args.length == 1) {
                    JSONObject obj = (JSONObject) args[0];
//                    T item = gson.fromJson(obj.toString(), mModelClass);
//                    Log.d("socket:" + eventName + ":", obj.toString());
//                    callback.onCreated(item);
                }
            }
        });
    }

//    @Override
    public <J> void onUpdated(final OnUpdatedCallback<J> callback) {
        final String eventName = "updated";
        mSocket.on(mServicePath + " " + eventName, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if (args.length == 1) {
//                    JSONObject obj = (JSONObject) args[0];
//                    T item = gson.fromJson(obj.toString(), mModelClass);
//                    Log.d("socket:" + eventName + ":", obj.toString());
//                    callback.onUpdated(item);
                }
            }
        });
    }

//    @Override
    public <J> void onRemoved(final OnRemovedCallback<J> callback) {
        final String eventName = "removed";
        mSocket.on(mServicePath + " " + eventName, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if (args.length == 1) {
                    JSONObject obj = (JSONObject) args[0];
//                    J item = gson.fromJson(obj.toString(), mModelClass);
//                    Log.d("socket:" + eventName + ":", obj.toString());
//                    callback.onRemoved(item);
                }
            }
        });
    }

//    @Override
    public <J> void onPatched(final OnPatchedCallback<J> callback) {
        final String eventName = "patched";
        mSocket.on(mServicePath + " " + eventName, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if (args.length == 1) {
                    JSONObject obj = (JSONObject) args[0];
//                    J item = gson.fromJson(obj.toString(), mModelClass);
//                    Log.d("socket:" + eventName + ":", obj.toString());
//                    callback.onPatched(item);
                }
            }
        });
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
