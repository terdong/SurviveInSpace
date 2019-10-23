package com.teamgehem.gehemengine.sound;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundEffect
{
    private SoundPool sp;
    private Context context;
    public SoundEffect(Context context)
    {
        this.context = context;
        sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
    }
    
    public void playSound(int soundId){
        sp.play(soundId, 1f, 1f, 0, 0, 1f);
    }
    
    protected int soundLoad(int resId)
    {
        return sp.load(context, resId,1);
    }
    
    public void release(){
        sp.release();
    }
    
}// class
