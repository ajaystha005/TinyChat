package com.nextdroidslabs.ajaystha.socket;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ajayshrestha on 11/27/17.
 */
@Module
public class TinySocketModule {

    @Provides
    public TinySocketContract provideSocketContractor() {
        return new TinySocketImpl();
    }

//    @Provides
//    public Socket provideSocket() {
//        return SocketUtils.createSocket();
//    }
}
