package com.nextdroidslabs.ajaystha.utils;

import com.nextdroidslabs.ajaystha.constant.SocketConstant;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by ajayshrestha on 11/27/17.
 */

public class SocketUtils {

    public static Socket createSocket() {
        Socket socket = null;
//        IO.Options options = new IO.Options();
//        options.port = 1234;
        try {
            socket = new Socket("52.91.109.76", 1234);
            //socket = IO.socket("ec2-52-91-109-76.compute-1.amazonaws.com:1234");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }

    public static InetSocketAddress getSocketAddress() {
        InetAddress addr = null;
        try {
            addr = InetAddress.getByName(SocketConstant.HOST_ADDRESS);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        int port = SocketConstant.PORT;
        return new InetSocketAddress(addr, port);
    }
}
