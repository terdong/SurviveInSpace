package com.teamgehem.gehemengine.sound;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundBGM
{
    private MediaPlayer mPlayer;
    private Context context;
    
    public SoundBGM(Context context, int bgmId){
        this.context = context;
        mPlayer = MediaPlayer.create( context, bgmId);
        mPlayer.setVolume(0.6f, 0.6f);
        mPlayer.setLooping(true);
    }
    public void setBgm(int bgmId){
        mPlayer = MediaPlayer.create(context, bgmId);
    }
    public void bgmStart(){
        mPlayer.start();
    }
    public void bgmStop(){
        mPlayer.stop();
    }
    public void bgmPause(){
        mPlayer.pause();
    }
    public void bgmDispose(){
        bgmStop();
        mPlayer.release();
        mPlayer=null;
    }
    public void bgmPlaying(){
        if(!mPlayer.isPlaying())
            mPlayer.start();
    }
    public boolean isPlaying(){
        return mPlayer.isPlaying();
    }
}//class
