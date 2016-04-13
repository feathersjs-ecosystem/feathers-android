package org.feathersjs.feathersdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import org.feathersjs.client.Feathers;
import org.feathersjs.client.service.FeathersService;

import org.feathersjs.client.service.OnCreatedCallback;
import org.feathersjs.feathersdemo.models.Message;

import java.util.ArrayList;
//import java.util.Iterator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends Activity {

    @Bind(R.id.my_recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.textbox)
    EditText mEditText;

    private RecyclerView.Adapter mAdapter;
    FeathersService<Message> messageService;
    ArrayList<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        messages = new ArrayList<>();

        initializeAdapter();

        messageService = Feathers.getInstance().service("messages");
        messageService.onCreated(new OnCreatedCallback<Message>() {
            @Override
            public void onCreated(final Message message) {
                Log.d("onCreated", message._id + "|" + message.text);

                runOnUiThread(new Runnable() {
                    public void run() {
                        messages.add(message);
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }


        });
        //setupEvents();

        fetchMessages();
    }

    /*
    private void setupEvents() {
        todoService.onCreated(new FeathersService.FeathersEventCallback<Todo>() {
            @Override
            public void onSuccess(final Todo todoNew) {
                Log.d("onCreated", todoNew.id + "|" + todoNew.text);

                runOnUiThread(new Runnable() {
                    public void run() {
                        getTodo(todoNew.id + "");
                        todos.add(todoNew);
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        todoService.onRemoved(new FeathersService.FeathersEventCallback<Todo>() {
            @Override
            public void onSuccess(final Todo todoNew) {
                Log.d("onRemoved", todoNew.id + "|" + todoNew.text);
                runOnUiThread(new Runnable() {
                    public void run() {
                        for (Todo todo : todos) {
                            if (todoNew.id == todo.id) {
                                todos.remove(todo);
                                mAdapter.notifyDataSetChanged();
                            }
                        }

                    }
                });
            }
        });

        todoService.onUpdated(new FeathersService.FeathersEventCallback<Todo>() {
            @Override
            public void onSuccess(final Todo todoNew) {
                Log.d("onUpdated", todoNew.id + "|" + todoNew.text);
                runOnUiThread(new Runnable() {
                    public void run() {
                        for (Todo todo : todos) {
                            if (todoNew.id == todo.id) {
                                todo.text = todoNew.text;
                                todo.complete = todoNew.complete;
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
            }
        });

        todoService.onPatched(new FeathersService.FeathersEventCallback<Todo>() {
            @Override
            public void onSuccess(final Todo todoNew) {
                Log.d("onPatched", todoNew.id + "|" + todoNew.text);
                runOnUiThread(new Runnable() {
                    public void run() {
                        for (Todo todo : todos) {
                            if (todoNew.id == todo.id) {
                                todo.text = todoNew.text;
                                todo.complete = todoNew.complete;
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
            }
        });
    }


    private void fetchTodos() {
//        todoService.find(new FeathersService.FeathersCallback<List<Todo>>() {
//            @Override
//            public void onSuccess(List<Todo> list) {
//                Log.d("onSuccess", list.size() + "");
//                todos.addAll(list);
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        mAdapter.notifyDataSetChanged();
//                    }
//                });
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//                Log.d("onError", errorMessage);
//            }
//        });
    }


    private void getTodo(final String id) {
        todoService.get(id, new FeathersService.FeathersCallback<Todo>() {
            @Override
            public void onSuccess(Todo todo) {
                Log.d("getTodo:onSuccess", todo.toString());
                Log.d("getTodo:onSuccess", todo.id + "");
                Log.d("getTodo:onSuccess", todo.text + "");
//                deleteTodo(id);
            }

            @Override
            public void onError(String errorMessage) {
                Log.d("getTodo:onError", errorMessage);
            }
        });
    }

    private void deleteTodo(String id) {
        todoService.remove(id, new FeathersService.FeathersCallback<Todo>() {
            @Override
            public void onSuccess(Todo todo) {
                Log.d("getTodo:onSuccess", todo.toString());
                Log.d("getTodo:onSuccess", todo.id + "");
                Log.d("getTodo:onSuccess", todo.text + "");
            }

            @Override
            public void onError(String errorMessage) {
                Log.d("deleteTodo:onError", errorMessage);
            }
        });
    }
*/

    private void fetchMessages() {
//        messageService.find(new FeathersService.FeathersCallback<Result<Message>>() {
//
//            @Override
//            public void onError(String errorMessage) {
//
//            }
//
////            @Override
////            public void onSuccess(Message message) {
////
////            }
//
//
////            @Override
////            public void onSuccess(Result<Message> messageResult) {
////
////            }
////
////            @Override
////            public void onSuccess(Result<Result<Message>> t) {
////
////            }
//        });
//            @Override
//            public void onSuccess(List<Todo> list) {
//                Log.d("onSuccess", list.size() + "");
//                todos.addAll(list);
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        mAdapter.notifyDataSetChanged();
//                    }
//                });
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//                Log.d("onError", errorMessage);
//            }
//        });
    }

    private void initializeAdapter() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MessagesAdapter(messages);
        mRecyclerView.setAdapter(mAdapter);
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

    @OnClick(R.id.loginButton)
    void login() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.signupButton)
    void signup() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.sendButton)
    void send() {
        Message message = new Message();
        message.text = mEditText.getText().toString();
//        Feathers.getInstance().service("messages").create(message, new FeathersService.FeathersCallback<Message>() {
//            @Override
//            public void onSuccess(Message t) {
//
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//
//            }
//        });
    }
}
