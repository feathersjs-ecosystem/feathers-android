package org.feathersjs.feathersdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.feathersjs.client.Feathers;
import org.feathersjs.client.plugins.authentication.AuthenticationOptions;
import org.feathersjs.client.plugins.authentication.FeathersAuthenticationConfiguration;
import org.feathersjs.client.plugins.providers.FeathersRest;
import org.feathersjs.client.plugins.storage.SharedPreferencesStorageProvider;
import org.feathersjs.client.service.FeathersService;

import org.feathersjs.client.service.OnCreatedCallback;
import org.feathersjs.client.service.OnRemovedCallback;
import org.feathersjs.client.service.OnUpdatedCallback;
import org.feathersjs.client.service.Result;
import org.feathersjs.feathersdemo.models.Message;

import java.util.ArrayList;
//import java.util.Iterator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static org.feathersjs.client.plugins.authentication.FeathersAuthenticationConfiguration.*;

public class MainActivity extends Activity {

    @Bind(R.id.my_recycler_view)
    RecyclerView mRecyclerView;

    MessagesAdapter mAdapter;
    FeathersService<Message> messageService;
    ArrayList<Message> mItems;

    @Bind(R.id.textbox)
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initializeAdapter();
        mAdapter.reload();
    }

    private void initializeAdapter() {
        mItems = new ArrayList<>();
        messageService = Feathers.getInstance().service("messages", Message.class);
        mAdapter = new MessagesAdapter(this, messageService, R.layout.item_message);
        mRecyclerView.setAdapter(mAdapter);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.sendButton)
    void createMessage() {

        Message message = new Message();
        message.text = mEditText.getText().toString();

        messageService.create(message, new FeathersService.FeathersCallback<Message>() {
            @Override
            public void onSuccess(Message t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mEditText.setText("");
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    @OnClick(R.id.logoutButton)
    void logout() {
        Feathers.getInstance().logout();
        Intent intent = new Intent(MainActivity.this, LaunchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
