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

boolean bmute = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Switch switchMusic = (Switch) findViewById(R.id.switch1);
        final Context context = this;
        MediaPlayerMusic.Init(context);

        Bundle arguments1 = getIntent().getExtras();

        if(arguments1!=null) {
            bmute = arguments1.getBoolean("mute");
        }




        switchMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked==true) {
                    MediaPlayerMusic.onPause();
                    bmute=true;
                    MediaPlayerMusic.onResume();

                }
                else
                {
                    bmute=false;
                    MediaPlayerMusic.onPause();


                }
            }
        });





    }





    public void ReturnMain(View view)
    {

        //создание экземпляра класса
        Intent intent = new Intent(MainActivitySettings.this, MainActivity.class);
        intent.putExtra("mute", bmute);
        startActivity(intent);
    }

    protected void onResume() {
        super.onResume();
        Switch switchMusic = (Switch) findViewById(R.id.switch1);
        switchMusic.setChecked(bmute);
        if (bmute) {
           // MediaPlayerMusic.onResume();
        }

    }

    @Override
    protected void onPause(){
        super.onPause();
        //MediaPlayerMusic.onPause();
    }

}

