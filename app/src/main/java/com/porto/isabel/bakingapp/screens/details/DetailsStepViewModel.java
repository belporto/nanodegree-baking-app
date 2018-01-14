package com.porto.isabel.bakingapp.screens.details;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.porto.isabel.bakingapp.model.baking.Recipe;
import com.porto.isabel.bakingapp.model.baking.Step;

public class DetailsStepViewModel extends ViewModel {

    private final MutableLiveData<Recipe> mRecipeLiveData = new MutableLiveData<>();
    private final MutableLiveData<Step> mStepLiveData = new MutableLiveData<>();


    public void setRecipe(Recipe recipe) {
        mRecipeLiveData.setValue(recipe);
    }

    public LiveData<Recipe> getRecipe() {
        return mRecipeLiveData;
    }

    public void selectStep(Step step) {
        mStepLiveData.setValue(step);
    }

    public LiveData<Step> getStep() {
        return mStepLiveData;
    }

}