package com.porto.isabel.bakingapp.screens.step;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.porto.isabel.bakingapp.R;
import com.porto.isabel.bakingapp.model.baking.Step;
import com.porto.isabel.bakingapp.screens.details.DetailsStepViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepActivity extends AppCompatActivity {

    private static final String STEP_EXTRA = "STEP_EXTRA";
    @BindView(R.id.step_toolbar)
    Toolbar mToolbar;

    DetailsStepViewModel mStepViewModel;

    public static void startActivity(Activity activity, Step step) {
        Intent intent = new Intent(activity, StepActivity.class);
        intent.putExtra(STEP_EXTRA, step);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Step step = getIntent().getParcelableExtra(STEP_EXTRA);

        mStepViewModel = ViewModelProviders.of(this).get(DetailsStepViewModel.class);
        mStepViewModel.selectStep(step);

        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
    }
}
