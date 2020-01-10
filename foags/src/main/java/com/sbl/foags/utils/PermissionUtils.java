package com.sbl.foags.utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.yanzhenjie.permission.AndPermission;

import java.util.List;


public class PermissionUtils {

    public static void requestRuntimePermission(Context context, @NonNull final OnPermissionListener listener, String... permissions) {
        AndPermission.with(context)
                .runtime()
                .permission(permissions)
                .onGranted(listener::onPermissionGranted)
                .onDenied(listener::onPermissionDenied)
                .start();
    }

    public static void requestRuntimePermission(Context context, @NonNull final OnPermissionListener listener, String[]... permissions) {
        AndPermission.with(context)
                .runtime()
                .permission(permissions)
                .onGranted(listener::onPermissionGranted)
                .onDenied(listener::onPermissionDenied)
                .start();
    }

    public static void requestRuntimePermission(Fragment fragment, @NonNull final OnPermissionListener listener, String... permissions) {
        AndPermission.with(fragment)
                .runtime()
                .permission(permissions)
                .onGranted(listener::onPermissionGranted)
                .onDenied(listener::onPermissionDenied)
                .start();
    }

    public static void requestRuntimePermission(Fragment fragment, @NonNull final OnPermissionListener listener, String[]... permissions) {
        AndPermission.with(fragment)
                .runtime()
                .permission(permissions)
                .onGranted(listener::onPermissionGranted)
                .onDenied(listener::onPermissionDenied)
                .start();
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        return AndPermission.hasPermissions(context, permissions);
    }

    public static boolean hasPermissions(Context context, String[]... permissions) {
        return AndPermission.hasPermissions(context, permissions);
    }

    public static boolean hasPermissions(Fragment fragment, String... permissions) {
        return AndPermission.hasPermissions(fragment, permissions);
    }

    public static boolean hasPermissions(Fragment fragment, String[]... permissions) {
        return AndPermission.hasPermissions(fragment, permissions);
    }

    public interface OnPermissionListener {

        void onPermissionGranted(List<String> permissions);

        void onPermissionDenied(List<String> data);
    }

}
