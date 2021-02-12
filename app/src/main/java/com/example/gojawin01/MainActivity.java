package com.example.gojawin01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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
    public int boostUpTime = 50;
    public int lvlUpTime = 1; // уровень прокачки
    public TextView mShowCount; // показ очков
    private TextView price; // показ цены клика
    private TextView priceTime; // показ цены тайма
    private  TextView TimerTxt;
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
        TimerTxt = (TextView) findViewById(R.id.timerTxt);
        price = (TextView) findViewById(R.id.Price);
        priceTime = (TextView) findViewById(R.id.PriceTime);


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
                    boostUpTime = 1000;
                    priceTime.setText(Integer.toString(boostUpTime));
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

    protected void onStop(){
        super.onStop();
        //сохранение очков в память при закрытии приложения
        String strCount = mShowCount.getText().toString();
        String strLvlUpTime = Integer.toString(lvlUpTime);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_SCORE, strCount);
        editor.putString(APP_PREFERENCES_UPTIME, strLvlUpTime);
        editor.putBoolean(APP_PREFERENCES_BOOL, bol);
        editor.apply();

    }



    protected void onResume()
{
    super.onResume();
    if (mSettings.contains(APP_PREFERENCES_SCORE)) {
        // выводим данные в TextView
        mShowCount.setText(mSettings.getString(APP_PREFERENCES_SCORE, ""));
        //переводит очки
        String valueCount= mShowCount.getText().toString();
        mCount=Integer.parseInt(valueCount);
    }

    if (mSettings.contains(APP_PREFERENCES_UPTIME)) {
       String value= mSettings.getString(APP_PREFERENCES_UPTIME, "");
       lvlUpTime=Integer.parseInt(value);
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

    //Клик (чуть не забыли)
    public void countUp(View view) {


        //анимация
        //Button btnDrain = findViewById(R.id.btnDrain);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        BounceInterpolator interpolator = new BounceInterpolator(0.2, 20);
        animation.setInterpolator(interpolator);
        //btnDrain.startAnimation(animation);
        //счетчик
        mCount = mCount + lvlUpOne;
        if (mShowCount != null)
            mShowCount.setText(Integer.toString(mCount));
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
        boostUpTime = 50;
        lvlUpTime = 1; // уровень прокачки
        price.setText(Integer.toString(boostUp));
        priceTime.setText(Integer.toString(boostUpTime));
        b.setVisibility(View.VISIBLE);
        mShowCount.setText(Integer.toString(mCount));
    }

    //прокачка клика
    public void Booster (View view){

        //анимация
        Button Booster = findViewById(R.id.Booster);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bouncebtn);

        // amplitude 0.2 and frequency 20
        BounceInterpolator interpolator = new BounceInterpolator(0.2, 20);
        animation.setInterpolator(interpolator);
        Booster.startAnimation(animation);


        ImageButton imageButton = findViewById(R.id.imageButton);
        final Animation animationImage =  AnimationUtils.loadAnimation(this, R.anim.rotate);
        animation.setInterpolator(interpolator);
        imageButton.setAnimation(animationImage);



        if (mCount>= boostUp) {
            lvlUpOne = lvlUpOne * 2;
            mCount = mCount - boostUp;
            boostUp = boostUp *4;
            price.setText(Integer.toString(boostUp));
            mShowCount.setText(Integer.toString(mCount));


        }
        else
        {
            Toast toast = Toast.makeText(this, R.string.boost_message, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    //прокачка таймера
    public void BoosterTime (View view){
         //анимация
        Button btnBoosterTime = findViewById(R.id.btnBoosterTime);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bouncebtn);

        // amplitude 0.2 and frequency 20
        BounceInterpolator interpolator = new BounceInterpolator(0.2, 20);
        animation.setInterpolator(interpolator);

        btnBoosterTime.startAnimation(animation);


        if (mCount>= boostUpTime) {
            lvlUpTime++;
            mCount = mCount - boostUpTime;
            boostUpTime = boostUpTime * 6;
            priceTime.setText(Integer.toString(boostUpTime));
            mShowCount.setText(Integer.toString(mCount));


        }
        else
        {
            Toast toast = Toast.makeText(this, R.string.boost_message, Toast.LENGTH_LONG);
            toast.show();
        }
    }


}