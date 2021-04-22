package com.example.gojawin01;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivitySettings extends Activity {

boolean bmute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Switch switchMusic = (Switch) findViewById(R.id.switch1);
        LoadData();
        final MediaPlayerMusic mediaPlayerMusic = new MediaPlayerMusic(bmute);

        final Context context = this;


        switchMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UpdateData();
                if(isChecked==true) {
                    mediaPlayerMusic.setMusicMute(true);

                }
                else
                {
                    mediaPlayerMusic.setMusicMute(false);
                    mediaPlayerMusic.MusicStop();
                }
            }
        });





    }

    public void LoadData()
    {


        //обновление данных в классе
        Bundle arguments = getIntent().getExtras();
        MediaPlayerMusic mediaPlayerMusic;
        mediaPlayerMusic = (MediaPlayerMusic) arguments.getSerializable(MediaPlayerMusic.class.getSimpleName());

        bmute= mediaPlayerMusic.getMusicMute();
    }

    public void UpdateData() {
        //обновление данных в классе
        Bundle arguments = getIntent().getExtras();
        MediaPlayerMusic mediaPlayerMusic;
        mediaPlayerMusic = (MediaPlayerMusic) arguments.getSerializable(MediaPlayerMusic.class.getSimpleName());
        mediaPlayerMusic.setMusicMute(bmute);
    }



    public void ReturnMain(View view)
    {
        UpdateData();
        //создание экземпляра класса
        Intent intent = new Intent(MainActivitySettings.this, MainActivity.class);
        startActivity(intent);
    }

    protected void onResume() {
        super.onResume();
        Switch switchMusic = (Switch) findViewById(R.id.switch1);
        final MediaPlayerMusic mediaPlayerMusic = new MediaPlayerMusic(true);
        switchMusic.setChecked(mediaPlayerMusic.getMusicMute());

    }

}

