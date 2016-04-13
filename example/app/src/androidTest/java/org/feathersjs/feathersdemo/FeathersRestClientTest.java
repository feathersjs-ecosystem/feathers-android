package org.feathersjs.feathersdemo;

import junit.framework.TestCase;

import org.junit.Before;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class FeathersRestClientTest extends TestCase {
    private static String TAG = "FeathersRestTest";

    @Before
    public void initialize() {

    }

    public void testAuthenticationAddsAuthHeader() {
        assertThat(false, is(true));
        //TODO: Mock out RestClient / Provider?
    }

}
