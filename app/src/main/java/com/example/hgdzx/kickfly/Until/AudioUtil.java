package com.example.hgdzx.kickfly.Until;
import java.util.HashMap;//引入HashMap类

import com.example.hgdzx.kickfly.R;

import android.app.Activity;
import android.content.Context;//
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
public class AudioUtil
{
    MediaPlayer mMediaPlayer;

    HashMap<Integer, Integer> soundPoolMap;
    Context myContext;

    //播放指定声音
    public static void PlayMusic(Context context,int musicid)
    {
        MediaPlayer mediaplay;
        mediaplay=MediaPlayer.create(context, musicid);
        mediaplay.start();
    }
    public static void PlayMusicLoop(Context context,int musicid)
    {
        MediaPlayer mediaplay;
        mediaplay=MediaPlayer.create(context, musicid);
        mediaplay.setLooping(true);
        mediaplay.start();
    }
}
