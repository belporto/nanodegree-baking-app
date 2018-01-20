package com.porto.isabel.bakingapp.screens.details;


import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.porto.isabel.bakingapp.R;
import com.porto.isabel.bakingapp.model.baking.Step;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepFragment extends Fragment {

    DetailsStepViewModel mDetailsViewModel;

    private SimpleExoPlayer mExoPlayer;

    @BindView(R.id.playerView)
    SimpleExoPlayerView mPlayerView;
    @BindView(R.id.description)
    TextView descriptionView;
    @BindView(R.id.button_nextStep)
    Button buttonNext;
    @BindView(R.id.button_previousStep)
    Button buttonPrevious;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_step, container, false);

        ButterKnife.bind(this, rootView);

        mDetailsViewModel = ViewModelProviders.of(getActivity()).get(DetailsStepViewModel.class);
        mDetailsViewModel.getStep().observe(this, this::showStepDetails);
        mDetailsViewModel.getButtonState().observe(this, this::showButtons);

        return rootView;
    }

    private void showButtons(ButtonState buttonState) {
        buttonPrevious.setVisibility(buttonState.showPrevious ? View.VISIBLE : View.GONE);
        buttonNext.setVisibility(buttonState.showNext ? View.VISIBLE : View.GONE);
    }

    private void showStepDetails(Step step) {
        if (step.videoURL != null && !step.videoURL.isEmpty()) {
            initializePlayer(Uri.parse(step.videoURL));
            mPlayerView.setVisibility(View.VISIBLE);
        } else {
            mPlayerView.setVisibility(View.GONE);
        }

        descriptionView.setText(step.description);
    }

    @OnClick(R.id.button_nextStep)
    public void nextStep() {
        mDetailsViewModel.showNextStep();
    }

    @OnClick(R.id.button_previousStep)
    public void previousStep() {
        mDetailsViewModel.showPreviousStep();
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
        }

        // Prepare the MediaSource.
        String userAgent = Util.getUserAgent(getContext(), "BakingApp");
        MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
        mExoPlayer.prepare(mediaSource);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }
}
