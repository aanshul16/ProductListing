package com.productlisting.root;

import android.app.Application;

import com.productlisting.http.Communicator;


public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .communicator(new Communicator())
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
