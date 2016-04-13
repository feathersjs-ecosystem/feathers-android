package org.feathersjs.client.service;

import android.util.Log;

import org.feathersjs.client.hooks.HookCallback;
import org.feathersjs.client.hooks.HookContext;
import org.feathersjs.client.hooks.HookDoneCallback;
import org.feathersjs.client.hooks.HookObject;
import org.feathersjs.client.hooks.HookType;
import org.feathersjs.client.providers.IFeathersProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class FeathersService<T> implements IFeathersService {

    private final String TAG = "!FeathersService";

    private IFeathersProvider mProvider;
    private final String mServiceName;
    private final String mBaseUrl;
    private final Class<T> mModelClass;
    private final HashMap<String, List<HookObject>> registeredBeforeHooks;
    private final HashMap<String, List<HookObject>> registeredAfterHooks;

    public interface FeathersCallback<T> {
        void onError(String errorMessage);
        void onSuccess(T t);
    }

    public interface FeathersListCallback<T> {
        void onError(String errorMessage);
        void onSuccess(Result<T> t);
    }


    public FeathersService(String baseUrl, String serviceName, Class<T> modelClass) {

        mBaseUrl = baseUrl;
        mServiceName = serviceName;
        mModelClass = modelClass;

        registeredBeforeHooks = new HashMap<>();
        registeredAfterHooks = new HashMap<>();

        initializeHooks();
    }

    private void initializeHooks() {
        for (ServiceEvent eventName : ServiceEvent.values()) {
            registeredBeforeHooks.put(eventName.toString(), new ArrayList<HookObject>());
            registeredAfterHooks.put(eventName.toString(), new ArrayList<HookObject>());
        }
    }

    public void setProvider(final IFeathersProvider provider) {
        mProvider = provider;
    }

    public IFeathersProvider getProvider() {
        return mProvider;
    }

    @Override
    public <J> void find(final Map<String, String> params, final FeathersCallback<Result<J>> cb) {

        final HookContext context = new HookContext();
        context.method = ServiceEvent.FIND;
        context.type = HookType.BEFORE;
//        context.params = params;

        if(registeredAfterHooks.get(ServiceEvent.FIND.toString()).size() == 0) {
            mProvider.find(params, cb);
        }

        Observable.create(new Observable.OnSubscribe<HookObject>() {
            @Override
            public void call(final Subscriber<? super HookObject> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        for (int i = 0; i < registeredBeforeHooks.get(ServiceEvent.FIND.toString()).size(); i++) {
                            final HookObject obj = registeredBeforeHooks.get(ServiceEvent.FIND.toString()).get(i);
                            obj.context.params = context.params;

                            Log.d(TAG, "calling hook " + (i + 1));
                            final int hookNumber = i + 1;
                            obj.hookCallback.call(obj, new HookDoneCallback() {
                                @Override
                                public void onDone(HookObject hookObject) {
                                    Log.d(TAG, "done hook " + hookNumber);
                                    Log.d("find:onNext", "onDone()" + hookObject.toString());
                                    subscriber.onNext(obj);
                                    if(hookNumber == registeredBeforeHooks.get(ServiceEvent.FIND.toString()).size()) {
                                        Log.d("find:onNext", "onDone()COMPLETE" + hookNumber);
                                    }
                                }
                            });


                        }
//                        subscriber.onCompleted();
                    }
                } catch (Exception ex) {
                    subscriber.onError(ex);
                }
            }
        }).subscribe(new Action1<HookObject>() {
            @Override
            public void call(HookObject hookObject) {
                context.params = hookObject.context.params;
                Log.d(TAG, "" + hookObject.toString());
//                hookObject.hookCallback.call(hookObject, new HookDoneCallback() {
//                    @Override
//                    public void onDone(HookObject hookObject) {
//                        Log.d("find:onNext", "onDone()" + hookObject.toString());
//
//                    }
//                });
            }
        });


        /*
        Subscriber <HookObject> hookSubscriber = new Subscriber<HookObject>() {
            @Override
            public void onCompleted() {
                Log.d("find:onCompleted", "");
                mProvider.find(params, cb);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("find:onError", e.getMessage(), e);
                cb.onError(e.getMessage());
            }

            @Override
            public void onNext(HookObject hookObject) {
                Log.d("find:onNext", hookObject.toString());
                hookObject.context = context;
                //TODO: Call callback with updated params
                hookObject.hookCallback.call(hookObject, new HookDoneCallback() {
                    @Override
                    public void onDone(HookObject hookObject) {
                        Log.d("find:onNext", "onDone()" + hookObject.toString());
                    }
                });
            }
        };

        Observable
                .from(registeredBeforeHooks.get(ServiceEvent.FIND.toString()))
                .subscribe(hookSubscriber);
*/

