package com.example.gojawin01;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

public class MediaPlayerMusic {

    boolean MusicMute;
    MediaPlayer mediaPlayer;
    AudioManager am;


    //конструктор класса
    public MediaPlayerMusic(boolean MusicMute){
        this.MusicMute = MusicMute;
    }


    public boolean getMusicMute() {
        return MusicMute;
    }

    public void setMusicMute(boolean MusicMute) {
        this.MusicMute = MusicMute;
    }



    // variable to hold context
    private Context context;

//save the context received via constructor in a local variable
    public void  MusicStart(Context context)
    {
        this.context=context;
        mediaPlayer = MediaPlayer.create(context, R.raw.lift);
        if (!mediaPlayer.isPlaying()&& MusicMute) {
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }

    }
    public void MusicStop()
    {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }





}
