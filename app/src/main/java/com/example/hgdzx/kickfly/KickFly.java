package com.example.hgdzx.kickfly;

import com.example.hgdzx.kickfly.Until.ActivityUtil;
import com.example.hgdzx.kickfly.Until.BitmapManager;
import com.example.hgdzx.kickfly.Until.GameObjData;
import com.example.hgdzx.kickfly.View.GameView;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

public class KickFly extends Activity
{
    //定义游戏的View
    GameView gv;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //全屏并且没有Title,获取通用的显示信息
        ActivityUtil.fullLandScapeScreen(this);
        ActivityUtil.initScreenData(this);
        //载入游戏资源文件，活虫子和死虫子的图片
        Resources res = getResources();
        BitmapManager.initFlyBitmap(res);
        //生成GameView的实例
        if (gv == null)
        {
            gv = new GameView(this);
        } else
        {
            Log.i(ActivityUtil.infoMessage, "GameView已存在");
        }
        setContentView(gv);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.i(ActivityUtil.infoMessage, "Destroy GameView");
        GameObjData.clear();
        System.gc();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.i(ActivityUtil.infoMessage, "Pause GameView");
        gv.gameLoop = false;
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.i(ActivityUtil.infoMessage, "Start GameView");
        gv.initGameObjData();
    }
}