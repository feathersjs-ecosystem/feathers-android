package org.feathersjs.feathersdemo;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.feathersjs.client.service.FeathersService;
import org.feathersjs.feathersdemo.models.Message;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class MessagesAdapter extends FeathersServiceAdapter<Message, MessagesAdapter.MessageViewHolder> {

    public MessagesAdapter(Activity activity, FeathersService<Message> service, int resource) {
        super(activity, service, resource);
    }

    public static class MessageViewHolder extends FeathersViewHolder {
        protected TextView vUsername;
        protected TextView vText;
        protected SimpleDraweeView vImage;

        public MessageViewHolder(View v) {
            super(v);
            vUsername = (TextView) v.findViewById(R.id.username);
            vText = (TextView) v.findViewById(R.id.message_text);
            vImage = (SimpleDraweeView) v.findViewById(R.id.my_image_view);
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
        if(message.sentBy != null) {
            holder.vImage.setImageURI(Uri.parse(message.sentBy.avatar));
            holder.vUsername.setText(message.sentBy.email);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Message updatedMessage = message;
                JSONObject obj = new JSONObject();
                try {
                    obj.put("text", "newTExt" + new Date().getTime());
                    updatedMessage.text = obj.optString("text");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mService.update(updatedMessage._id, updatedMessage, new FeathersService.FeathersCallback<Message>() {
                    @Override
                    public void onError(String errorMessage) {

                    }

                    @Override
                    public void onSuccess(Message t) {

                    }
                });
            }
        });
    }
}
