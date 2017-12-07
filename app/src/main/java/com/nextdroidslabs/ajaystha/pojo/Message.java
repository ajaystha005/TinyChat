package com.nextdroidslabs.ajaystha.pojo;

/**
 * Created by ajayshrestha on 11/27/17.
 */

public class Message {

    private String mText;
    private String mClientTimeStamp;
    private String mServerTimeStamp;

    public Message() {

    }

    public Message(String text) {
        this.mText = text;
    }

    public String getText() {
        return mText;
    }

    public void setMessage(String message) {
        this.mText = message;
    }

    public String getClientTimeStamp() {
        return mClientTimeStamp;
    }

    public void setClientTimeStamp(String clientTimeStamp) {
        this.mClientTimeStamp = clientTimeStamp;
    }

    public String getServerTimeStamp() {
        return mServerTimeStamp;
    }

    public void setServerTimeStamp(String serverTimeStamp) {
        this.mServerTimeStamp = serverTimeStamp;
    }
}
