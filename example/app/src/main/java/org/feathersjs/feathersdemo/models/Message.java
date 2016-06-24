package org.feathersjs.feathersdemo.models;

import java.util.Date;

public class Message {
    public String _id;
    public String text;
    public String createdAt;
    public User sentBy;

    @Override
    public boolean equals(Object o) {
        if (o != null) {
            if (Message.class.isInstance(o)) {
                Message messageToCompare = (Message) o;
                if (messageToCompare._id != null && _id != null)
                    return messageToCompare._id.equalsIgnoreCase(_id);
            }
        }
        return super.equals(o);
    }
}
