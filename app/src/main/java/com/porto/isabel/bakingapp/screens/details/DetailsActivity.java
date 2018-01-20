package com.porto.isabel.bakingapp.screens.details;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.porto.isabel.bakingapp.R;
import com.porto.isabel.bakingapp.model.baking.Recipe;
import com.porto.isabel.bakingapp.model.baking.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    private static final String RECIPE_EXTRA = "RECIPE_EXTRA";
    @BindView(R.id.details_toolbar)
    Toolbar mToolbar;

    DetailsStepViewModel mDetailsViewModel;

    public static void startActivity(Activity activity, Recipe recipe) {
        Intent intent = new Intent(activity, DetailsActivity.class);
        intent.putExtra(RECIPE_EXTRA, recipe);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Recipe recipe = getIntent().getParcelableExtra(RECIPE_EXTRA);

        mDetailsViewModel = ViewModelProviders.of(this).get(DetailsStepViewModel.class);
        mDetailsViewModel.setRecipe(recipe);
        mDetailsViewModel.getStep().observe(this, this::showStepDetails);

        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        if (isOneFragmentLayout()) {
            if (savedInstanceState != null) {
                return;
            }

            DetailsFragment firstFragment = new DetailsFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        } else {
            mDetailsViewModel.setShowButtons(false);
            mDetailsViewModel.selectStep(0);
        }
    }

    private void showStepDetails(Step step) {
        if (isOneFragmentLayout()) {
            StepFragment stepFragment = new StepFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, stepFragment);

            transaction.commit();
        }
    }


    private boolean isOneFragmentLayout() {
        return findViewById(R.id.fragment_container) != null;
    }


}
