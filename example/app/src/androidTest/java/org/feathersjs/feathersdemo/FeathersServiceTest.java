package org.feathersjs.feathersdemo;

import junit.framework.TestCase;

import org.junit.Before;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class FeathersServiceTest extends TestCase {
    private static String TAG = "FeathersServiceTest";

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
