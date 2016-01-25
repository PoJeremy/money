package com.f1reking.mymoney.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.f1reking.mymoney.util.L;
import com.f1reking.mymoney.util.ManifestConfig;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by F1ReKing on 2016/1/3.
 */
public class AppApplication extends Application {

    private List<Activity> activityList = new LinkedList<Activity>();
    private static Stack<Activity> mActivityStack;
    private static AppApplication instance;
    private static Context context;

    private AppApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = context.getApplicationContext();

        boolean isDeveloperMode = isDeveliperMode();

        if (isDeveloperMode) {
            L.openDebug();
        } else {
            L.closeDebug();
        }
    }

    /**
     * 用于读取配置，是否为开发模式，以便控制应用的日志等辅助开发功能的打开或关闭；在应用版本发布时，请在Manifest配置开发者模式为false
     */
    private boolean isDeveliperMode() {
        return ManifestConfig.getBooleanMetaValue(context, "developer_mode");
    }

    public static AppApplication getInstance() {
        if (null == instance) {
            instance = new AppApplication();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (null == mActivityStack) {
            mActivityStack = new Stack<Activity>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     *
     * @return
     */
    public Activity currentActivity() {
        Activity activity = mActivityStack.lastElement();
        return activity;
    }

    /**
     * 移除当前Activity（堆栈中最后一个压入的）
     */
    public void removeActivity() {
        Activity activity = mActivityStack.lastElement();
        removeActivity(activity);
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = mActivityStack.lastElement();
        finishActivity();
    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     *
     * @param cls
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i)) {
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
    }

    /**
     * 退出应用程序
     * @param context
     * @param isBackground
     *          是否开启后台运行
     */
    public void AppExit(Context context,Boolean isBackground){
        finishActivity();
        //设置后台
        if (!isBackground){
            System.exit(0);
        }
    }


}
