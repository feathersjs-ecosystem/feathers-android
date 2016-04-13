package org.feathersjs.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.hamcrest.Description;
import org.json.JSONObject;
import org.mockito.ArgumentMatcher;

import static org.mockito.Matchers.argThat;

public class JSONObjectMatcher extends ArgumentMatcher<JSONObject> {
    private final JSONObject expected;

    public JSONObjectMatcher(JSONObject expected) {
        this.expected = expected;
    }

    public static JSONObject jsonEq(JSONObject expected) {
        return argThat(new JSONObjectMatcher(expected));
    }

    @Override
    public boolean matches(Object argument) {
        if (expected == null) return argument == null;
        if (!(argument instanceof JSONObject)) return false;
        JSONObject actual = (JSONObject) argument;

        JsonParser parser = new JsonParser();
        JsonElement expectedElement = parser.parse(expected.toString());
        JsonElement actualElement = parser.parse(actual.toString());

        boolean isMatch = expectedElement.equals(actualElement);
        return isMatch;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(expected.toString());
    }
}