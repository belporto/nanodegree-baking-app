package com.porto.isabel.bakingapp.screens.details;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.porto.isabel.bakingapp.R;
import com.porto.isabel.bakingapp.model.baking.Recipe;
import com.porto.isabel.bakingapp.screens.details.adapter.DetailsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsFragment extends Fragment implements DetailsAdapter.DetailsAdapterOnClickHandler {


    DetailsViewModel mDetailsViewModel;
    @BindView(R.id.details_recycler_view)
    RecyclerView mRecyclerView;
    private DetailsAdapter mAdapter;
    private OnStepSelectedListener mStepSelectedListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        ButterKnife.bind(this, rootView);

        mDetailsViewModel = ViewModelProviders.of(getActivity()).get(DetailsViewModel.class);
        mDetailsViewModel.getRecipe().observe(this, this::showRecipeDetails);

        mAdapter = new DetailsAdapter(this, mDetailsViewModel.getStepPosition());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);


        return rootView;
    }


    private void showRecipeDetails(Recipe recipe) {
        mAdapter.setData(recipe);
    }

    @Override
    public void onClick(int stepPosition) {
        mDetailsViewModel.selectStep(stepPosition);
        mStepSelectedListener.onStepSelected(stepPosition);
    }

    public interface OnStepSelectedListener {
        void onStepSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mStepSelectedListener = (OnStepSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepSelectedListener");
        }
    }


}
