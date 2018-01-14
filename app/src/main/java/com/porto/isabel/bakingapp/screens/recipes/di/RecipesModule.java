package com.porto.isabel.bakingapp.screens.recipes.di;

import com.porto.isabel.bakingapp.network.BakingApi;
import com.porto.isabel.bakingapp.screens.recipes.RecipesViewModelFactory;

import dagger.Module;
import dagger.Provides;


@RecipesScope
@Module
public class RecipesModule {

    @Provides
    @RecipesScope
    public RecipesViewModelFactory provideFactory(BakingApi bakingApi) {
        return new RecipesViewModelFactory(bakingApi);
    }
}
