package com.example.hgdzx.kickfly;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.example.hgdzx.kickfly.Until.ActivityUtil;
import com.example.hgdzx.kickfly.View.GameMainView;

public class GameMainActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // 全屏并且没有Title并且横屏显示
        ActivityUtil.fullLandScapeScreen(this);
        // 得到屏幕的相关信息并且保存在ActivityUtil中的静态变量中
        ActivityUtil.initScreenData(this);
        //输出主界面
        setContentView(new GameMainView(this));
    }

}