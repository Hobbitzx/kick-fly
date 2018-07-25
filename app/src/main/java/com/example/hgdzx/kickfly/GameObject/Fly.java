package com.example.hgdzx.kickfly.GameObject;


import java.util.Random;

import com.example.hgdzx.kickfly.Until.ActivityUtil;
import com.example.hgdzx.kickfly.Until.BitmapManager;
import com.example.hgdzx.kickfly.R;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;


public class Fly extends BaseGameObj {

    public static final int FLY_WIDTH = 65;
    public static final int FLY_HEIGHT = 60;

    private static final int MOVE_DIRECTION_LEFT = 0;
    private static final int MOVE_DIRECTION_RIGHT = 1;

    private static final int MOVE_DIRECTION_DOWN = 2;
    private static final int MOVE_DIRECTION_UP = 3;
    private static final int MOVE_OFFSET = 10;

    static final Bitmap texture = BitmapManager.getBitmap(R.drawable.fly); // 纹理资源
    static final Bitmap textureDead = BitmapManager.getBitmap(R.drawable.deadfly);

    static final Random rdm = new Random();
    static final Matrix m = new Matrix();

    public int mDirection = 0;// 当前苍蝇的朝向
    private Bitmap mTexture;
    private Bitmap mTextureDead;
    private long mLastMoveTime;

    public boolean dead = false;

    public Fly() {
        w = FLY_WIDTH;
        h = FLY_HEIGHT;
        init();
    }

    @Override
    public void paint(Canvas c) {
        if (dead) {
            c.drawBitmap(mTextureDead, x, y, null);
        } else {
            c.drawBitmap(mTexture, x, y, null);
        }
    }

    @Override
    public void init() {
        x = rdm.nextInt(ActivityUtil.SCREEN_WIDTH - FLY_WIDTH);//生成0-bound的随机数
        y = rdm.nextInt(ActivityUtil.SCREEN_HEIGHT - FLY_HEIGHT);

        mDirection = rdm.nextInt(4);
        mTexture = getFrame(mDirection, texture);
        mTextureDead = getFrame(mDirection, textureDead);
        visiable = true;
    }

    /**
     * 移动
     */
    public void move() {
        if (dead) {
            return;
        }
        long time = System.currentTimeMillis();
        if (mLastMoveTime == 0) {
            mLastMoveTime = time;
        }
        int move = (int) ((time - mLastMoveTime) * MOVE_OFFSET / 100);
        switch (mDirection) {
            case MOVE_DIRECTION_DOWN:
                y += move;
                if (y + 5 + h >= ActivityUtil.SCREEN_HEIGHT) {
                    mDirection = MOVE_DIRECTION_UP;
                    mTexture = getFrame(mDirection, texture);
                    mTextureDead = getFrame(mDirection, textureDead);
                }
                break;
            case MOVE_DIRECTION_UP:
                y -= move;
                if (y + 5 <= 0) {
                    mDirection = MOVE_DIRECTION_DOWN;
                    mTexture = getFrame(mDirection, texture);
                    mTextureDead = getFrame(mDirection, textureDead);
                }
                break;
            case MOVE_DIRECTION_LEFT:
                x -= move;
                if (x + 5 <= 0) {
                    mDirection = MOVE_DIRECTION_RIGHT;
                    mTexture = getFrame(mDirection, texture);
                    mTextureDead = getFrame(mDirection, textureDead);
                }
                break;
            case MOVE_DIRECTION_RIGHT:
                x += move;
                if (x + 5 + w >= ActivityUtil.SCREEN_WIDTH) {
                    mDirection = MOVE_DIRECTION_LEFT;
                    mTexture = getFrame(mDirection, texture);
                    mTextureDead = getFrame(mDirection, textureDead);
                }
                break;
        }
        mLastMoveTime = time;
    }

    public boolean contains(int mx, int my) {
        return mx > x && mx < x + w && my > y && my < y + h;
    }

    public boolean contains(int mx, int my, int tolerance) {
        return mx > x - tolerance && mx < x + w + tolerance &&
                my > y - tolerance && my < y + h + tolerance;
    }

    @Override
    public void logic() {
        move();
    }

    /**
     * 获得对象指定方向上的帧图片
     *
     * @param mDirection 方向
     * @param b          原始图片
     * @return
     */
    private Bitmap getFrame(int mDirection, Bitmap b) {
        switch (mDirection) {
            case MOVE_DIRECTION_DOWN:
                m.setRotate(270);
                break;
            case MOVE_DIRECTION_UP:
                m.setRotate(90);
                break;
            case MOVE_DIRECTION_LEFT:
                m.setRotate(0);
                break;
            case MOVE_DIRECTION_RIGHT:
                m.setRotate(180);
                break;
        }
        //创建一个子位图，各参数含义如下：
        // 产生子位图的源位图
        // 子位图第一个像素在源位图的X坐标
        // 子位图第一个像素在源位图的Y坐标
        // 子位图每一行的像素个数
        // 子位图的行数
        // 对像素值进行变换的可选矩阵
        // 若为true，源图要被过滤
        return Bitmap.createBitmap(b, 0, 0, w, h, m, true);
    }

    /**
     * 碰撞检测
     *
     * @param fly
     */
    public void collisionTo(Fly fly) {
        switch (mDirection) {
            case MOVE_DIRECTION_DOWN:
                if (fly.contains(x + 5, y + 5 + h)
                        || fly.contains(x + 5 + w, y + 5 + h)) {
                    mDirection = MOVE_DIRECTION_LEFT;
                    mTexture = getFrame(mDirection, texture);
                    mTextureDead = getFrame(mDirection, textureDead);
                }
                break;
            case MOVE_DIRECTION_UP:
                if (fly.contains(x + 5, y + 5) || fly.contains(x + 5 + w, y + 5)) {
                    mDirection = MOVE_DIRECTION_DOWN;
                    mTexture = getFrame(mDirection, texture);
                    mTextureDead = getFrame(mDirection, textureDead);
                }
                break;
            case MOVE_DIRECTION_LEFT:
                if (fly.contains(x + 5, y + 5) || fly.contains(x, y + h + 5)) {
                    mDirection = MOVE_DIRECTION_UP;
                    mTexture = getFrame(mDirection, texture);
                    mTextureDead = getFrame(mDirection, textureDead);
                }
                break;
            case MOVE_DIRECTION_RIGHT:
                if (fly.contains(x + 5 + w, y + 5)
                        || fly.contains(x + 5 + w, y + h + 5)) {
                    mDirection = MOVE_DIRECTION_LEFT;
                    mTexture = getFrame(mDirection, texture);
                    mTextureDead = getFrame(mDirection, textureDead);
                }
                break;
        }
    }
}
