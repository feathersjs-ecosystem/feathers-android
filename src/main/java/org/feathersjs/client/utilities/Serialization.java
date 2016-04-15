package org.feathersjs.client.utilities;

import android.util.Log;

import com.google.gson.Gson;

import org.feathersjs.client.service.Result;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.entity.StringEntity;

public class Serialization {

    public static <T> Result<T> deserializeArray(JSONObject object, Class<T> mModelClass, Gson gson) {
        Result<T> result = new Result<>();
        try {
            result.limit = object.getInt("limit");
            result.skip = object.getInt("skip");
            result.total = object.getInt("total");
            result.data = new ArrayList<T>();

            JSONArray dataArray = object.getJSONArray("data");

            for (int i = 0; i < dataArray.length(); i++) {
                try {
                    JSONObject obj = dataArray.getJSONObject(i);
                    T item = gson.fromJson(obj.toString(), mModelClass);
                    result.data.add(item);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (JSONException ex) {
            Log.e("Feathers", ex.getMessage(), ex);
        }
        return result;
    }

    public static <T> T deserializeObject(JSONObject object, Class<T> mModelClass, Gson gson) {
        T item = gson.fromJson(object.toString(), mModelClass);
        return item;
    }

    public static <J> StringEntity getEntityForObject(J item, Gson gson) {

        String json;
        if (item instanceof JSONObject)
            json = item.toString();
        else
            json = gson.toJson(item);
        StringEntity entity = null;
        try {
            entity = new StringEntity(json);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return entity;
    }
}
