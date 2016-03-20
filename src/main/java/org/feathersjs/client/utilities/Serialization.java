package org.feathersjs.client.utilities;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Serialization {

    public static <T> List<T> deserializeArray(JSONArray array, Class<T> mModelClass, Gson gson) {

        List<T> items = new ArrayList<T>();
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject obj = array.getJSONObject(i);
                T item = gson.fromJson(obj.toString(), mModelClass);
                items.add(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return items;
    }

    public static <T> T deserializeObject(JSONObject object, Class<T> mModelClass, Gson gson) {
        T item = gson.fromJson(object.toString(), mModelClass);
        return item;
    }
}
