package org.feathersjs.client.plugins.hooks;

public interface HookDoneCallback <J> {
    <J> void onDone(HookObject<J> hookObject);
}