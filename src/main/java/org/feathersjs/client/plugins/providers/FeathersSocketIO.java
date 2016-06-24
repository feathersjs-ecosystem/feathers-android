package org.feathersjs.client.plugins.providers;

import com.github.nkzawa.socketio.client.IO;

public class FeathersSocketIO extends IProviderConfiguration {

    public static class Options extends IO.Options {
//        public String baseUrl;
    }

    private final Options mOptions;

    public FeathersSocketIO(Options options) {
       mOptions = options;
    }

    public FeathersSocketIO() {
        mOptions = new Options();
    }
}
