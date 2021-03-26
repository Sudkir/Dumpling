package com.example.gojawin01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivityBuy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_buy);

        String user = "0";

        //user = getIntent().getExtras().getString("username");
        TextView infoTextView = (TextView)findViewById(R.id.textView4);
        infoTextView.setText(user);
    }


    public final static String THIEF = "com.example.gojawin01.THIEF";

    public void HertMain(View view) {//кнопка перехода на новую активность

        Intent intent = new Intent(MainActivityBuy.this, MainActivity.class);
        Intent answerIntent = new Intent();
        answerIntent.putExtra(THIEF, "Сраный пёсик");
        setResult(RESULT_OK, answerIntent);
        finish();

        //startActivity(intent);//старт окна

    }


    public void onRadioClick(View v) {

/*
        switch (v.getId()) {
            case R.id.radioDog:
                answerIntent.putExtra(THIEF, "Сраный пёсик");
                break;
            case R.id.radioCrow:
                answerIntent.putExtra(THIEF, "Ворона");
                break;
            case R.id.radioCat:
                answerIntent.putExtra(THIEF, "Лошадь Пржевальского");
                break;

            default:
                break;
        }
        */


    }





}