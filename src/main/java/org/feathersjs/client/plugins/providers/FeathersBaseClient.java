package org.feathersjs.client.plugins.providers;

import android.util.Log;

import com.google.gson.Gson;

import org.feathersjs.client.Feathers;
import org.feathersjs.client.plugins.authentication.FeathersAuthentication;
import org.feathersjs.client.service.FeathersService;
import org.feathersjs.client.service.OnCreatedCallback;
import org.feathersjs.client.service.OnPatchedCallback;
import org.feathersjs.client.service.OnRemovedCallback;
import org.feathersjs.client.service.OnUpdatedCallback;
import org.feathersjs.client.service.Result;
import org.feathersjs.client.service.ServiceEvent;
import org.json.JSONObject;

import java.util.Map;

public class FeathersBaseClient extends IFeathersProvider {
    private final String TAG = FeathersBaseClient.this.getClass().toString();
//    String mServicePath = null;
    Gson gson = null;
    FeathersAuthentication mAuthentication;
    Feathers mFeathers;
    OnCreatedCallback mOnCreatedCallback;
    OnRemovedCallback mOnRemovedCallback;
    OnUpdatedCallback mOnUpdatedCallback;
    OnPatchedCallback mOnPatchedCallback;

//    String mBaseUrl;
//    String mServiceName;
//    String mServiceUrl;

    <J> void sendEvent(ServiceEvent event, J item) {
        try {
            if (event == ServiceEvent.CREATE) {
                if (mOnCreatedCallback != null) {
                    mOnCreatedCallback.onCreated(item);
                }
            } else if (event == ServiceEvent.UPDATE) {
                if (mOnUpdatedCallback != null) {
                    mOnUpdatedCallback.onUpdated(item);
                }
            } else if (event == ServiceEvent.REMOVE) {
                if (mOnRemovedCallback != null) {
                    mOnRemovedCallback.onRemoved(item);
                }
            } else if (event == ServiceEvent.PATCH) {
                if (mOnPatchedCallback != null) {
                    mOnPatchedCallback.onPatched(item);
                }
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error sending event:" + event, ex);
        }
    }

    @Override
    public <J> void find(String baseUrl, String serviceName, Map<String, String> params, FeathersService.FeathersCallback<Result<J>> cb, Class<J> jClass) {

    }

    @Override
    public <J> void get(String baseUrl, String serviceName, String id, FeathersService.FeathersCallback<J> cb, Class<J> jClass) {

    }

    @Override
    public <J> void remove(String baseUrl, String serviceName, String id, FeathersService.FeathersCallback<J> cb, Class<J> jClass) {

    }

    @Override
    public <J, K> void create(String baseUrl, String serviceName, J item, FeathersService.FeathersCallback<K> cb, Class<K> jClass) {

    }

    @Override
    public <J> void update(String baseUrl, String serviceName, String id, J item, FeathersService.FeathersCallback<J> cb, Class<J> jClass) {

    }

    @Override
    public <J> void patch(String baseUrl, String serviceName, String id, JSONObject item, FeathersService.FeathersCallback<J> cb, Class<J> jClass) {

    }

    /*
        Service events
    */

    //@Override
    public <J> void onCreated(String serviceName, final OnCreatedCallback<J> callback) {
        mOnCreatedCallback = callback;
    }

    //@Override
    public <J> void onUpdated(String serviceName, final OnUpdatedCallback<J> callback) {
        mOnUpdatedCallback = callback;
    }

    // @Override
    public <J> void onRemoved(String serviceName, final OnRemovedCallback<J> callback) {
        mOnRemovedCallback = callback;
    }

    //@Override
    public <J> void onPatched(String serviceName, final OnPatchedCallback<J> callback) {
        mOnPatchedCallback = callback;
    }
}
