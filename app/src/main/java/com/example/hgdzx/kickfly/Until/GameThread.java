package com.example.hgdzx.kickfly.Until;
import com.example.hgdzx.kickfly.View.GameView;
import android.util.Log;

public class GameThread extends Thread
{

    //游戏主view
    GameView mGv;
    public GameThread(GameView gv)
    {
        mGv = gv;
    }

    @Override
    public void run()
    {
        while (!Thread.currentThread().isInterrupted() && mGv.gameLoop)
        {
            mGv.doUpdate();
            mGv.doPaint();
        }
        Log.i(ActivityUtil.infoMessage, "GameThread Over");
    }
}
