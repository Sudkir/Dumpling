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
                    boostUpTime = 500;
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
        price.setText(Integer.toString(boostUp));

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
        priceTime.setText(Integer.toString(boostUpTime));
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
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        BounceInterpolator interpolator = new BounceInterpolator(0.2, 20);
        animation.setInterpolator(interpolator);
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
        boostUpTime = 500;
        lvlUpTime = 1; // уровень прокачки
        price.setText(Integer.toString(boostUp));
        priceTime.setText(Integer.toString(boostUpTime));
        b.setVisibility(View.VISIBLE);
        mShowCount.setText(Integer.toString(mCount));
    }


    //работа с окном прокачки
    static final private int CHOOSE_THIEF = 0;// параметр RequestCode
    public void HertBuy(View view) {//кнопка перехода на новую активность

        Intent intent = new Intent(MainActivity.this, MainActivityBuy.class);

        //работа с классом BuyUp
        TextView boostUpText = findViewById(R.id.Price);
        int boostUpCl = Integer.parseInt(boostUpText.getText().toString());
        TextView mCountText = findViewById(R.id.textView);
        int mCountCl = Integer.parseInt(boostUpText.getText().toString());
        int lvlUpOneCl = 1;

        BuyUp buyUp = new BuyUp(boostUpCl, mCountCl, lvlUpOneCl);//создание экземпляра класса

        intent.putExtra(BuyUp.class.getSimpleName(), buyUp);

        startActivityForResult(intent, CHOOSE_THIEF);//старт окна

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TextView infoTextView = (TextView) findViewById(R.id.textViewTest);

        if (requestCode == CHOOSE_THIEF) {

            if (resultCode == RESULT_OK) //если результат возвращен через кнопку
            {
                String thiefname = data.getStringExtra(MainActivityBuy.THIEF);
                infoTextView.setText(thiefname);
            }else {
                infoTextView.setText(""); // стираем текст
            }
        }
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

        //вращение
        ImageButton imageButton = findViewById(R.id.imageButton);
        final Animation animationImage =  AnimationUtils.loadAnimation(this, R.anim.rotate);
        animation.setInterpolator(interpolator);
        imageButton.setAnimation(animationImage);


        //формула прокачки
        if (mCount>= boostUpTime) {
            lvlUpTime++;
            mCount = mCount - boostUpTime;
            boostUpTime = boostUpTime * 6;
            //возврат значенией
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