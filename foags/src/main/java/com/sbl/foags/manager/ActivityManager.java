package com.sbl.foags.manager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.sbl.foags.MyApplication;
import com.sbl.foags.base.BaseActivity;
import java.util.LinkedList;
import java.util.List;


public class ActivityManager {

    private final LinkedList<BaseActivity> mActivities = new LinkedList<>();
    private static ActivityManager sManager;

    //用于记录应用是否运行在前台
    private int mOnStartCount;

    private ActivityManager() {

        MyApplication.instance.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if (activity instanceof BaseActivity) {
                    add((BaseActivity) activity);
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
                mOnStartCount++;
                Log.i("ActivityTag", mOnStartCount+",onActivityStarted activity="+activity.toString());
            }

            @Override
            public void onActivityResumed(Activity activity) {
                //将当前显示的 Activity 放在栈顶
                if (activity instanceof BaseActivity) {
                    mActivities.remove(activity);
                    add((BaseActivity) activity);
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                mOnStartCount--;
                Log.i("ActivityTag", mOnStartCount+",onActivityStopped activity="+activity.toString());
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                //Activity 被消毁（finish 或 旋转了屏幕），将它从activities 列表中移除
                if (activity instanceof BaseActivity) {
                    mActivities.remove(activity);
                }
            }
        });
    }

    /**
     * 单一实例
     */
    public static ActivityManager getInstance() {
        if (sManager == null) {
            sManager = new ActivityManager();
        }
        return sManager;
    }

    /**
     * 是否运行在后台
     */
    public boolean isRunningBackground() {
        Log.i("ActivityTag", "isRunningBackground "+mOnStartCount);
        return mOnStartCount == 0;
    }

    private void add(BaseActivity activity) {
        // 如果是打开第一个页面，需要判断是否弹出视频请求页面
        // 因为mOnStartCount会先执行start周期，所以会+1
        // 如果要启动的Activity正好是AVChatActivity，不用进入判断
//        if (!(activity instanceof AVChatActivity)) {
//            if (AppAVChatManager.Companion.getManager().getIncomingCall()) {
//                AVChatActivity.Companion.receiveVideoChat(AppAVChatManager.Companion.getManager().getChatConfig(), 0);
//            }
//        }
        mActivities.add(activity);
    }

    public Activity findActivityByClass(Class clazz) {
        if (mActivities.size() == 0) {
            return null;
        }
        for (Activity activity : mActivities) {
            if (activity.getClass() == clazz) {
                return activity;
            }
        }
        return null;
    }


    /**
     * 启动一个Activity
     *
     * @param _class 类名
     */
    public void startActivity(Class<? extends BaseActivity> _class) {
        Context context = getTopActivity();
        Intent intent = new Intent();
        if (context == null) {
            context = MyApplication.instance;
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.setClass(context, _class);
        context.startActivity(intent);
    }

    /**
     * 启动一个Activity
     */
    public void startActivity(Intent intent, Class<? extends BaseActivity> _class) {
        Context context = getTopActivity();
        intent = intent == null ? new Intent() : intent;
        if (context == null) {
            context = MyApplication.instance;
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.setClass(context, _class);
        context.startActivity(intent);
    }

    /**
     * 获取栈顶Activity（堆栈中最后一个压入的）
     */
    public BaseActivity getTopActivity() {
        return mActivities.peekLast();
    }

    /**
     * 退出应用程序
     */
    public void exit() {
        killAllActivity();
    }

    /**
     * 获取Activity栈中数量
     *
     * @return activity保存的数量
     */
    public int getActivitySize() {
        return mActivities.size();
    }

    /**
     * 获取栈
     *
     * @return 启动的全部Activity
     */
    public List<BaseActivity> getActivates() {
        return mActivities;
    }

    /**
     * finish all activity
     */
    public void killAllActivity() {
        for (BaseActivity baseActivity : mActivities) {
            if (!baseActivity.isFinishing()) {
                baseActivity.finish();
            }
        }
    }
}
