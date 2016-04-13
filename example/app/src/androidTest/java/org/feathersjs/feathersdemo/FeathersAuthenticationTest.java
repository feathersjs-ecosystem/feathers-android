package org.feathersjs.feathersdemo;

import junit.framework.TestCase;

import org.junit.Before;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class FeathersAuthenticationTest extends TestCase {
    private static String TAG = "FeathersAuthTest";

    @Before
    public void initialize() {

    }

    public void testAuthenticationAddsAuthHeaderInRestProvider() {
        assertThat(false, is(true));
    }

    public void testAuthenticationAddsAuthQueryInSocketProvider() {
        assertThat(false, is(true));
    }

}
