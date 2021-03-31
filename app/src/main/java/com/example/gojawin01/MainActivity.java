package com.example.gojawin01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


//класс для анимации
class BounceInterpolator implements android.view.animation.Interpolator {
    private double mAmplitude;
    private double mFrequency;

    BounceInterpolator(double amplitude, double frequency) {
        mAmplitude = amplitude;
        mFrequency = frequency;
    }

    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time / mAmplitude) *
                Math.cos(mFrequency * time) + 1);
    }
}



public class MainActivity extends AppCompatActivity {


    TextView timerTextView;

    long startTime = 0;
    int seconds;
    int minutes;

    public int boostUp = 10; //цена прокачки тика
    public int mCount =0; //общее количесво очков
    public int lvlUpOne = 1; // уровень прокачки
    public int boostUpTime = 500;
    public int lvlUpTime = 1; // уровень прокачки
    public TextView mShowCount; // показ очков
    public TextView price; // показ цены клика
    public TextView priceTime; // показ цены тайма
    public  TextView TimerTxt;
    boolean bol = false;

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
             seconds = (int) (millis / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;

            timerTextView.setText(String.format("%d", seconds));
            timerHandler.postDelayed(this, 1000);

            mCount=mCount + lvlUpTime;
            mShowCount.setText(Integer.toString(mCount));
        }
    };


    //сохранение данных
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_SCORE= "Score"; // очки
    public static final String APP_PREFERENCES_UPTIME= "UpTime"; // очки
    public static final String APP_PREFERENCES_BOOL = "Bool";
    public static final String APP_PREFERENCES_PRICETIME = "Time"; // прокачка таймера
    public static final String APP_PREFERENCES_PRICESCORE = "PriceScore"; // прокачка таймера
    public static final String APP_PREFERENCES_UPONE = "lvlUp"; // прокачка таймера


    SharedPreferences mSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //спрятать заголовок
        getSupportActionBar().hide();

        timerTextView = (TextView) findViewById(R.id.timerTxt);
        Button b = (Button) findViewById(R.id.btnTimer);

        mShowCount = (TextView) findViewById(R.id.textView);
        mCount =0;
        mShowCount.setText(Integer.toString(mCount));
        price = (TextView) findViewById(R.id.Price);

        //сейв переменных
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        final Toast toast = Toast.makeText(this, R.string.boost_message, Toast.LENGTH_LONG);

        //Кнопка покупки
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Button b = (Button) v;


                if (mCount>= boostUpTime) {
                    //зануление счётчика
                    timerHandler.removeCallbacks(timerRunnable);
                    //старт таймера
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    b.setVisibility(View.INVISIBLE);
                    mCount = mCount - boostUpTime;
                    boostUpTime = 500;
                    bol = true;

                }
                else
                {
                    toast.show();
                }
            }
        });

    }

    protected void onPause() {
        super.onPause();
        //пауза приложения

    }

    @Override
    public void onStart() {
        super.onStart();
        //onCreate->onStart->onResume
    }

    protected void onStop(){
        super.onStop();
        //сохранение очков в память при закрытии приложения
        String strCount = mShowCount.getText().toString();

        SharedPreferences.Editor editor = mSettings.edit();

        editor.putString(APP_PREFERENCES_SCORE, strCount);

        String strLvlUpTime = Integer.toString(lvlUpTime);
        editor.putString(APP_PREFERENCES_UPTIME, strLvlUpTime);

        String strLvlUpOne = Integer.toString(lvlUpOne);
        editor.putString(APP_PREFERENCES_UPONE, strLvlUpOne);

        String strPriceTime = Integer.toString(boostUpTime);
        editor.putString(APP_PREFERENCES_PRICETIME, strPriceTime);

        String strPriceScore = Integer.toString(boostUp);
        editor.putString(APP_PREFERENCES_PRICESCORE, strPriceScore);

        editor.putBoolean(APP_PREFERENCES_BOOL, bol);
        editor.apply();

    }



    protected void onResume()
{
    super.onResume();

    AnimDollar();


    //принятие данных из второй формы через класс BuyUp
    //обновление данных

    Bundle arguments = getIntent().getExtras();
    if(arguments!=null) {//проверка на NULL
        BuyUp buyUp;
        buyUp = (BuyUp) arguments.getSerializable(BuyUp.class.getSimpleName());
        //работа с данными

        if (buyUp != null) {
            boostUp = buyUp.getBoostUpCl();
            mCount = buyUp.getMCount();
            lvlUpOne = buyUp.getLvlUpOneCl();
            mShowCount.setText(Integer.toString(mCount));
        }
    }

    //количествао очков
    if (mSettings.contains(APP_PREFERENCES_SCORE)) {
        // выводим данные в TextView
        mShowCount.setText(mSettings.getString(APP_PREFERENCES_SCORE, ""));
        //переводит очки
        String valueCount= mShowCount.getText().toString();
        mCount=Integer.parseInt(valueCount);
    }
    //прокачка клика
    if (mSettings.contains(APP_PREFERENCES_PRICESCORE)) {
        String value=  mSettings.getString(APP_PREFERENCES_PRICESCORE, "");
        boostUp=Integer.parseInt(value);
    }
    //прокачка таймера
    if (mSettings.contains(APP_PREFERENCES_UPTIME)) {
       String value= mSettings.getString(APP_PREFERENCES_UPTIME, "");
       lvlUpTime=Integer.parseInt(value);
    }
     //прокачка клика
    if (mSettings.contains(APP_PREFERENCES_UPONE)) {
        String value= mSettings.getString(APP_PREFERENCES_UPONE, "");
        lvlUpOne=Integer.parseInt(value);
    }
    //цена прокачки таймера
    if (mSettings.contains(APP_PREFERENCES_PRICETIME)) {
        String value= mSettings.getString(APP_PREFERENCES_PRICETIME, "");
        boostUpTime=Integer.parseInt(value);
        //priceTime.setText(Integer.toString(boostUpTime));
    }
    //таймер
    if (mSettings.contains(APP_PREFERENCES_BOOL)) {
        bol = mSettings.getBoolean(APP_PREFERENCES_BOOL, false);
        if (bol == true){
            Button b = (Button) findViewById(R.id.btnTimer);
            //старт таймера
            startTime = System.currentTimeMillis();
            timerHandler.postDelayed(timerRunnable, 0);
            b.setVisibility(View.INVISIBLE);
        }
    }

}

    public void AnimImageBtn(View view) {
        ImageButton imageButton = findViewById(R.id.imageButton);
        final Animation animation =  AnimationUtils.loadAnimation(this, R.anim.bounce);
        BounceInterpolator interpolator = new BounceInterpolator(0.2, 20);
        animation.setInterpolator(interpolator);
        imageButton.setAnimation(animation);

        //счетчик
        mCount = mCount + lvlUpOne;
        if (mShowCount != null)
            mShowCount.setText(Integer.toString(mCount));
    }

    // новая игра на кнопку
    public void showToast(View view) {

        Toast toast = Toast.makeText(this, R.string.toast_message, Toast.LENGTH_LONG);
        toast.show();

        Button b = (Button) view;

        boostUp = 10; //цена прокачки тика
        mCount = 0; //общее количесво очков
        lvlUpOne = 1; // уровень прокачки
        boostUpTime = 500;
        lvlUpTime = 1; // уровень прокачки

        b.setVisibility(View.VISIBLE);
        mShowCount.setText(Integer.toString(mCount));
    }


    //работа с окном прокачки
    static final private int CHOOSE_THIEF = 0;// параметр RequestCode
    public void HertBuy(View view) {//кнопка перехода на новую активность
        Intent intent = new Intent(MainActivity.this, MainActivityBuy.class);

        //работа с классом BuyUp создание экземпляра класса
        BuyUp buyUp = new BuyUp(boostUp, mCount, lvlUpOne,boostUpTime,lvlUpTime,bol);
        intent.putExtra(BuyUp.class.getSimpleName(), buyUp);
        //старт окна
        startActivityForResult(intent, CHOOSE_THIEF);

    }


    public void AnimDollar()
    {

        //анимация
        //Button btnBoosterTime = findViewById(R.id.btnBoosterTime);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bouncebtn);
        // amplitude 0.2 and frequency 20
        BounceInterpolator interpolator = new BounceInterpolator(0.2, 20);
        animation.setInterpolator(interpolator);
        //вращение
        ImageButton imageButton = findViewById(R.id.imageButton);
        final Animation animationImage =  AnimationUtils.loadAnimation(this, R.anim.rotate);
        animation.setInterpolator(interpolator);
        imageButton.setAnimation(animationImage);
    }

}