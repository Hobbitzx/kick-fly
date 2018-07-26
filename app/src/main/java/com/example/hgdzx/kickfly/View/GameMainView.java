package com.example.hgdzx.kickfly.View;

import com.example.hgdzx.kickfly.GameModeActivity;
import com.example.hgdzx.kickfly.KickFly;
import com.example.hgdzx.kickfly.R;

import com.example.hgdzx.kickfly.Until.ActivityUtil;
import com.example.hgdzx.kickfly.Until.AudioUtil;
import com.example.hgdzx.kickfly.Until.GameObjData;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

public class GameMainView extends View
{
    // 上下文
    private Context myContext;
    //自绘控件，由onDraw函数绘制
    private Rect StartRect = new Rect(0,(int)(ActivityUtil.SCREEN_HEIGHT*385/480),
            (int)(ActivityUtil.SCREEN_WIDTH*147/640),(int)(ActivityUtil.SCREEN_HEIGHT*422/480));
    private Rect SetRect = new Rect(0,(int)(ActivityUtil.SCREEN_HEIGHT*432/480),
            (int)(ActivityUtil.SCREEN_WIDTH*147/640),(int)(ActivityUtil.SCREEN_HEIGHT*468/480));

    public GameMainView(Context context)
    {
        super(context);
        myContext = context;
        // 开始背景声音线程线程
        Thread MusicThread = new Thread(new MusicHandler());
        MusicThread.start();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
            // 绘制背景
            canvas.drawBitmap(ActivityUtil.returnPic("main_menu", myContext), null,
                    new Rect(0,0,ActivityUtil.SCREEN_WIDTH,ActivityUtil.SCREEN_HEIGHT),
                    new Paint());
    }

    @Override
    //处理手机屏幕的触摸事件
    //event封装了触摸事件的所有信息例如触摸位置、类型及触摸时间，在触摸时被创建
    public boolean onTouchEvent(MotionEvent event)
    {
        //屏幕被按下时
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            int ax = (int) event.getX();
            int ay = (int) event.getY();

            // 点击到开始按钮
            if (StartRect.contains(ax, ay))
            {


                // 转向到继续游戏
                Intent intent = new Intent();
                intent.setClass(getContext(), KickFly.class);
                // 设定游戏的模式为打死30只虫子就结束
                GameObjData.CURRENT_GAME_MODE = GameObjData.MODE_100C;
                // 转向登陆后的页面
                getContext().startActivity(intent);
            }
            // 点击到设置按钮
            if (SetRect.contains(ax, ay))
            {
                // 转向到继续游戏
                Intent intent = new Intent();
                intent.setClass(getContext(), GameModeActivity.class);
                // 转向登陆后的页面
                getContext().startActivity(intent);
                // ActivityUtil.ShowXYMessage(getContext(), ax, ay);
            }
        }
        postInvalidate();// 刷新屏幕
        return super.onTouchEvent(event);
    }

    class MusicHandler implements Runnable
    {
        public void run()
        {
            try
            {
                Thread.sleep(500);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            // 播放声音
            AudioUtil.PlayMusicLoop(myContext, R.raw.backsound);
        }
    }

}
