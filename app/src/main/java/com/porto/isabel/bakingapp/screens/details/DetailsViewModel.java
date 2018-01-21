package com.porto.isabel.bakingapp.screens.details;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.porto.isabel.bakingapp.model.baking.Recipe;
import com.porto.isabel.bakingapp.model.baking.Step;

import java.util.ArrayList;
import java.util.List;

public class DetailsViewModel extends ViewModel {

    private final MutableLiveData<Recipe> mRecipeLiveData = new MutableLiveData<>();
    private final MutableLiveData<Step> mStepLiveData = new MutableLiveData<>();
    private final MutableLiveData<ButtonState> mButtonStatLiveData = new MutableLiveData<>();
    private List<Step> mSteps = new ArrayList<>();
    private int mPosition;
    private boolean showButtons = true;

    public void setRecipe(Recipe recipe) {
        mRecipeLiveData.setValue(recipe);
        mSteps = recipe.steps;
    }

    public LiveData<Recipe> getRecipe() {
        return mRecipeLiveData;
    }

    public void selectStep(int stepPosition) {
        mPosition = stepPosition;
        mStepLiveData.setValue(mSteps.get(stepPosition));

        boolean showPrevious = showButtons && mPosition > 0;
        boolean showNext = showButtons && mPosition < mSteps.size() - 1;

        mButtonStatLiveData.setValue(new ButtonState(showPrevious, showNext));

    }

    public void setShowButtons(boolean showButtons) {
        this.showButtons = showButtons;
    }

    public LiveData<Step> getStep() {
        return mStepLiveData;
    }

    public void showNextStep() {
        if (mPosition < mSteps.size() - 1) {
            selectStep(mPosition + 1);
        }
    }

    public void showPreviousStep() {
        if (mPosition > 0) {
            selectStep(mPosition - 1);
        }
    }

    public LiveData<ButtonState> getButtonState() {
        return mButtonStatLiveData;
    }
}