package com.sbl.foags.exoplayer.cache;

import android.content.Context;

import com.danikula.videocache.HttpProxyCacheServer;


public class ExoHttpProxyCacheServer {

    private static HttpProxyCacheServer proxy;

    private static HttpProxyCacheServer newProxy(Context context) {
        return new HttpProxyCacheServer(context.getApplicationContext());
    }

    static synchronized HttpProxyCacheServer getProxy(Context context) {
        if (proxy == null) {
            proxy = newProxy(context);
        }
        return proxy;
    }

    public static String getProxyUrl(Context context, String url){
        return getProxy(context).getProxyUrl(url);
    }

}
