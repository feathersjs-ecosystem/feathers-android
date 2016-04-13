package org.feathersjs.feathersdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import org.feathersjs.feathersdemo.models.Message;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {
    private ArrayList<Message> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        protected TextView vText;
        protected CheckBox vCheckbox;


        public MessageViewHolder(View v) {
            super(v);
            vText =  (TextView) v.findViewById(R.id.todo_text);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MessagesAdapter(ArrayList<Message> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MessagesAdapter.MessageViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_message, parent, false);

        return new MessageViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message message = mDataset.get(position);
        holder.vText.setText(message.text);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
