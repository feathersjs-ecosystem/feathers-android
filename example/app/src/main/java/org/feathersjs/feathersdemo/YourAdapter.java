package org.feathersjs.feathersdemo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.feathersjs.client.service.FeathersService;
import org.feathersjs.feathersdemo.models.Message;

public class YourAdapter<T> extends FeathersServiceAdapter<T, YourAdapter.MessageViewHolder> {

    public YourAdapter(Activity activity, FeathersService<T> service, int resource) {
        super(activity, service, resource);
    }

    public static class MessageViewHolder extends FeathersViewHolder {
        protected TextView vText;

        public MessageViewHolder(View v) {
            super(v);
            vText = (TextView) v.findViewById(R.id.message_text);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_message, parent, false);

        return new MessageViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {

        final Message message = (Message) getDataSet().get(position);
        holder.vText.setText(message.text);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mService.remove(message._id, new FeathersService.FeathersCallback<T>() {
                    @Override
                    public void onError(String errorMessage) {

                    }

                    @Override
                    public void onSuccess(T t) {

                    }
                });
            }
        });
    }
}
