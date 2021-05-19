package com.example.gojawin01;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class MediaPlayerMusic {

    static SoundPool sp;

    static MediaPlayer media_player;
    static MediaPlayer media_player_food;

    static void Init( Context context){
        media_player = MediaPlayer.create(context, R.raw.lift);
        media_player_food = MediaPlayer.create(context, R.raw.food);
        media_player.setLooping(true);
    }

    static void onResumeFood() {


            MediaPlayerMusic.media_player_food.start();

    }
    static void onPauseFood(){
        if( MediaPlayerMusic.media_player_food != null && MediaPlayerMusic.media_player_food.isPlaying()){
            MediaPlayerMusic.media_player_food.pause();
        }
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






