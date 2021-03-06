package com.example.hgdzx.kickfly.View;

import com.example.hgdzx.kickfly.GameObject.GameOverLayer;
import com.example.hgdzx.kickfly.GameObject.MainLayer;
import com.example.hgdzx.kickfly.Until.ActivityUtil;
import com.example.hgdzx.kickfly.Until.GameObjData;
import com.example.hgdzx.kickfly.Until.GameThread;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class GameView extends SurfaceView implements Callback {

    public static final int GAME_STATE_RUN = 1;// 游戏状态：运行
    public static final int GAME_STATE_OVER = 0;// 游戏状态：结束
    public static final int GAME_STATE_PAUSE = 2;// 游戏状态：暂停
    private static final int GAMEOVER_LAYER_WIDTH = ActivityUtil.SCREEN_WIDTH;
    private static final int GAMEOVER_LAYER_HEIGHT = ActivityUtil.SCREEN_HEIGHT;

    SurfaceHolder mHolder;
    GameThread mThread;// 游戏线程
    MainLayer mMainLayer;// 游戏主画面
    GameOverLayer gol;// 游戏结束的画面
    public boolean gameLoop;// 游戏是否循环

    private Bitmap mBackground;

    public GameView(Context context) {
        super(context);
        mHolder = getHolder();
        mHolder.addCallback(this);
        //设定主绘图层的大小
        mMainLayer = new MainLayer(0, 0, ActivityUtil.SCREEN_WIDTH,
                ActivityUtil.SCREEN_HEIGHT, context);
        //生成结束层
        gol = new GameOverLayer(0, 0, GAMEOVER_LAYER_WIDTH,
                GAMEOVER_LAYER_HEIGHT);
        //传递当前context到ActivityUtil工具类中
        ActivityUtil.myContext = context;
        mBackground = ActivityUtil.returnPic("background5", context);
    }

    /**
     * 初始化游戏对象的数据
     */
    public void initGameObjData() {
        //设定游戏循环为真
        gameLoop = true;
        //设定当前游戏当前运行状态为运行
        GameObjData.CURRENT_GAME_STATE = GameView.GAME_STATE_RUN;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    public void surfaceCreated(SurfaceHolder holder) {
        //生成 游戏线程
        mThread = new GameThread(this);
        mThread.start();
    }

    /**
     * 绘制游戏元素
     */
    public void doPaint() {
        Canvas c = mHolder.lockCanvas();
        //c.drawColor(Color.WHITE);

        //绘制背景
        c.drawBitmap(mBackground,
                null, new Rect(0, 0, ActivityUtil.SCREEN_WIDTH, ActivityUtil.SCREEN_HEIGHT),
                new Paint());
        switch (GameObjData.CURRENT_GAME_STATE) {
            case GAME_STATE_RUN:
                mMainLayer.paint(c);
                break;
            case GAME_STATE_PAUSE:
                break;
            case GAME_STATE_OVER:
                mMainLayer.paint(c);
                gol.paint(c);
                if (gol.appearFull) {
                    gameLoop = false;
                }
                break;
        }
        mHolder.unlockCanvasAndPost(c);
    }

    /**
     * 更新游戏元素数据
     */
    public void doUpdate() {
        //检查游戏是否结束
        GameObjData.checkGameOver();
        switch (GameObjData.CURRENT_GAME_STATE) {
            case GAME_STATE_RUN:
                mMainLayer.logic();
                break;
            case GAME_STATE_OVER:
                gol.logic();
                break;
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        gameLoop = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (GameObjData.CURRENT_GAME_STATE) {
            case GAME_STATE_RUN:
                mMainLayer.onTouch(event);
                break;
        }
        return false;
    }
}
