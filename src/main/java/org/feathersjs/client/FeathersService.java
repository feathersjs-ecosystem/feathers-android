package org.feathersjs.client;

import android.util.Log;

import org.feathersjs.client.callbacks.OnCreatedCallback;
import org.feathersjs.client.callbacks.OnPatchedCallback;
import org.feathersjs.client.callbacks.OnRemovedCallback;
import org.feathersjs.client.callbacks.OnUpdatedCallback;
import org.feathersjs.client.hooks.HookCallback;
import org.feathersjs.client.hooks.HookObject;
import org.feathersjs.client.hooks.HookType;
import org.feathersjs.client.interfaces.IFeathersService;
import org.feathersjs.client.interfaces.IFeathersProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

public class FeathersService<T> implements IFeathersService<T> {

    private final String TAG = "!FeathersService";

    private IFeathersProvider<T> mProvider;
    private final String mServiceName;
    private final String mBaseUrl;
    private final Class<T> mModelClass;
    private final HashMap<String, List<HookObject<T>>> registeredBeforeHooks;
    private final HashMap<String, List<HookObject<T>>> registeredAfterHooks;

    private final HashMap<String, List<Observable<HookObject<T>>>> registeredBeforeObservables;

    public interface FeathersCallback<T> {
        void onSuccess(T t);

        void onError(String errorMessage);
    }


    public FeathersService(String baseUrl, String serviceName, Class<T> modelClass) {

        mBaseUrl = baseUrl;
        mServiceName = serviceName;
        mModelClass = modelClass;


        registeredBeforeHooks = new HashMap<>();
        registeredAfterHooks = new HashMap<>();

        registeredBeforeObservables = new HashMap<>();

        initializeHooks();
//
//
//        this.mSocketProvider = new FeathersSocketClient<T>(baseUrl, serviceName, modelClass);
//        this.mRESTProvider = new FeathersRestClient<T>(baseUrl, serviceName, modelClass);
//
//        this.mProvider = new FeathersSocketClient<T>(baseUrl, serviceName, modelClass);
    }

    private void initializeHooks() {
        for (ServiceEvent eventName : ServiceEvent.values()) {
            registeredBeforeHooks.put(eventName.toString(), new ArrayList<HookObject<T>>());
            registeredAfterHooks.put(eventName.toString(), new ArrayList<HookObject<T>>());

            registeredBeforeObservables.put(eventName.toString(), new ArrayList<Observable<HookObject<T>>>());
        }
    }

    public void setProvider(final IFeathersProvider<T> provider) {
        mProvider = provider;
    }

    public IFeathersProvider<T> getProvider() {
        return mProvider;
    }

    @Override
    public void find(final Map<String, String> params, final FeathersCallback<Result<T>> cb) {

        //TODO: Call before hooks

//        Observable<HookObject<Article>> hook1Observer = Observable.create(
//                new Observable.OnSubscribe<HookObject<Article>>() {
//                    @Override
//                    public void call(Subscriber<? super HookObject<Article>> subscriber) {
//
////                        sub.onNext("Hello, world!");
////                        sub.onCompleted();
//                    }
//                }


        Observable
                .from(registeredBeforeHooks.get(ServiceEvent.FIND.toString()))
                .subscribe(new Subscriber<HookObject<T>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Completed All Before Hooks.");

                        //TODO: now finally call the provider with the modified hook data & params
                        mProvider.find(params, cb);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.err.println("Whoops: " + throwable.getMessage());
                    }

                    @Override
                    public void onNext(HookObject<T> hook) {
                        Log.d(TAG, "SHOULD CALL before:" + hook.method);
                        hook.hookCallback.call(hook);
//                        this.

                        //TODO: Call the hook and pass in the newly modified data?
                        // hook.c
//                        hook.hookCallback.call();
                    }
                });


    }

    // Support passing no params
    @Override
    public void find(FeathersCallback<Result<T>> cb) {
        find(new HashMap<String, String>(), cb);
    }

    @Override
    public void get(String id, FeathersCallback<T> cb) {
        mProvider.get(id, cb);
    }

    @Override
    public void remove(String id, FeathersCallback<T> cb) {
        mProvider.remove(id, cb);
    }

    @Override
    public void create(T item, FeathersCallback<T> cb) {
        mProvider.create(item, cb);
    }

    @Override
    public void update(String id, T item, FeathersCallback<T> cb) {
        mProvider.update(id, item, cb);
    }

    @Override
    public void patch(String id, T item, FeathersCallback<T> cb) {
        mProvider.patch(id, item, cb);
    }

    /*  Hooks  */
    public void before(ServiceEvent event, HookCallback<T> hook) {
        registerHook(HookType.BEFORE, event, hook);
    }

    public void before(ServiceEvent event, List<HookCallback<T>> hooks) {
        for (HookCallback<T> hook : hooks) {
            registerHook(HookType.BEFORE, event, hook);
        }
    }

//    public void after(ServiceEvent event, HookCallback<T> hook) {
//        registerHook(HookType.AFTER, event, hook);
//    }
//
//    public void after(ServiceEvent event, List<HookCallback<T>> hooks) {
//        for (HookCallback<T> hook : hooks) {
//            registerHook(HookType.AFTER, event, hook);
//        }
//    }

    private void registerHook(HookType type, ServiceEvent event, final HookCallback<T> hook) {
        Log.d(TAG, "registerHook:" + type + "|" + event);
        final HookObject<T> obj = new HookObject<>();
        obj.method = event;
        obj.type = type;
        obj.hookCallback = hook;

        if (type.equals(HookType.BEFORE)) {
            registeredBeforeHooks.get(event.toString()).add(obj);

            //Observable<HookObject<T>> observable = new

            Observable<HookObject<T>> hook1Observer = Observable.create(
                    new Observable.OnSubscribe<HookObject<T>>() {
                        @Override
                        public void call(Subscriber<? super HookObject<T>> subscriber) {
                        //obj.hookCallback.call(o);
//                        sub.onNext("Hello, world!");
//                        sub.onCompleted();
                        }
                    });


            registeredBeforeObservables.get(event.toString()).add(hook1Observer);


        } else {
            registeredAfterHooks.get(event.toString()).add(obj);
        }
    }


    /* Events */
    public FeathersService<T> onCreated(final OnCreatedCallback<T> callback) {
        mProvider.onCreated(callback);
        return this;
    }

    public FeathersService<T> onUpdated(final OnUpdatedCallback<T> callback) {
        mProvider.onUpdated(callback);
        return this;
    }

    public FeathersService<T> onRemoved(final OnRemovedCallback<T> callback) {
        mProvider.onRemoved(callback);
        return this;
    }

    public FeathersService<T> onPatched(final OnPatchedCallback<T> callback) {
        mProvider.onPatched(callback);
        return this;
    }


    public String getName() {
        return mServiceName;
    }

    public Class<T> getModelClass() {
        return mModelClass;
    }

    public String getBaseUrl() {
        return mBaseUrl;
    }


}