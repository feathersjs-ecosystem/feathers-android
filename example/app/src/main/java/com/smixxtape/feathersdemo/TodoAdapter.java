package com.smixxtape.feathersdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.smixxtape.feathersdemo.models.Todo;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private ArrayList<Todo> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        protected TextView vText;
        protected CheckBox vCheckbox;


        public TodoViewHolder(View v) {
            super(v);
            vText =  (TextView) v.findViewById(R.id.todo_text);
            vCheckbox =  (CheckBox) v.findViewById(R.id.todo_checkbox);
//            vSurname = (TextView)  v.findViewById(R.id.txtSurname);
//            vEmail = (TextView)  v.findViewById(R.id.txtEmail);
//            vTitle = (TextView) v.findViewById(R.id.title);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TodoAdapter(ArrayList<Todo> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TodoAdapter.TodoViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_todo, parent, false);

        return new TodoViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        Todo ci = mDataset.get(position);
        holder.vText.setText(ci.text);
        holder.vCheckbox.setChecked(ci.complete);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