//        Observable.create(new Observable.OnSubscribe<HookObject>() {
//            @Override
//            public void call(Subscriber<? super HookObject> subscriber) {
//                try {
//                    if (!subscriber.isUnsubscribed()) {
//                        for (int i = 0; i < registeredBeforeHooks.get(ServiceEvent.FIND.toString()).size(); i++) {
//                            subscriber.onNext(registeredBeforeHooks.get(ServiceEvent.FIND.toString()).get(i));
//                        }
//                        subscriber.onCompleted();
//                    }
//                } catch (Exception ex) {
//                    subscriber.onError(ex);
//                }
//            }
//        }).subscribe(new Action1<HookObject>() {
//            @Override
//            public void call(HookObject hookObject) {
////                System.out.println("Got: " + integer);
//                hookObject.hookCallback.call(hookObject, new HookDoneCallback() {
//                    @Override
//                    public void onDone(HookObject hookObject) {
//                        Log.d("find:onNext", "onDone()" + hookObject.toString());
//
//                    }
//                });
//            }
//        });


        /*

        new Subscriber<HookObject<J>>() {
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
                    public void onNext(HookObject<J> hook) {
                        Log.d(TAG, "SHOULD CALL before:" + hook.context.method);
//                        hook.hookCallback.call(hook, new HookDoneCallback() {
//                            @Override
//                            public void onDone(HookObject<J> hookObject) {
//
//                            }
//                        });
//                        this.

                        //TODO: Call the hook and pass in the newly modified data?
                        // hook.c
//                        hook.hookCallback.call();
                    }
                }

         */


    }

    // Support passing no params
    @Override
    public <J> void find(FeathersCallback<Result<J>> cb) {
        find(new HashMap<String, String>(), cb);
    }

    @Override
    public <J> void get(String id, FeathersCallback<J> cb) {
        mProvider.get(id, cb);
    }

    @Override
    public <J> void remove(String id, FeathersCallback<J> cb) {
        mProvider.remove(id, cb);
    }

    @Override
    public <J> void create(J item, FeathersCallback<J> cb) {
        mProvider.create(item, cb);
    }

    @Override
    public <J> void update(String id, J item, FeathersCallback<J> cb) {
        mProvider.update(id, item, cb);
    }

    @Override
    public <J> void patch(String id, J item, FeathersCallback<J> cb) {
        mProvider.patch(id, item, cb);
    }



    /*  Hooks  */
    public <J> void before(ServiceEvent event, HookCallback<J> hook) {
        registerHook(HookType.BEFORE, event, hook);
    }

    public <J> void before(ServiceEvent event, List<HookCallback<J>> hooks) {
        for (HookCallback<J> hook : hooks) {
            registerHook(HookType.BEFORE, event, hook);
        }
    }

    public <J> void after(ServiceEvent event, HookCallback<J> hook) {
        registerHook(HookType.AFTER, event, hook);
    }

    public <J> void after(ServiceEvent event, List<HookCallback<J>> hooks) {
        for (HookCallback<J> hook : hooks) {
            registerHook(HookType.AFTER, event, hook);
        }
    }

    private <J> void registerHook(HookType type, ServiceEvent event, final HookCallback<J> hook) {
        Log.d(TAG, "registerHook:" + type + "|" + event);
        final HookObject<J> obj = new HookObject<>();
        obj.context = new HookContext();
        obj.context.method = event;
        obj.context.type = type;
        obj.hookCallback = hook;

        if (type.equals(HookType.BEFORE)) {
            registeredBeforeHooks.get(event.toString()).add(obj);
        } else {
            registeredAfterHooks.get(event.toString()).add(obj);
        }
    }


    /* Events */
    public <J> FeathersService onCreated(final OnCreatedCallback<J> callback) {
        mProvider.onCreated(callback);
        return this;
    }

    public <J> FeathersService onUpdated(final OnUpdatedCallback<J> callback) {
        mProvider.onUpdated(callback);
        return this;
    }

    public <J> FeathersService onRemoved(final OnRemovedCallback<J> callback) {
        mProvider.onRemoved(callback);
        return this;
    }

    public <J> FeathersService onPatched(final OnPatchedCallback<J> callback) {
        mProvider.onPatched(callback);
        return this;
    }


    public String getName() {
        return mServiceName;
    }

    public Class getModelClass() {
        return mModelClass;
    }

    public String getBaseUrl() {
        return mBaseUrl;
    }


}