package com.example.gojawin01;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.security.cert.CertPathBuilder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import io.reactivex.schedulers.Schedulers;


//класс для анимации
class BounceInterpolator implements android.view.animation.Interpolator {
    //add final
    private final double mAmplitude;
    private final double mFrequency;

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
    public int lvlUpOne = 1; // клэф прокачки
    public int lvlClick = 1; // уровень прокачки
    public String dateLast = "";
    public int boostUpTime = 500;
    public int lvlUpTime = 1; // уровень прокачки
    public TextView mShowCount; // показ очков
    public TextView price; // показ цены клика
    boolean bol = false;//таймер
    boolean muteBool = true;//звук

    Button buttonUp;//прокачки
    Button buttonSet;//настройки


    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @SuppressLint({"SetTextI18n", "DefaultLocale"})
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
            //mShowCount.setText(Integer.toString(fmCount));
            //double fmCount = mCount;
            //mShowCount.setText(String.format("%f.2",fmCount));
            //mShowCount.setText(Float.toString(fmCount));
        }
    };


    //сохранение данных
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_SCORE= "Score"; // очки
    public static final String APP_PREFERENCES_UPTIME= "UpTime"; // очки
    public static final String APP_PREFERENCES_BOOL = "Bool";
    public static final String APP_PREFERENCES_PRICETIME = "Time"; // прокачка таймера
    public static final String APP_PREFERENCES_PRICESCORE = "PriceScore"; //
    public static final String APP_PREFERENCES_UPONE = "lvlUp"; //коэф прокачки
    public static final String APP_PREFERENCES_CLICKLVL = "ClickLvL"; //уровень прокачки
    public static final String APP_PREFERENCES_MUTE = "Mute"; //мут музыки
    public static final String APP_PREFERENCES_DATE = "Date"; //last visit date


    SharedPreferences mSettings;

    //создание менеджера уведомлений
    private NotificationManager notificationManager;
    // Идентификатор уведомления
    private static final int NOTIFY_ID = 1;
    // Идентификатор канала
    private static String CHANNEL_ID = "CHANNEL_ID";


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //уведомление менеджер
        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        setContentView(R.layout.activity_main);
        //спрятать заголовок
        Objects.requireNonNull(getSupportActionBar()).hide();



        //установка языка по умолчанию
        buttonSet = findViewById(R.id.btnSettings);
        buttonSet.setText(R.string.btn_settings);

        buttonUp = findViewById(R.id.btnUpgrade);
        buttonUp.setText(R.string.btn_upgrade);

        timerTextView = findViewById(R.id.timerTxt);
        mShowCount = findViewById(R.id.textView);
        mCount =0;
        mShowCount.setText(Integer.toString(mCount));
        price = findViewById(R.id.Price);
        //сейв переменных
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        //обраобтка свайпов на фоне
        final Context context = this;
        ImageView imageView = findViewById(R.id.imageViewBack);
        imageView.setOnTouchListener(new OnSwipeTouchListener(context) {
            public void onSwipeTop() {
            }
            @SuppressLint("ClickableViewAccessibility")
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onSwipeRight() {
                ActivitySettingsShow();
            }
            public void onSwipeLeft() {
                ActivityBuyShow();
            }
            public void onSwipeBottom() {
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onPause() {
        super.onPause();
        MediaPlayerMusic.onPause();
        DInt();
        //пауза приложения

    }

    @Override
    public void onStart() {
        super.onStart();
        //onCreate->onStart->onResume




        //количествао очков
        if (mSettings.contains(APP_PREFERENCES_SCORE)) {
            // выводим данные в TextView
            mShowCount.setText(mSettings.getString(APP_PREFERENCES_SCORE, ""));
            //переводит очки
            String valueCount= mShowCount.getText().toString();
            if (valueCount != null) {
                mCount=Integer.parseInt(valueCount);
            }

        }
        //date
        if (mSettings.contains(APP_PREFERENCES_DATE)) {
            String value=mSettings.getString(APP_PREFERENCES_DATE, "");
            if (value != null) {
                dateLast=value;
            }
        }
        //прокачка клика
        if (mSettings.contains(APP_PREFERENCES_PRICESCORE)) {
            String value=  mSettings.getString(APP_PREFERENCES_PRICESCORE, "");
            if (value != null) {
                boostUp=Integer.parseInt(value);
            }
        }
        //прокачка таймера
        if (mSettings.contains(APP_PREFERENCES_UPTIME)) {
            String value= mSettings.getString(APP_PREFERENCES_UPTIME, "");
            if (value != null) {
                lvlUpTime=Integer.parseInt(value);
            }
        }
        //прокачка клика
        if (mSettings.contains(APP_PREFERENCES_UPONE)) {
            String value= mSettings.getString(APP_PREFERENCES_UPONE, "");
            if (value != null) {
                lvlUpOne=Integer.parseInt(value);
            }
        }
        //уровень прокаяки
        if (mSettings.contains(APP_PREFERENCES_CLICKLVL)) {
            String value= mSettings.getString(APP_PREFERENCES_CLICKLVL, "");
            if (value != null) {
                lvlClick=Integer.parseInt(value);
            }
        }
        //цена прокачки таймера
        if (mSettings.contains(APP_PREFERENCES_PRICETIME)) {
            String value= mSettings.getString(APP_PREFERENCES_PRICETIME, "");
            if (value != null) {
                boostUpTime=Integer.parseInt(value);
            }
            //priceTime.setText(Integer.toString(boostUpTime));
        }
        //таймер
        if (mSettings.contains(APP_PREFERENCES_BOOL)) {
            bol = mSettings.getBoolean(APP_PREFERENCES_BOOL, false);

        }

        //mute
        if (mSettings.contains(APP_PREFERENCES_MUTE)) {
            muteBool = mSettings.getBoolean(APP_PREFERENCES_MUTE, true);
            if (muteBool){
                MediaPlayerMusic.onResume();
            }

        }



        //музыка
        MediaPlayerMusic.Init(this);
        //сколько не было
        DShow();

    }

    public void EditSave() {
        //сохранение очков в память при закрытии приложения
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_SCORE, Integer.toString(mCount));
        editor.putString(APP_PREFERENCES_UPTIME, Integer.toString(lvlUpTime));
        editor.putString(APP_PREFERENCES_UPONE, Integer.toString(lvlUpOne));
        editor.putString(APP_PREFERENCES_CLICKLVL, Integer.toString(lvlClick));
        editor.putString(APP_PREFERENCES_PRICETIME, Integer.toString(boostUpTime));
        editor.putString(APP_PREFERENCES_PRICESCORE, Integer.toString(boostUp));
        editor.putString(APP_PREFERENCES_DATE, dateLast);
        editor.putBoolean(APP_PREFERENCES_BOOL, bol);
        editor.putBoolean(APP_PREFERENCES_MUTE, muteBool);
        editor.apply();
    }

    protected void onStop(){
        super.onStop();
        EditSave();
    }


    @SuppressLint("SetTextI18n")
    public void ResemeAll()
    {
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
                lvlUpOne = buyUp.getClickUp();
                lvlClick = buyUp.getClickLvl();
                mShowCount.setText(Integer.toString(mCount));
                lvlUpTime = buyUp.getLvlUpTimeCl();
                boostUpTime = buyUp.getBoostUpTimeCl();
                bol = buyUp.getTimerBoolCl();
            }

        }

        if (bol)
        {
            //зануление счётчика
            timerHandler.removeCallbacks(timerRunnable);
            //старт таймера
            startTime = System.currentTimeMillis();
            timerHandler.postDelayed(timerRunnable, 0);
        }




        Bundle arguments1 = getIntent().getExtras();

        if(arguments1!=null) {
            muteBool = arguments1.getBoolean("mute");
            if(muteBool) {
                MediaPlayerMusic.onResume();
            }
        }

    }


    protected void onResume() {
    super.onResume();

    AnimDollar();
    ResemeAll();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public <string> void showNotification(String strContentTitle, Object strContentText){

        //создание канала
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        //свойства уведомления
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setAutoCancel(true)
                        .setWhen(System.currentTimeMillis())//время
                        .setSmallIcon(R.drawable.ic_baseline_monetization_on_24)//пикча
                        .setContentTitle((CharSequence) strContentTitle)
                        .setContentText((CharSequence) strContentText)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setLights(Color.WHITE, 3000, 3000)
                        .setDefaults(NotificationCompat.DEFAULT_SOUND)
                        .setContentIntent(pendingIntent);
        notificationBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });

        //запуск
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(NOTIFY_ID, notificationBuilder.build());
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    public void AnimImageBtn(View view) {
        MediaPlayerMusic.onResumeFood();
        ImageButton imageButton = findViewById(R.id.imageButton);
        final Animation animation =  AnimationUtils.loadAnimation(this, R.anim.bounce);
        BounceInterpolator interpolator = new BounceInterpolator(0.2, 20);
        animation.setInterpolator(interpolator);
        imageButton.setAnimation(animation);
        //счетчик
        mCount = mCount + lvlUpOne;
        if (mShowCount != null)
            mShowCount.setText(Integer.toString(mCount));
        //MediaPlayerMusic.onPauseFood();
    }

    static final private int CHOOSE_THIEF = 0;// параметр RequestCode


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void ActivitySettingsShowV(View v){
        ActivitySettingsShow();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void ActivitySettingsShow()
    {
        //showNotification("228","\uD83D\uDC7D");
        Intent intent = new Intent(MainActivity.this, MainActivitySettings.class);
        intent.putExtra("mute", muteBool);
        startActivity(intent);
    }





    //работа с окном прокачки
    public void ActivityBuyShowV(View view) {
        ActivityBuyShow();
    }
    private void ActivityBuyShow()
    {
        Intent intent = new Intent(MainActivity.this, MainActivityBuy.class);
        //работа с классом BuyUp создание экземпляра класса
        BuyUp buyUp = new BuyUp(boostUp, mCount, lvlUpOne,boostUpTime,lvlUpTime,bol,lvlClick);
        intent.putExtra(BuyUp.class.getSimpleName(), buyUp);
        intent.putExtra("mute", muteBool);
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
    public void Share(View v)
    {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/sudkir")));

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void DInt()
    {
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("MMddHHmmss");
        dateLast = df.format(Calendar.getInstance().getTime());
        //int i = Integer.parseInt(sDate.replaceAll("[\\D]", ""));
        //
        //        //return Integer.parseInt (sDate);
    }

    public  void  DShow() {
        Toast toastTimer = Toast.makeText(this, dateLast, Toast.LENGTH_SHORT);
        toastTimer.show();
    }


}