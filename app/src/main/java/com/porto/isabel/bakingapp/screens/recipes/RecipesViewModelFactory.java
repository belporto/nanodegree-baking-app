package com.porto.isabel.bakingapp.screens.recipes;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.porto.isabel.bakingapp.network.BakingApi;

class RecipesViewModelFactory implements ViewModelProvider.Factory {

    private final BakingApi mBakingApi;

    RecipesViewModelFactory(BakingApi bakingApi) {
        this.mBakingApi = bakingApi;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RecipesViewModel.class)) {
            return (T) new RecipesViewModel(mBakingApi);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
