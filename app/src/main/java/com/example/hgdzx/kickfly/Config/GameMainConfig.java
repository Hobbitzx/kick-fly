package com.example.hgdzx.kickfly.Config;

import android.graphics.Rect;

import com.example.hgdzx.kickfly.Until.ActivityUtil;

public class GameMainConfig
{
    public static Rect returnDescBackgroundRect()
    {

        if (ActivityUtil.SCREEN_WIDTH == 800 && ActivityUtil.SCREEN_HEIGHT==480)
        {
            return new Rect(0, 0, 800, 480);
        }
        else
        {
            return new Rect(0, 0, 480, 320);
        }

    }
}
