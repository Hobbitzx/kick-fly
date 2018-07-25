package com.example.hgdzx.kickfly.Until;


import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
public class ActivityUtil
{
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static int SCREEN_DPI;
    public static Context myContext;
    public final static String infoMessage="DebugInfo";
    public static void noTitleBar(Activity activity)
    {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public static void noNotificationBar(Activity activity)
    {
        final Window win = activity.getWindow();
        win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 设置全屏及横屏显示
     */
    public static void fullLandScapeScreen(Activity activity) {
        //无标题
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置窗体全屏
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //landscape横屏显示，显示时高度大于宽度
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 获取屏幕尺寸
     */
    public static void initScreenData(Activity activity) {
        //获取显示的通用信息，分辨率等
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        SCREEN_DPI = dm.densityDpi;

    }
    //使用反射技术载入选择图片
    public static Bitmap returnPic(String strPic,Context context)
    {
        int indentify = context.getResources().getIdentifier(context.getPackageName()
                        + ":drawable/" + strPic, null,
                null);
        return BitmapFactory.decodeResource(context.getResources(),
                indentify);
    }
    //震动
    public static void getVibrator(Context context)
    {
        Vibrator vibrator = (Vibrator)context.getSystemService("vibrator");
        long[] pattern = {800, 40,400, 30}; // OFF/ON/OFF/ON...
        vibrator.vibrate(pattern, 2);//-1不重复，非-1为从pattern的指定下标开始重复
    }
    //停顿指定时间
    public static void SleepTime(int time)
    {
        try
        {
            Thread.sleep(time);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
