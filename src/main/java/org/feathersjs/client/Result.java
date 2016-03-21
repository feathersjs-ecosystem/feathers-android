package org.feathersjs.client;

import java.util.List;

public class Result<T> {
    public int total;
    public int limit;
    public int skip;
    public List<T> data;
}
