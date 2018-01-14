package com.porto.isabel.bakingapp.screens.step;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.porto.isabel.bakingapp.R;
import com.porto.isabel.bakingapp.model.baking.Step;
import com.porto.isabel.bakingapp.screens.details.DetailsStepViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragment extends Fragment {


    DetailsStepViewModel mDetailsViewModel;

    @BindView(R.id.step)
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_step, container, false);

        ButterKnife.bind(this, rootView);


        mDetailsViewModel = ViewModelProviders.of(getActivity()).get(DetailsStepViewModel.class);
        mDetailsViewModel.getStep().observe(this, this::showStepDetails);

        return rootView;
    }

    private void showStepDetails(Step step) {
        textView.setText(step.description);
    }
}
