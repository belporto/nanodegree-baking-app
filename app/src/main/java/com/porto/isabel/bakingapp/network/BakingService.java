package com.porto.isabel.bakingapp.network;

import com.porto.isabel.bakingapp.model.baking.Recipe;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface BakingService {

    //https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json
    @GET("59121517_baking/baking.json")
    Observable<List<Recipe>> getRecipes();
}
