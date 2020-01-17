package com.sbl.foags.exoplayer;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.sbl.foags.R;

import java.util.Formatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class ExoPlayerMediaControllerLayout extends RelativeLayout implements View.OnClickListener, OnSeekBarChangeListener {


    private final String TAG = "sbl ExoPlayerMediaControllerLayout";

    private ExoPlayerMediaControllerListener controllerListener;
    private ViewGroup mAnchor;
    private View mRoot;

    private ImageView switchStatusView;
    private TextView currentTimeView;
    private TextView totalTimeView;
    private SeekBar seekBarView;
    private ImageView switchScreenView;

    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;
    private boolean isShowing = false;

    private Disposable hindDisposable;
    private Disposable timerDisposable;


    public ExoPlayerMediaControllerLayout(Context context, ExoPlayerMediaControllerListener listener) {
        super(context);
        controllerListener = listener;
        mRoot = null;
    }


    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        if (mRoot != null) {
            initControllerView(mRoot);
        }
    }


    private View makeControllerView() {
        mRoot = LayoutInflater.from(getContext()).inflate(R.layout.exo_player_media_controller_layout, this, false);
        initControllerView(mRoot);
        return mRoot;
    }


    private void initControllerView(View v) {
        switchStatusView = v.findViewById(R.id.switchStatusView);
        switchScreenView = v.findViewById(R.id.switchScreenView);
        seekBarView = v.findViewById(R.id.seekBarView);
        currentTimeView = v.findViewById(R.id.currentTimeView);
        totalTimeView = v.findViewById(R.id.totalTimeView);

        switchStatusView.setOnClickListener(this);
        switchScreenView.setOnClickListener(this);

        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        seekBarView.setOnSeekBarChangeListener(this);
    }


    private String stringForTime(long timeMs) {
        int totalSeconds = (int) timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = totalSeconds / 60 % 60;
        int hours = totalSeconds / 3600;
        mFormatBuilder.setLength(0);
        return hours > 0 ?
                mFormatter.format("%02d:%02d:%02d", hours, minutes, seconds).toString() :
                mFormatter.format("%02d:%02d", minutes, seconds).toString();
    }


    public void initSeekBar(){
        Log.e(TAG, "initSeekBar()");
        if (controllerListener != null) {
            long position = controllerListener.getCurrentPosition();
            long duration = controllerListener.getDuration();

            seekBarView.setMax((int)duration);
            currentTimeView.setText(stringForTime(position));
            totalTimeView.setText(stringForTime(duration));

            if(timerDisposable != null){
                timerDisposable.dispose();
                timerDisposable = null;
            }
            timerDisposable = Observable.interval(100, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> {
                        if(controllerListener.isPlaying()){

                            long nowPosition = controllerListener.getCurrentPosition();
                            seekBarView.setProgress((int)nowPosition);
                            currentTimeView.setText(stringForTime(nowPosition));
                        }
                    });
        }
    }


    private void updateStatusView() {
        if (mRoot != null && switchStatusView != null && controllerListener != null) {
            if (controllerListener.isPlaying()) {
                switchStatusView.setImageResource(R.drawable.ic_stop);
                switchStatusView.setTag("1");
            } else {
                switchStatusView.setImageResource(R.drawable.ic_play);
                switchStatusView.setTag("0");
            }
        }

        updateScreenView();
    }


    private void updateScreenView() {
        if (mRoot != null && switchScreenView != null && controllerListener != null) {
            if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                switchScreenView.setImageResource(R.drawable.ic_quanping);
            } else {
                switchScreenView.setImageResource(R.drawable.ic_suoxiao);
            }
        }
    }


    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateScreenView();
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.switchStatusView) {
            doPauseResume();
        } else if (i == R.id.switchScreenView) {
            if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                doFullScreen();
            } else {
                doSmallScreen();
            }
        }
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            controllerListener.seekTo(progress);
            currentTimeView.setText(stringForTime(progress));
        }
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        updateStatusView();
        show(ExoPlayerLayout.HIDE_TIME);
    }


    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }


    private void doPauseResume() {
        if (controllerListener != null) {
            if(controllerListener.isFinish()){
                controllerListener.restart();
            }else if (controllerListener.isPlaying()) {
                controllerListener.pause();
            } else {
                controllerListener.start();
            }
        }
        updateStatusView();
        show(ExoPlayerLayout.HIDE_TIME);
    }


    private void doFullScreen() {
        if (controllerListener != null) {
            controllerListener.doFullScreen();
            show(ExoPlayerLayout.HIDE_TIME);
        }
    }


    private void doSmallScreen() {
        if (controllerListener != null) {
            controllerListener.doSmallScreen();
            show(ExoPlayerLayout.HIDE_TIME);
        }
    }


    private void showControllerAnimation() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "alpha", 0f, 1f);
        objectAnimator.setDuration(400);
        objectAnimator.start();
    }


    private void hideControllerAnimation(Animator.AnimatorListener animationListener) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f);
        objectAnimator.setDuration(400);
        objectAnimator.addListener(animationListener);
        objectAnimator.start();
    }


    public void setAnchorView(ViewGroup view) {
        mAnchor = view;
        LayoutParams frameParams = new LayoutParams(-1, -1);
        removeAllViews();
        View v = makeControllerView();
        addView(v, frameParams);
    }


    public void show(int timeout) {
        Log.e(TAG, "show()");
        if (mAnchor != null && !isShowing) {
            mAnchor.addView(ExoPlayerMediaControllerLayout.this, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            isShowing = true;
            try {
                showControllerAnimation();
            } catch (Exception ignored) {
            }
        }
        updateStatusView();


        if(hindDisposable != null){
            hindDisposable.dispose();
            hindDisposable = null;
        }
        hindDisposable = Observable.timer(timeout, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    hide();
                });
    }


    public void hide() {
        if (!controllerListener.isPlaying()) {
            return;
        }
        Log.e(TAG, "hide()");
        try {
            hideControllerAnimation(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    removeView();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    removeView();
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        } catch (Exception ignored) {
            removeView();
        }
    }


    public void removeView(){
        if (mAnchor != null) {
            mAnchor.removeView(ExoPlayerMediaControllerLayout.this);
        }
        isShowing = false;
    }


    public void releaseController() {
        if(hindDisposable != null){
            hindDisposable.dispose();
            hindDisposable = null;
        }
        if(timerDisposable != null){
            timerDisposable.dispose();
            timerDisposable = null;
        }
        if (mAnchor != null) {
            mAnchor.removeView(ExoPlayerMediaControllerLayout.this);
        }
        isShowing = false;
    }


    public void finishController() {
        releaseController();
        controllerListener = null;
        mFormatBuilder = null;
        mFormatter = null;
        mAnchor = null;
        System.gc();
    }


    public boolean isShowing() {
        return isShowing;
    }


    public void setShowStateView(boolean show) {
        if (mRoot != null && switchStatusView != null && controllerListener != null) {
            if (show) {
                switchStatusView.setVisibility(View.VISIBLE);
            } else {
                switchStatusView.setVisibility(View.GONE);
            }
        }
    }
}