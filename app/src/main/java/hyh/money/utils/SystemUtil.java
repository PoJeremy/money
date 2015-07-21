/**
 * @Title: SystemUtil.java
 * @Package com.yuexunit.newdis.util
 * @Description: TODO
 * @author LinSQ
 * @date 2014-8-22 下午5:38:12
 * @version V1.0
 */
package hyh.money.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/**
 * @author LinSQ
 * @ClassName: SystemUtil
 * @Description: TODO
 * @date 2014-8-22 下午5:38:12
 */
public class SystemUtil {

    public static String getPhoneID(Context context) {
        // 获取手机唯一标识
        String phoneid = null;
        try {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(context.TELEPHONY_SERVICE);
            phoneid = tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
            phoneid = null;
        }
        if (phoneid == null || "".equals(phoneid))
            phoneid = "18120758248";
        return phoneid;
    }

    public static float getDisplayMetrics(Context context) {
        // 获取当前屏幕的宽度高度
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        // int screenWidth=dm.widthPixels;
        // int screenHeight=dm.heightPixels;
        // Point srceenInfo=new Point(screenWidth, screenHeight);
        return dm.density;
    }

    public static int getViewMeasuredWidth(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredWidth();
    }

    public static int getViewMeasuredHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredHeight();
    }

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
     * 获得屏幕高度
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

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static void getScreenInfo(Context context) {
        // 获取当前屏幕的宽度高度
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        Log.e("lintest", "ScreenInfo dip= " + dm.density + " width= "
                + screenWidth + " height= " + screenHeight);
    }

    public static int getCacheMaxSize(Context context) {
        // 获取空闲内存容量
        ActivityManager activeManager = ((ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE));
        int memClass = activeManager.getMemoryClass();
        return 1024 * 1024 * memClass / 4; // 硬引用缓存容量，为系统可用内存的1/4
    }

    public static boolean isGPSEnable(Context context) {
        // 判别GPS是否开启
        LocationManager alm = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (alm.isProviderEnabled(LocationManager.GPS_PROVIDER))
            return true;
        else
            return false;
    }

    public static boolean checkNetWork(Context context) {
        // 判别网络是否连接
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        int NET_TYPE = tm.getNetworkType();
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetWorkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetWorkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiNetWorkInfo != null && wifiNetWorkInfo.isAvailable()) {
            if (wifiNetWorkInfo.isConnected())
                return true;// "wifi true";
            else
                return false;// "wifi false";
        } else if (mobileNetWorkInfo != null && mobileNetWorkInfo.isAvailable()) {
            if (mobileNetWorkInfo.isConnected())
                return true;// return "mobile true";
            else
                return false;// return "mobile false";
        } else
            return false;// return "null";
    }

    /**
     * 求百分比
     *
     * @param x
     * @param total
     * @return
     */
    public static String getPercent(double x, double total) {
        String result = "";//接受百分比的值
        double x_double = x * 1.0;
        double tempresult = x / total;
        NumberFormat nf = NumberFormat.getPercentInstance();     //注释掉的也是一种方法
        nf.setMinimumFractionDigits(2);        //保留到小数点后几位
        DecimalFormat df1 = new DecimalFormat("0.00%");    //##.00%   百分比格式，后面不足2位的用0补齐
        result = nf.format(tempresult);
//        result = df1.format(tempresult);
        return result;
    }



}
