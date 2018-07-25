package com.example.hgdzx.kickfly.GameObject;

import com.example.hgdzx.kickfly.R;
import com.example.hgdzx.kickfly.Until.ActivityUtil;
import com.example.hgdzx.kickfly.Until.AudioUtil;
import com.example.hgdzx.kickfly.Until.GameObjData;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.LinkedList;

public class MainLayer extends Layer {

    private static final int LIVE_FLY_COUNT = 50; // 屏幕中最多存活的苍蝇数量

    private long lastUpdate;

    private Paint mFillPaint;
    private Paint mStrokePaint;
    private Context mContext;
    private LinkedList<Long> mFrameTimestamps = new LinkedList<>();

    public MainLayer(int x, int y, int w, int h, Context context) {
        super(x, y, w, h);
        mContext = context;
        init();
    }

    @Override
    public void init() {
        //生成苍蝇
        for (int i = 0; i < LIVE_FLY_COUNT; i++) {
            addGameObj(new Fly());
        }

        mFillPaint = new Paint();
        mFillPaint.setColor(Color.YELLOW);
        mFillPaint.setAntiAlias(true);
        mFillPaint.setTextSize(toPixel(15));
        mFillPaint.setAlpha(70);

        mStrokePaint = new Paint(mFillPaint);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(8);
        mStrokePaint.setColor(Color.BLACK);
        mStrokePaint.setAntiAlias(true);
        mStrokePaint.setTextSize(toPixel(15));
        mStrokePaint.setAlpha(70);
    }

    @Override
    public void logic() {
        synchronized (objs) {
            for (BaseGameObj obj : objs) {
                obj.logic();
                for (BaseGameObj obj2 : objs) {
                    Fly f = (Fly) obj;
                    if (!f.equals(obj2) && !f.dead) {
                        f.collisionTo((Fly) obj2);
                    }
                }
            }
        }
        //如果当前时间与上次更新时间超过1秒那么累加
        long now = System.currentTimeMillis();
        if (now - lastUpdate >= 1000) {
            GameObjData.CURRENT_USE_TIME++;
            lastUpdate = now;
        }
        mFrameTimestamps.addLast(now);
        while (mFrameTimestamps.getFirst() < now - 1000) {
            mFrameTimestamps.removeFirst();
        }
    }

    @Override
    public void paint(Canvas c) {
        super.paint(c);
        String text = "杀死数量: " + GameObjData.CURRENT_KILL_COUNT;
        c.drawText(text, toPixel(8), toPixel(23), mStrokePaint);
        c.drawText(text, toPixel(8), toPixel(23), mFillPaint);
        text = "用时: " + GameObjData.CURRENT_USE_TIME;
        c.drawText(text, toPixel(8), toPixel(40), mStrokePaint);
        c.drawText(text, toPixel(8), toPixel(40), mFillPaint);
        text = "FPS: " + mFrameTimestamps.size();
        c.drawText(text, toPixel(8), toPixel(57), mStrokePaint);
        c.drawText(text, toPixel(8), toPixel(57), mFillPaint);
    }

    public void onTouch(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        // 播放声音
        AudioUtil.PlayMusic(ActivityUtil.myContext, R.raw.musickill);
        synchronized (objs) {
            for (BaseGameObj obj : objs) {
                Fly f = (Fly) obj;
                if (!f.dead && f.contains(x, y, 50)) {
                    f.dead = true;
                    GameObjData.CURRENT_KILL_COUNT++;
                    if (objs.size() < GameObjData.OVER_KILL_COUNT) {
                        objs.add(new Fly());
                    }
                    return;
                }
            }
        }
    }

    private int toPixel(int dip) {
        return Math.round(dip * mContext.getResources().getDisplayMetrics().scaledDensity + 0.5f);
    }
}
