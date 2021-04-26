package com.example.gojawin01;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

public class MediaPlayerMusic {

    static SoundPool sp;
    static int drop_on_field;
    static int drop_on_screen;
    static int take;
    static int complete;
    static MediaPlayer media_player;

    static void Init( Context context){
        media_player = MediaPlayer.create(context, R.raw.lift);
        media_player.setLooping(true);
    }


    static void onResume(){
        if( MediaPlayerMusic.media_player != null && !MediaPlayerMusic.media_player.isPlaying()){
            MediaPlayerMusic.media_player.start();
        }
    }


    static void onPause(){
        if( MediaPlayerMusic.media_player != null && MediaPlayerMusic.media_player.isPlaying()){
            MediaPlayerMusic.media_player.pause();
        }
    }
}






