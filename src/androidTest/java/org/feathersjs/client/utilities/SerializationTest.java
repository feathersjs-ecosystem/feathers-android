package org.feathersjs.client.utilities;

import com.google.gson.Gson;

import org.feathersjs.client.service.Result;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.testng.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SerializationTest {

    private class TestClass {
        public String title;
        public String description;
    }

    @Test
    public void testDeserializeArrayWithNoResults() {
       Gson gson = new Gson();
        try {
            JSONObject object = new JSONObject();
            object.put("limit", 10);
            object.put("skip", 0);
            object.put("total", 0);
            object.put("data", new JSONArray());

            Result<JSONObject> object1 = Serialization.deserializeArray(object, JSONObject.class, gson);

            assertEquals(object1.limit, 10);
            assertEquals(object1.skip, 0);
            assertEquals(object1.total, 0);
            assertEquals(object1.data.size(), 0);
        } catch(JSONException ex) {

        }
    }

    @Test
    public void testDeserializeArrayWithResults() {
        Gson gson = new Gson();
        try {
            JSONObject object = new JSONObject();
            object.put("limit", 10);
            object.put("skip", 0);
            object.put("total", 2);

            JSONArray data = new JSONArray();

            JSONObject resultObject = new JSONObject();
            resultObject.put("title", "title 1");
            resultObject.put("description", "description 1");
            data.put(resultObject);

            resultObject = new JSONObject();
            resultObject.put("title", "title 2");
            resultObject.put("description", "description 2");
            data.put(resultObject);

            object.put("data", data);

            Result<TestClass> result = Serialization.deserializeArray(object, TestClass.class, gson);

            assertEquals(result.limit, 10);
            assertEquals(result.skip, 0);
            assertEquals(result.total, 2);
            assertEquals(result.data.size(), 2);

            assertEquals(result.data.get(0).title, "title 1");
            assertEquals(result.data.get(0).description, "description 1");

            assertEquals(result.data.get(1).title, "title 2");
            assertEquals(result.data.get(1).description, "description 2");

        } catch(JSONException ex) {

        }
    }
}
