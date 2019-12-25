package com.sbl.foags.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.Application;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.core.content.FileProvider;


import com.sbl.foags.MyApplication;
import com.sbl.foags.manager.ActivityManager;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class SystemUtil {


    /**
     * 手机型号
     *
     * @return 手机型号
     */
    public static String getMobileType() {
        return Build.MANUFACTURER + " " + Build.MODEL;
    }

    /**
     * 弹出软件盘
     */
    public static void showSoftInputFromWindow(View view) {
        ((InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE))
                .showSoftInput(view, InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    /**
     * 隐藏软件盘
     */
    public static void hideSoftInputFromWindow(Activity activity) {
        if (activity == null)
            return;
        ((InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(activity.getWindow().getDecorView()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    /**
     * 判断当前应用程序处于前台还是后台
     */
    public static boolean isRunningBackground() {
        return ActivityManager.getInstance().isRunningBackground();
    }

    /**
     * 判断通知是否打开
     */
    public static boolean isNotificationEnabled(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return true;
        }
        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass;
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void requestSettingCanDrawOverlays(Context context) {
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= Build.VERSION_CODES.O) {//8.0以上
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            if (context instanceof Application) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        } else if (sdkInt >= Build.VERSION_CODES.M) {//6.0-8.0
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            if (context instanceof Application) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            context.startActivity(intent);
        } else {//4.4-6.0一下
            //无需处理了
        }
    }

    /**
     * 检查是否这个activity
     */
    private static boolean findActivity(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null;
    }

    /**
     * 通用跳转到应用设置页面
     */
    private static void goToCommonAppSetting(Context context) {
        Uri packageURI = Uri.parse("package:" + context.getPackageName());
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        context.startActivity(intent);
    }

    /**
     * 判断是否已经安装了app
     */
    public static Boolean hasInstallApp(Context context, String packageName) {
        try {
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(packageName,
                            PackageManager.GET_UNINSTALLED_PACKAGES);

            return info != null;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 拨打电话
     */
    public static void dial(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }

    /**
     * 是否允许安装未知来源的应用
     */
    public static boolean checkCanInstallUnknownApk(Context context) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.O || context.getPackageManager().canRequestPackageInstalls();
    }

    /**
     * 安装app
     */
    public static void installApk(final Context context, final File file) {
        // 如果是8.0手机，需要判断权限
        installApkWithoutPermission(context, file);
        /*if (checkCanInstallUnknownApk(context)) {
            installApkWithoutPermission(context, file);
        } else {
            // 申请安装权限
            PermissionUtils.requestRuntimePermission(context, new PermissionUtils.OnPermissionListener() {
                @Override
                public void onPermissionGranted(List<String> permissions) {
                    installApkWithoutPermission(context, file);
                }

                @Override
                public void onPermissionDenied(List<String> data) {
                    new CustomDialog.Builder(context)
                            .setMessage(R.string.setting_install_apk_tip)
                            .setLeftText(R.string.cancel)
                            .setRightText(R.string.setting_install_apk)
                            .setOnButtonClickListener((dialog, index) -> {
                                if (index == 2) {
                                    SystemUtil.goSystemInstallUnknownApk((Activity) context);
                                }
                            }).show();
                }
            }, Manifest.permission.REQUEST_INSTALL_PACKAGES);
        }*/
    }

    private static void installApkWithoutPermission(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 判断是否是7.0
        if (Build.VERSION.SDK_INT >= 24) {
            // 适配android7.0 ，不能直接访问原路径
            // 需要对intent 授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(FileProvider.getUriForFile(context, context.getPackageName() + ".update.fileProvider", file),
                    "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }


    /**
     * 权限判断需要系统大于android 6.0
     */
//    public static boolean needCheckPermission() {
//        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
//    }

    /**
     * 打开手机浏览器
     */
    public static void openSystemBrowser(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (url.startsWith("http://")) {
            intent.setData(Uri.parse(url));
        } else {
            intent.setData(Uri.parse("http://" + url));
        }
        context.startActivity(intent);

    }

    /**
     * 打开手机浏览器
     */
    public static void openBrowser(Context context, String url) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri contentUrl = Uri.parse(url);
            intent.setData(contentUrl);
            context.startActivity(intent);
        } catch (Exception ignored) {

        }
    }

    /**
     * 打开已安装的app
     */
    public static void launchApp(Context context, String packageName, String activityName) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName(packageName, activityName);
        intent.setComponent(cn);
        context.startActivity(intent);
    }


    /**
     * 模拟点击home键，回到桌面
     */
    public static void backHome(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(intent);
    }

    /**
     * 启动app
     */
    public static void launch(Context context) {
        context.startActivity(context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()));
    }


    /**
     * 把普通路径装换成Uri
     * */
    public static String convertPathToUri(String path){
        return "file://" + path;
    }
}
