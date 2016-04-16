package org.feathersjs.feathersdemo;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import org.feathersjs.client.Feathers;
import org.feathersjs.client.service.FeathersService;
import org.feathersjs.client.service.OnCreatedCallback;
import org.feathersjs.client.service.OnPatchedCallback;
import org.feathersjs.client.service.OnRemovedCallback;
import org.feathersjs.client.service.OnUpdatedCallback;
import org.feathersjs.client.service.Result;
import org.feathersjs.feathersdemo.models.Message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FeathersServiceAdapter<T, K extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<K> {
    private ArrayList<T> mDataset;
    private Activity mActivity;
    FeathersService<T> mService;
    private Class<K> mViewHolderClass;
    private int mResource;

    public ArrayList<T> getDataSet() {
        return mDataset;
    }

    public FeathersServiceAdapter(Activity activity, FeathersService<T> service, int resource) {
        mDataset = new ArrayList<>();
        mActivity = activity;
        mService = service;
        mResource = resource;

        listenForEvents();
    }

    private void listenForEvents() {

        mService.onCreated(new OnCreatedCallback<T>() {
            @Override
            public void onCreated(final T item) {
                Log.d("onCreated", item.toString());
                mActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        mDataset.add(item);
                        notifyDataSetChanged();
                    }
                });
            }
        });

        mService.onRemoved(new OnRemovedCallback<T>() {
            @Override
            public void onRemoved(final T removedItem) {
                Log.d("onRemoved", removedItem.toString());
                mActivity.runOnUiThread(new Runnable() {
                    public void run() {

                        Iterator<T> iter = mDataset.iterator();
                        while (iter.hasNext()) {
                            T item = iter.next();
                            if (item.equals(removedItem)) {
                                iter.remove();
                                notifyDataSetChanged();
                            }
                        }

                    }
                });
            }
        });

        mService.onUpdated(new OnUpdatedCallback<T>() {
            @Override
            public void onUpdated(final T updatedItem) {
                Log.d("onUpdated", updatedItem.toString());
                mActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        for (T item : mDataset) {
                            if (updatedItem.equals(item)) {
                                mDataset.set(mDataset.indexOf(item), updatedItem);
                                notifyDataSetChanged();
                            }
                        }
                    }
                });
            }
        });

        mService.onPatched(new OnPatchedCallback<T>() {
            @Override
            public void onPatched(final T patchedItem) {
                Log.d("onPatched", patchedItem.toString());
                mActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        int currentIndex = 0;
                        int indexToUpdate = 0;
                        Iterator<T> iter = mDataset.iterator();
                        while (iter.hasNext()) {
                            T item = iter.next();
                            if (item.equals(patchedItem)) {
                                indexToUpdate = currentIndex;
                                iter.remove();
                                notifyDataSetChanged();
                            }
                            currentIndex++;
                        }
                        mDataset.add(indexToUpdate, patchedItem);
                    }
                });
            }
        });
    }

    public void reload() {
        mService.find(new FeathersService.FeathersCallback<Result<T>>() {
            @Override
            public void onSuccess(Result<T> t) {
                Log.d("MainActivity:find", "onSuccess :" + t.toString());
                mDataset.addAll(t.data);
                mActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("MainActivity:find", errorMessage);
            }
        });
    }

    @Override
    public void onBindViewHolder(K holder, int position) {
        throw new UnsupportedOperationException();
    }

    @Override
    public K onCreateViewHolder(ViewGroup parent, int viewType) {
        throw new UnsupportedOperationException();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
