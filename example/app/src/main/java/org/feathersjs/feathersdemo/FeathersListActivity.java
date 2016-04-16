package org.feathersjs.feathersdemo;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.feathersjs.client.service.FeathersService;
import org.feathersjs.feathersdemo.models.Message;

import java.util.ArrayList;

import butterknife.Bind;

public class FeathersListActivity <T> extends Activity {
    @Bind(R.id.my_recycler_view)
    RecyclerView mRecyclerView;

    RecyclerView.Adapter mAdapter;
    FeathersService<T> messageService;
    ArrayList<T> mItems;
}
