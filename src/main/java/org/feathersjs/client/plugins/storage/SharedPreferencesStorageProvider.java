package org.feathersjs.client.plugins.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferencesStorageProvider implements IStorageProvider {

    private SharedPreferences mPreferences;

    public SharedPreferencesStorageProvider(Context context) {
        mPreferences = context.getSharedPreferences("feathers-authentication", Context.MODE_PRIVATE);
        Log.d("PREFS", mPreferences.getAll().toString());
    }

    public Object getItem(String key) {
        return mPreferences.getString(key, null);
    }
    @Override
    public void setItem(String key, Object value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(key, value.toString());
        editor.apply();
    }
}