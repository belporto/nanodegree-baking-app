package com.porto.isabel.bakingapp.network;

import com.porto.isabel.bakingapp.model.baking.Recipe;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BakingApi {

    private BakingService mService;

    public BakingApi() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(BakingService.class);
    }

    public Observable<List<Recipe>> getRecipes() {
        return mService.getRecipes();
    }
}
