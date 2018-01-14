package com.porto.isabel.bakingapp.di;


import android.content.Context;

import com.porto.isabel.bakingapp.network.BakingApi;

import dagger.Component;

@AppScope
@Component(modules = {
        AppModule.class, NetworkModule.class
})
public interface AppComponent {

    Context context();

    BakingApi bakingApi();
}
