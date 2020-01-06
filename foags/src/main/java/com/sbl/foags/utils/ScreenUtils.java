package com.sbl.foags.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;


import com.sbl.foags.MyApplication;

import static android.content.Context.KEYGUARD_SERVICE;


public class ScreenUtils {

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕gao度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 得到设备的密度
     */
    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 设置一个View的高度加上状态栏的高度
     */
    public static void addStatusHeightMargin(View view) {
        int statusBarHeight = getStatusHeight(view.getContext());
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        params.topMargin += statusBarHeight;
        view.setLayoutParams(params);
    }

    /**
     * 设置一个View的高度加上状态栏的高度
     */
    public static void addStatusHeightPadding(View view) {
        int statusBarHeight = getStatusHeight(view.getContext());
        view.setPadding(view.getPaddingLeft(),
                view.getPaddingTop() + statusBarHeight,
                view.getPaddingRight(), view.getPaddingBottom());
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params.height != ViewGroup.LayoutParams.WRAP_CONTENT) {
            params.height += getStatusHeight(view.getContext());
            view.setLayoutParams(params);
        }
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 检查是否有虚拟按键
     */
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        return !hasMenuKey && !hasBackKey;
    }

    /**
     * 获取虚拟键盘的高度
     */
    public static int getNavigationHeight(Context context) {
        if (checkDeviceHasNavigationBar(context)) {
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /**
     * 屏幕是否点亮状态
     */
    public static boolean isScreenOn() {
        Context context = MyApplication.instance;
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        assert pm != null;
        return pm.isScreenOn();

    }

    /**
     * 是否是锁屏界面
     */
    public static boolean isScreenLocked() {
        Context context = MyApplication.instance;
        KeyguardManager keyguardManager = ((KeyguardManager) context.getSystemService(KEYGUARD_SERVICE));
        return keyguardManager != null && keyguardManager.inKeyguardRestrictedInputMode();
    }

    /**
     * 唤醒手机屏幕并解锁
     */
    public static void wakeUpAndUnlock() {

        // 获取电源管理器对象
        PowerManager pm = (PowerManager) MyApplication.instance
                .getSystemService(Context.POWER_SERVICE);
        assert pm != null;
        boolean screenOn = pm.isScreenOn();

        if (!screenOn) {
            // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(
                    PowerManager.ACQUIRE_CAUSES_WAKEUP |
                            PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
            wl.acquire(10000); // 点亮屏幕
            wl.release(); // 释放
        }
        // 屏幕解锁
        KeyguardManager keyguardManager = (KeyguardManager) MyApplication.instance
                .getSystemService(KEYGUARD_SERVICE);
        assert keyguardManager != null;
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("unLock");
        // 屏幕锁定
        keyguardLock.reenableKeyguard();
        keyguardLock.disableKeyguard(); // 解锁
    }

    public static void lightenScreen() {
        // 获取电源管理器对象
        PowerManager pm = (PowerManager) MyApplication.instance
                .getSystemService(Context.POWER_SERVICE);

        assert pm != null;
        boolean screenOn = pm.isScreenOn();

        if (!screenOn) {
            // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(
                    PowerManager.ACQUIRE_CAUSES_WAKEUP |
                            PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
            wl.acquire(10000); // 点亮屏幕
            wl.release(); // 释放
        }

    }

    /**
     * 设置状态栏为暗色
     */
    public static void setStateBarIconDark(Activity activity) {
        // 设置状态栏的图标颜色为暗色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    /**
     * 设置状态栏为亮色
     */
    public static void setStateBarIconLight(Activity activity) {
        // 设置状态栏的图标颜色为暗色
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    /**
     * 显示虚拟键盘
     */
    public static void showNavigationBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int flag = View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            //判断当前版本在4.0以上并且存在虚拟按键，否则不做操作
            if (ScreenUtils.checkDeviceHasNavigationBar(activity)) {
                activity.getWindow().getDecorView().setSystemUiVisibility(flag);
            }
        }
    }

    /**
     * 隐藏虚拟键盘
     */
    public static void hideNavigationBar(Activity activity) {
        if (Build.VERSION.SDK_INT < 19) {
            View view = activity.getWindow().getDecorView();
            view.setSystemUiVisibility(View.GONE);
        } else {
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public static void requestActivityOrientation(Activity activity, int orientation) {
        activity.setRequestedOrientation(orientation);
    }

}
