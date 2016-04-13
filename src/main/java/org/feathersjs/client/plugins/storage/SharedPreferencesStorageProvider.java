package org.feathersjs.client.plugins.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesStorageProvider implements IStorageProvider {

    private SharedPreferences mPreferences;

    public SharedPreferencesStorageProvider(Context context) {
        mPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
    }

    public Object getItem(String key) {
        return mPreferences.getString(key, "");
    }
    @Override
    public void setItem(String key, Object value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(key, value.toString());
        editor.apply();
    }
}