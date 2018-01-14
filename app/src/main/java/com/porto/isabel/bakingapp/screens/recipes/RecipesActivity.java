package com.porto.isabel.bakingapp.screens.recipes;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.porto.isabel.bakingapp.BakingAppApplication;
import com.porto.isabel.bakingapp.R;
import com.porto.isabel.bakingapp.di.AppComponent;
import com.porto.isabel.bakingapp.model.baking.Recipe;
import com.porto.isabel.bakingapp.screens.recipes.adapter.RecipesAdapter;
import com.porto.isabel.bakingapp.screens.recipes.di.DaggerRecipesComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class RecipesActivity extends AppCompatActivity implements RecipesAdapter.RecipesAdapterOnClickHandler {

    @BindView(R.id.recipes_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.recipes_error)
    View mErrorView;
    @BindView(R.id.recipes_empty)
    View mEmptyView;
    @BindView(R.id.recipes_loading)
    ProgressBar mProgressBar;
    @BindView(R.id.recipes_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recipes_toolbar)
    Toolbar mToolbar;

    @Inject
    RecipesViewModelFactory mRecipesViewModelFactory;

    private RecipesAdapter mRecipesAdapter;
    private RecipesViewModel mRecipesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ButterKnife.bind(this);

        AppComponent appComponent = ((BakingAppApplication) getApplication()).getAppComponent();
        DaggerRecipesComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);

        setSupportActionBar(mToolbar);

        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecipesAdapter = new RecipesAdapter(this);
        mRecyclerView.setAdapter(mRecipesAdapter);

        mRecipesViewModel = ViewModelProviders.of(this, mRecipesViewModelFactory).get(RecipesViewModel.class);
        mRecipesViewModel.response().observe(this, this::processResponse);
        mRecipesViewModel.loadRecipes();
    }

    private void processResponse(RecipesResponse response) {
        switch (response.status) {
            case LOADING:
                renderLoadingState();
                break;

            case SUCCESS:
                renderDataState(response.data);
                break;

            case ERROR:
                renderErrorState(response.error);
                break;
        }
    }

    private void renderLoadingState() {
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
    }

    private void renderDataState(List<Recipe> recipes) {
        mRecipesAdapter.setData(recipes);
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
    }

    private void renderErrorState(Throwable throwable) {
        Timber.e(throwable);
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        if (getResources().getBoolean(R.bool.isTablet)) {
            return new GridLayoutManager(this, 4);
        } else {
            return new LinearLayoutManager(this);
        }
    }

    @Override
    public void onClick(Recipe recipe) {
        Timber.d("Recipe clicked " + recipe.name);
    }

}
