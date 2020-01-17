package com.sbl.foags.exoplayer;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.offline.FilteringManifestParser;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;
import com.sbl.foags.R;
import com.sbl.foags.exoplayer.base.BasePlayerLayout;
import com.sbl.foags.exoplayer.base.CommonUtils;
import com.sbl.foags.exoplayer.cache.ExoHttpProxyCacheServer;

import java.util.Collections;
import java.util.List;


public class ExoPlayerLayout extends BasePlayerLayout implements View.OnTouchListener,
        OnClickListener,
        ExoPlayerMediaControllerListener {

    private static final String TAG = "sbl ExoPlayerLayout";
    public static int HIDE_TIME = 5000;

    private PlayerView playerView;
    private DataSource.Factory mediaDataSourceFactory;
    private SimpleExoPlayer player;
    private DefaultTrackSelector trackSelector;
    private DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

    private ExoPlayerMediaControllerLayout mediaController;
    private FrameLayout rootLayout;
    private ProgressBar loadingView;
    private FrameLayout errorLayout;
    private TextView errorView;
    private ExoPlayerListener exoPlayerListener;

    private Activity activity;
    private String videoUrl = null;
    private int startWindow;
    private long startPosition;
    private boolean isPause = false;


    public ExoPlayerLayout(Context context) {
        this(context, null);
    }


    public ExoPlayerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public ExoPlayerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExoPlayerLayout);
            try {
                int resizeMode = a.getInt(R.styleable.ExoPlayerLayout_resize_mode, AspectRatioFrameLayout.RESIZE_MODE_FIT);
                playerView.setResizeMode(resizeMode);
            } finally {
                a.recycle();
            }
        }
    }


    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.exo_player_layout, this);
        playerView = findViewById(R.id.playerView);
        loadingView = findViewById(R.id.loadingView);
        errorLayout = findViewById(R.id.errorLayout);
        errorView = findViewById(R.id.errorView);
        rootLayout = findViewById(R.id.rootLayout);

        mediaController = new ExoPlayerMediaControllerLayout(getContext(), this);
        mediaController.setAnchorView(rootLayout);
        mediaController.setOnTouchListener(this);
        rootLayout.setOnTouchListener(this);
        errorView.setOnClickListener(this);
        mediaDataSourceFactory = buildDataSourceFactory(true);
    }


    private void initializePlayer(boolean shouldAutoPlay) {
        if (needCache(videoUrl)) {
            ExoHttpProxyCacheServer.getProxyUrl(getContext(), videoUrl);
        }

        if (player == null) {
            @DefaultRenderersFactory.ExtensionRendererMode int extensionRendererMode =
                    useExtensionRenders()
                            ? DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON
                            : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF;

            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
            trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            DefaultRenderersFactory rendererFactory =
                    new DefaultRenderersFactory(getContext(), extensionRendererMode);
//            MyDefaultRenderersFactory rendererFactory =
//                    new MyDefaultRenderersFactory(getContext());
            player = ExoPlayerFactory.newSimpleInstance(getContext(), rendererFactory, trackSelector);
            player.addListener(new PlayerEventListener());
            player.addAnalyticsListener(new EventLogger(trackSelector));
            mediaController.setEnabled(true);
            playerView.setPlayer(player);
        }

        boolean haveStartPosition = startWindow != C.INDEX_UNSET;
        if (haveStartPosition) {
            player.seekTo(startWindow, startPosition);
        }
        player.setPlayWhenReady(shouldAutoPlay);
        player.prepare(buildMediaSource(Uri.parse(videoUrl), ""), !haveStartPosition, false);

        mediaController.show(HIDE_TIME);
    }


    @SuppressWarnings("unchecked")
    private MediaSource buildMediaSource(Uri uri, String overrideExtension) {
        @C.ContentType int type = Util.inferContentType(uri, overrideExtension);
        switch (type) {
            case C.TYPE_DASH:
                return new DashMediaSource.Factory(
                        new DefaultDashChunkSource.Factory(mediaDataSourceFactory),
                        buildDataSourceFactory(false))
                        .setManifestParser(
                                new FilteringManifestParser<>(
                                        new DashManifestParser(), (List<StreamKey>) getOfflineStreamKeys()))
                        .createMediaSource(uri);
            case C.TYPE_SS:
                return new SsMediaSource.Factory(
                        new DefaultSsChunkSource.Factory(mediaDataSourceFactory),
                        buildDataSourceFactory(false))
                        .setManifestParser(
                                new FilteringManifestParser<>(
                                        new SsManifestParser(), (List<StreamKey>) getOfflineStreamKeys()))
                        .createMediaSource(uri);
            case C.TYPE_HLS:
                return new HlsMediaSource.Factory(mediaDataSourceFactory).createMediaSource(uri);
            case C.TYPE_OTHER:
                return new ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(uri);
            default: {
                throw new IllegalStateException("Unsupported type: " + type);
            }
        }
    }


    private List<?> getOfflineStreamKeys() {
        return Collections.emptyList();
    }


    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        return CommonUtils.getInstance(getContext()).buildDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }


    private boolean useExtensionRenders() {
        return false;
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.errorView) {
            setLoadingShow(true);
            errorLayout.setVisibility(View.GONE);
            initializePlayer(false);
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                try {
                    if (mediaController.isShowing()) {
                        mediaController.hide();
                    } else {
                        if(isPlaying()){
                            mediaController.show(HIDE_TIME);
                        }
                    }
                } catch (NullPointerException var2) {
                    var2.printStackTrace();
                }
                break;
        }
        return true;
    }


    @Override
    public void start() {
        if (player != null)
            player.setPlayWhenReady(true);
    }


    @Override
    public void pause() {
        if (player != null)
            player.setPlayWhenReady(false);
    }


    @Override
    public void restart() {
        if (player != null) {
            player.setPlayWhenReady(true);
            player.seekTo(0);
        }
    }


    @Override
    public long getDuration() {
        return player != null ? player.getDuration() == -1L ? 0 : player.getDuration() : 0;
    }


    @Override
    public long getCurrentPosition() {
        return player != null ? (player.getDuration() == -1L ? 0 : player.getCurrentPosition()) : 0;
    }


    @Override
    public void seekTo(long var1) {
        if (player != null)
            player.seekTo((player.getDuration() == -1L ? 0 : Math.min(Math.max(0, var1), getDuration())));
    }


    @Override
    public boolean isPlaying() {
        return player != null && player.getPlayWhenReady() && player.getPlaybackState() != Player.STATE_ENDED;
    }


    @Override
    public boolean isFinish() {
        return player != null &&  player.getPlaybackState() == Player.STATE_ENDED;
    }


    @Override
    public void doFullScreen() {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }


    @Override
    public void doSmallScreen() {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    private void setLoadingShow(boolean show) {
        loadingView.setVisibility(show ? View.VISIBLE : View.GONE);
        if (mediaController != null) {
            mediaController.setShowStateView(!show);
        }
    }


    public void play(boolean autoPlay) {
        if (TextUtils.isEmpty(videoUrl)) {
            return;
        }
        startPosition = 0;

        if(isPause){
            return;
        }
        initializePlayer(autoPlay);
    }


    public void release() {
        if (player != null) {
            updateStartPosition();
            player.release();
            player = null;
            trackSelector = null;
        }
    }


    private void releasePlayer() {
        if (mediaController != null) {
            mediaController.releaseController();
        }
        setLoadingShow(false);
        errorLayout.setVisibility(GONE);

        if (mediaController != null)
            mediaController.finishController();
        exoPlayerListener = null;
        mediaDataSourceFactory = null;
        activity = null;
        clearStartPosition();
        System.gc();
    }


    public void onResume() {
        if (isPause) {
            initializePlayer(false);
        }
        isPause = false;
    }


    public void onPause() {
        release();
        isPause = true;
    }


    public void onDestroy() {
        releasePlayer();
    }


    private class PlayerEventListener implements Player.EventListener {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            Log.e(TAG, "playWhenReady = " + playWhenReady + ", playbackState = " + playbackState);
            switch (playbackState) {
                case Player.STATE_BUFFERING:
                    Log.e(TAG, "onPlayerStateChanged = ExoPlayer.STATE_BUFFERING");
                    setLoadingShow(true);
                    errorLayout.setVisibility(GONE);
                    break;
                case Player.STATE_READY:
                    Log.e(TAG, "onPlayerStateChanged = ExoPlayer.STATE_READY");
                    setLoadingShow(false);
                    errorLayout.setVisibility(GONE);

                    mediaController.show(HIDE_TIME);
                    break;
                case Player.STATE_ENDED:
                    Log.e(TAG, "onPlayerStateChanged = ExoPlayer.STATE_ENDED");
                    mediaController.show(HIDE_TIME);
                    setLoadingShow(false);
                    errorLayout.setVisibility(GONE);
                    if (exoPlayerListener != null) {
                        exoPlayerListener.playerEnd();
                    }
                    break;
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException e) {
            release();
            mediaController.removeView();
            setLoadingShow(false);
            errorLayout.setVisibility(VISIBLE);
            if (exoPlayerListener != null)
                exoPlayerListener.playerError();
        }

        @Override
        public void onSeekProcessed() {
            mediaController.initSeekBar();
        }
    }


    private void updateStartPosition() {
        if (player != null) {
            startWindow = player.getCurrentWindowIndex();
            startPosition = Math.max(0, player.getContentPosition());
        }
    }


    private void clearStartPosition() {
        startWindow = C.INDEX_UNSET;
        startPosition = C.TIME_UNSET;
    }


    public void setLaunchDate(Activity activity,
                              ExoPlayerListener listener,
                              String videoUrl) {
        this.activity = activity;
        this.exoPlayerListener = listener;
        this.videoUrl = videoUrl;
    }
}