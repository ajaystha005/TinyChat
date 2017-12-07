package com.nextdroidslabs.ajaystha.socket;

import android.os.AsyncTask;

import com.nextdroidslabs.ajaystha.constant.SocketConstant;
import com.nextdroidslabs.ajaystha.pojo.Message;
import com.nextdroidslabs.ajaystha.utils.JSONUtils;
import com.nextdroidslabs.ajaystha.utils.SocketUtils;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by ajayshrestha on 11/27/17.
 */

/**
 * Implementation of TCP Socket Client which is responsible to send , receive and update user message. This class can be more decoupled
 */
public class TinySocketImpl implements TinySocketContract {

    private Socket mSocket;
    private TinySocketContract.MessageListener mMessageListener;

    private BufferedReader mInputReader;
    private PrintWriter mOutputWriter;
    private boolean mWaiting = true;
    private ScheduledExecutorService mSchedulerService;

    private boolean mShouldNotify = false;

    public TinySocketImpl() {
        mSchedulerService = Executors.newScheduledThreadPool(5);
    }

    @Override
    public void init(TinySocketContract.MessageListener messageListener) {
        this.mMessageListener = messageListener;

        listenForSocketConnection();
    }

    @Override
    public void connect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    connectToServer();
                    listenForServerResponse();
                } catch (Exception e) {
                    e.printStackTrace();
                    if (mShouldNotify && mMessageListener != null) {
                        mMessageListener.onError();
                    }
                }
            }
        }).start();
    }

    /**
     * Try to connect to server
     *
     * @throws IOException
     */
    private void connectToServer() throws IOException {
        close();

        mSocket = new Socket();
        mSocket.connect(SocketUtils.getSocketAddress());
        if (mMessageListener != null) {
            mMessageListener.onConnected();

            mShouldNotify = true;
        }
    }

    /**
     * wait for server response
     *
     * @throws Exception
     */
    private void listenForServerResponse() throws Exception {
        mOutputWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream())));
        mInputReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
        while (mWaiting) {
            try {
                String receivedMessage = mInputReader.readLine();
                if (receivedMessage != null && mMessageListener != null) {
                    Message message = JSONUtils.parseReceivedMessage(receivedMessage);
                    if (message != null) {
                        mMessageListener.onMessageReceived(message);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * Send json message to server
     *
     * @param originalMessage
     * @param json
     * @throws IOException
     */
    private void sendMessageToServer(Message originalMessage, String json) throws IOException {
        if (mOutputWriter != null) {
            mOutputWriter.println(json);
            mOutputWriter.flush();

            //Sometimes #checkError only returns after print the message. so print and check
            if (mOutputWriter.checkError()) {
                if (originalMessage != null && mMessageListener != null) {
                    mMessageListener.onSendingFailed(originalMessage);
                }
            } else {
                if (mMessageListener != null) {
                    mMessageListener.updateLastSyncedTime();
                }
            }
        }

    }

    /**
     * Disconnect on Destroy Activity
     */
    @Override
    public void disconnect() {
        if (mSocket != null && mSocket.isConnected()) {
            mWaiting = false;
            close();
        }
    }

    /**
     * Send message
     *
     * @param message
     */
    @Override
    public void sendMessage(final Message message) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String jsonMessage = JSONUtils.getJSONFromInputMessage(message.getText());
                    sendMessageToServer(message, jsonMessage);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * Sync history
     *
     * @param lastSyncTime
     */
    @Override
    public void syncMessage(final long lastSyncTime) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String jsonMessage = JSONUtils.getHistoryJSON(lastSyncTime);
                    sendMessageToServer(null, jsonMessage);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean isConnected() {
        return mSocket != null && mSocket.isConnected();
    }

    /**
     * Check the connection every 10 second and notify the user
     */
    private void listenForSocketConnection() {
        mSchedulerService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //This means there is no connection to server
                //TODO: mSocket.isConnected doesn't work as expected
                if (mOutputWriter == null || !canWriteToServer()) {
                    connect();
                    if (mShouldNotify) {
                        if (mMessageListener != null) {
                            mMessageListener.onDisconnected();
                        }
                        mShouldNotify = false;
                    }
                }
            }
        }, 100, SocketConstant.HEART_BEAT_INTERVAL, TimeUnit.MILLISECONDS);

    }

    /**
     * Check If can write to server to test connection
     *
     * @return
     */
    private boolean canWriteToServer() {
        //ping a server TODO: Most possible a worst way to check
        mOutputWriter.println(1);
        return !mOutputWriter.checkError();
    }


    //Close the Socket Instance
    private void close() {
        if (mSocket != null) {
            try {
                mSocket.close();
                mOutputWriter.close();
                mInputReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
