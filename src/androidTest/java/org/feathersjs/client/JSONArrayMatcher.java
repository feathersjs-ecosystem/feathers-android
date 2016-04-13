package org.feathersjs.client;

import org.hamcrest.Description;
import org.json.JSONObject;
import org.mockito.ArgumentMatcher;

import static org.mockito.Matchers.argThat;

import org.hamcrest.Description;
import org.json.JSONArray;
import org.mockito.ArgumentMatcher;

import static org.mockito.Matchers.argThat;

public class JSONArrayMatcher extends ArgumentMatcher<JSONArray> {
    private final JSONArray expected;

    public JSONArrayMatcher(JSONArray expected) {
        this.expected = expected;
    }

    public static JSONArray jsonEq(JSONArray expected) {
        return argThat(new JSONArrayMatcher(expected));
    }

    @Override
    public boolean matches(Object argument) {
        if (expected == null) return argument == null;
        if (!(argument instanceof JSONArray)) return false;
        JSONArray actual = (JSONArray) argument;
        return expected.toString().equals(actual.toString());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(expected.toString());
    }
}