package org.feathersjs.client.providers;

import com.github.nkzawa.socketio.client.IO;

import org.feathersjs.client.interfaces.IProviderConfiguration;

public class FeathersSocketIO extends IProviderConfiguration {

    public static class Options extends IO.Options {

    }

    private final Options mOptions;

    public FeathersSocketIO(Options options) {
       mOptions = options;
    }

    public FeathersSocketIO() {
        mOptions = new Options();
    }
}
