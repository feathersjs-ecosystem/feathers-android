package org.feathersjs.client.plugins.providers;

public class FeathersRest extends IProviderConfiguration {

    public static class Options {

    }

    private final Options mOptions;

    public FeathersRest(Options options) {
       mOptions = options;
    }

    public FeathersRest() {
        mOptions = new Options();
    }
}
