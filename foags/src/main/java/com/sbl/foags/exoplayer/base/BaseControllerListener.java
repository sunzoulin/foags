package com.sbl.foags.exoplayer.base;


public interface BaseControllerListener {

    void start();

    void pause();

    void restart();

    long getDuration();

    long getCurrentPosition();

    void seekTo(long var1);

    boolean isPlaying();

    boolean isFinish();

    void doFullScreen();

    void doSmallScreen();
}
