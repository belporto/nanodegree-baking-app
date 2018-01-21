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

public class StepDetailsFragment extends Fragment {

    DetailsViewModel mDetailsViewModel;

    private SimpleExoPlayer mExoPlayer;

    private SimpleExoPlayerView mPlayerView;
    private TextView mDescriptionView;
    private Button mButtonNext;
    private Button mButtonPrevious;
    private View mDetailsView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);

        mPlayerView = rootView.findViewById(R.id.playerView);
        mDescriptionView = rootView.findViewById(R.id.description);
        mButtonNext = rootView.findViewById(R.id.button_nextStep);
        mButtonPrevious = rootView.findViewById(R.id.button_previousStep);
        mDetailsView = rootView.findViewById(R.id.details_view);

        mDetailsViewModel = ViewModelProviders.of(getActivity()).get(DetailsViewModel.class);

        mDetailsViewModel.getStep().observe(this, this::showStepDetails);
        if (hasButtons()) {
            mDetailsViewModel.getButtonState().observe(this, this::showButtons);
            mButtonNext.setOnClickListener(view -> mDetailsViewModel.showNextStep());
            mButtonPrevious.setOnClickListener(view -> mDetailsViewModel.showPreviousStep());
        }

        return rootView;
    }


    private boolean hasButtons() {
        return mButtonPrevious != null && mButtonNext != null;
    }


    private void showButtons(ButtonState buttonState) {
        mButtonPrevious.setVisibility(buttonState.showPrevious ? View.VISIBLE : View.GONE);
        mButtonNext.setVisibility(buttonState.showNext ? View.VISIBLE : View.GONE);
    }

    private void showStepDetails(Step step) {


        if (step.videoURL != null && !step.videoURL.isEmpty()) {
            initializePlayer(Uri.parse(step.videoURL));
            mPlayerView.setVisibility(View.VISIBLE);

            if (showVideoFullScreen()) {
                changeDetailsViewVisibility(View.GONE);
            }

        } else {
            mPlayerView.setVisibility(View.GONE);
            changeDetailsViewVisibility(View.VISIBLE);
        }

        mDescriptionView.setText(step.description);
    }

    private void changeDetailsViewVisibility(int visibility) {
        if (mDetailsView != null) {
            mDetailsView.setVisibility(visibility);
        }
    }

    private boolean showVideoFullScreen() {
        return getResources().getBoolean(R.bool.showVideoFullScreen);
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
        if (mDetailsViewModel.getVideoCurrentPosition() > 0) {
            mExoPlayer.seekTo(mDetailsViewModel.getVideoCurrentPosition());
            mExoPlayer.setPlayWhenReady(true);
        }
        mExoPlayer.prepare(mediaSource);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mExoPlayer != null) {
            mDetailsViewModel.saveVideoCurrentPosition(mExoPlayer.getCurrentPosition());

        }
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
