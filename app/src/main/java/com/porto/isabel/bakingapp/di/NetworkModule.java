package com.porto.isabel.bakingapp.di;

import com.porto.isabel.bakingapp.network.BakingApi;

import dagger.Module;
import dagger.Provides;


@Module
public class NetworkModule {

    @Provides
    public BakingApi provideBakingApi() {
        return new BakingApi();
    }
}
