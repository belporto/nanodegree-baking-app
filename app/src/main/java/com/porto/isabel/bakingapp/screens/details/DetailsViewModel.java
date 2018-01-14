package com.porto.isabel.bakingapp.screens.details;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.porto.isabel.bakingapp.model.baking.Recipe;

public class DetailsViewModel extends ViewModel {

    private final MutableLiveData<Recipe> mRecipeLiveData = new MutableLiveData<Recipe>();

    public void setRecipe(Recipe recipe) {
        mRecipeLiveData.setValue(recipe);
    }

    public LiveData<Recipe> getRecipe() {
        return mRecipeLiveData;
    }
}