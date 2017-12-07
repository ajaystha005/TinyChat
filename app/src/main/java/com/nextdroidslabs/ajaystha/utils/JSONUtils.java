package com.nextdroidslabs.ajaystha.utils;

import com.nextdroidslabs.ajaystha.pojo.Message;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ajayshrestha on 11/27/17.
 */

public class JSONUtils {

    public static Message parseReceivedMessage(String jsonString) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonString);
        String msg = jsonObject.getString("msg");
        String clientTime = jsonObject.getString("client_time");
        String serverTime = jsonObject.getString("server_time");

        Message message = new Message();
        message.setMessage(msg);
        message.setClientTimeStamp(clientTime);
        message.setServerTimeStamp(serverTime);

        return message;
    }

    public static String getJSONFromInputMessage(String message) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", message);
        jsonObject.put("client_time", System.currentTimeMillis());

        return jsonObject.toString();
    }

    public static String getHistoryJSON(long lastSyncedTime) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command", "history");
        jsonObject.put("client_time", System.currentTimeMillis());
        jsonObject.put("since", lastSyncedTime);

        return jsonObject.toString();
    }
}
