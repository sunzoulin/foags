package com.sbl.foags.common.cache;

import com.sbl.foags.MyApplication;
import com.sbl.foags.common.config.CacheConstants;
import com.sbl.foags.user.User;
import com.sbl.foags.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;


public class RxACache {

    private static RxACache INSTANCE = null;
    private ACache aCache;
    private Map<String, Integer> cacheTimes;

    /**
     * 用户信息
     */
    private User user;

    private RxACache() {
        aCache = ACache.get(MyApplication.instance);
        cacheTimes = new HashMap<>();
        initCacheTime();
    }

    public static RxACache getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RxACache();
        }
        return INSTANCE;
    }

    /**
     * 设置各种缓存时间
     */
    private void initCacheTime() {

    }

    /**
     * 放入过期时间
     */
    public void putCacheTime(String key, int time) {
        cacheTimes.put(key, time);
    }

    public <T extends Object> T get(String key) {
        // 注意此处的key不可以为空，否则返回null
        return StringUtils.isEmpty(key) ? null : (T) aCache.getAsObject(key);
    }

    /**
     * 删除缓存
     *
     * @param key
     */
    public void remove(final String key) {
        aCache.execute(() -> {
            try {
                aCache.remove(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 保存
     *
     * @param key
     * @param obj
     */
    public void put(String key, Object obj) {
        // 判断是否有过期时间
        if (cacheTimes.containsKey(key)) {
            aCache.put(key, obj, cacheTimes.get(key));
        } else {
            aCache.put(key, obj);
        }
    }

    /**
     * 加入缓存时间的key
     */
    public void putByCacheTime(String key, Object obj, int cacheTime) {
        aCache.put(key, obj, cacheTime);
    }

    /**
     * 获取用户信息
     */
    public synchronized User getUser() {
        if (user == null) {
            user = getInstance().get(CacheConstants.CURRENT_USER_INFO_CACHE_KEY);
        }
        return user;

    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 获取用户id
     */
    public String getUserId() {
        return null != getUser() ? getUser().getId() : null;
    }

    /**
     * 清空用户信息
     */
    public void clearUserInfo() {
        setUser(null);
        remove(CacheConstants.CURRENT_USER_INFO_CACHE_KEY);
    }

    /**
     * 保存用户信息，修改了用户信息后，请调用此方法保存到本地
     */
    public void saveUserInfo() {
        put(CacheConstants.CURRENT_USER_INFO_CACHE_KEY, user);
    }

}
