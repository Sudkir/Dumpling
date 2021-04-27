package com.example.gojawin01;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivitySettings extends Activity {

boolean bmute = true;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final Context context = this;
        MediaPlayerMusic.Init(context);
        Starting();//старт всего

        //обраобтка свайпов на фоне
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setOnTouchListener(new OnSwipeTouchListener(context) {
            public void onSwipeTop() {

            }
            public void onSwipeRight() {

            }
            public void onSwipeLeft() {

                Return();

            }
            public void onSwipeBottom() {

            }

        });
        //обработка вкл.выкл музыки
        Switch switchMusic = (Switch) findViewById(R.id.switch1);
        switchMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked==true ) {
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

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchBar = (Switch) findViewById(R.id.SwitchBar);
        switchBar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {

                }
                else
                {

                }
            }
        });
    }



    public void Return() {
    //создание экземпляра класса
    Intent intent = new Intent(MainActivitySettings.this, MainActivity.class);
    intent.putExtra("mute", bmute);
    startActivity(intent);
    MediaPlayerMusic.onPause();

}

public void Starting()
{
    Switch switchMusic = (Switch) findViewById(R.id.switch1);
    Bundle arguments1 = getIntent().getExtras();
    if(arguments1!=null) {
        bmute = arguments1.getBoolean("mute");
        if(bmute) {
            MediaPlayerMusic.onResume();
        }
    }
    switchMusic.setChecked(bmute);
}




    public void ReturnMain(View view)
    {
        Return();
    }

    @Override
    protected void onPause(){
        super.onPause();
        Return();
    }

    protected void onStop() {
        super.onStop();
        //Return();
    }


    protected void onResume() {
        super.onResume();
        Starting();
    }



}

