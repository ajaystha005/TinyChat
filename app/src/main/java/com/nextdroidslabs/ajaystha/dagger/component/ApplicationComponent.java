package com.nextdroidslabs.ajaystha.dagger.component;

import android.content.Context;

import com.nextdroidslabs.ajaystha.dagger.ApplicationModule;
import com.nextdroidslabs.ajaystha.dagger.scope.ApplicationScope;
import com.nextdroidslabs.ajaystha.data.DataModule;
import com.nextdroidslabs.ajaystha.socket.TinySocketModule;
import com.nextdroidslabs.ajaystha.tinychat.TinyChatModule;

import dagger.Component;

/**
 * Created by ajayshrestha on 11/27/17.
 */
@ApplicationScope
@Component(modules = {ApplicationModule.class, DataModule.class, TinySocketModule.class})
public interface ApplicationComponent {
    Context getContext();

    TinyChatComponent plus(TinyChatModule tinyChatModule);

}
