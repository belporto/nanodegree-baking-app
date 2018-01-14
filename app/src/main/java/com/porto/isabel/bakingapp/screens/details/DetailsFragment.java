package com.porto.isabel.bakingapp.screens.details;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.porto.isabel.bakingapp.R;
import com.porto.isabel.bakingapp.model.baking.Recipe;

import timber.log.Timber;

public class DetailsFragment extends Fragment {


    DetailsViewModel mDetailsViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_details, container, false);


        mDetailsViewModel = ViewModelProviders.of(getActivity()).get(DetailsViewModel.class);
        mDetailsViewModel.getRecipe().observe(this, this::showRecipeDetails);

        return rootView;
    }

    private void showRecipeDetails(Recipe recipe) {
        if (recipe != null) {
            Timber.d(recipe.name);
        } else {
            Timber.d("Recipe is null");
        }

    }
}
