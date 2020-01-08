package com.sbl.foags.exoplayer.cache;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import static android.os.Environment.MEDIA_MOUNTED;


public class VideoCacheUtils {

    private static final String INDIVIDUAL_DIR_NAME = "video-cache";

    public static boolean isCached(Context context, String url) {
        return ExoHttpProxyCacheServer.getProxy(context).isCached(url);
    }

    public static boolean clear(Context context) {
        return getIndividualCacheDirectory(context).delete();
    }

    private static File getIndividualCacheDirectory(Context context) {
        File cacheDir = getCacheDirectory(context, true);
        return new File(cacheDir, INDIVIDUAL_DIR_NAME);
    }

    private static File getCacheDirectory(Context context, boolean preferExternal) {
        File appCacheDir = null;
        String externalStorageState;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) { // (sh)it happens
            externalStorageState = "";
        }
        if (preferExternal && MEDIA_MOUNTED.equals(externalStorageState)) {
            appCacheDir = getExternalCacheDir(context);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        if (appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/";
            appCacheDir = new File(cacheDirPath);
        }
        return appCacheDir;
    }

    private static File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                Log.w("videoCache", "Unable to create external cache directory");
                return null;
            }
        }
        return appCacheDir;
    }
}
