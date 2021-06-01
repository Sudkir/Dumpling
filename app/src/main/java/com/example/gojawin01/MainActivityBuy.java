package com.example.gojawin01;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.Objects;

public class MainActivityBuy extends AppCompatActivity {

//assert add
    // Идентификатор уведомления
    private static final int NOTIFY_ID = 228;

    // Идентификатор канала
    private static final String CHANNEL_ID = "Goja channel";

    public int boostUp = 10; //цена прокачки тика
    public int mCount =0; //общее количесво очков
    public int ClickUp = 1; // уровень прокачки
    public int boostUpTime =500;//цена прокачки тайма
    public int lvlUpTime = 1; // уровень прокачки
    public int ClickLvl = 1; // уровень прокачки
    boolean timerBool ;
    public Button btnbooster;
    public Button btnboosterTime;
    public Button btnTimer;
    public Button exit;


    public TextView price; // показ цены клика
    public TextView priceTime; // показ цены тайма
    public TextView countView; // показ очков

    public TextView lvlClick; // показ уровня клика
    public TextView lvlTime; // показ цены тайма
    boolean BoolMute;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //спрятать заголовок
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main_buy);
        btnbooster =findViewById(R.id.btnBooster);
        exit =findViewById(R.id.exitBtn);
        btnboosterTime =findViewById(R.id.btnBoosterTime);
        btnTimer =findViewById(R.id.btnTimer);
        price = findViewById(R.id.Price);
        priceTime = findViewById(R.id.PriceTime);
        countView = findViewById(R.id.CountView);

        //передача переменных
        Send();
        LoadData();
        UpLanguage();


        //обраобтка свайпов на фоне
        final Context context = this;
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setOnTouchListener(new OnSwipeTouchListener(context) {
            public void onSwipeTop() {

            }
            @SuppressLint("ClickableViewAccessibility")
            public void onSwipeRight() {
                Return();
            }

            public void onSwipeLeft() {



            }
            public void onSwipeBottom() {

            }

        });

    }


    public  void Send()//передача переменных
{
    Bundle arguments = getIntent().getExtras();
    BuyUp buyUp;
    assert arguments != null;
    buyUp = (BuyUp) arguments.getSerializable(BuyUp.class.getSimpleName());
    //передача переменных
    assert buyUp != null;
    boostUp = buyUp.getBoostUpCl();
    mCount = buyUp.getMCount();
    ClickUp = buyUp.getClickUp();
    boostUpTime = buyUp.getBoostUpTimeCl();
    lvlUpTime = buyUp.getLvlUpTimeCl();
    timerBool = buyUp.getTimerBoolCl();
    ClickLvl = buyUp.getClickLvl();
    //включение плеера
    BoolMute = arguments.getBoolean("mute");
        if(BoolMute) {
            MediaPlayerMusic.onResume();
        }


}


    @SuppressLint("SetTextI18n")
    public void LoadData()
    {
        countView.setText(Integer.toString(mCount));
        priceTime.setText(Integer.toString(boostUpTime));
        price.setText(Integer.toString(boostUp));

        Button b;
        if (timerBool) b = findViewById(R.id.btnTimer);
        else b = findViewById(R.id.btnBoosterTime);
        b.setClickable(false);
        b.setBackgroundResource(R.drawable.button_unclick);
    }


    public void UpdateData()
    {
        //обновление данных в классе
        Bundle arguments = getIntent().getExtras();
        BuyUp buyUp;
        assert arguments != null;
        buyUp = (BuyUp) arguments.getSerializable(BuyUp.class.getSimpleName());
        assert buyUp != null;
        buyUp.setBoostUpCl(boostUp);
        buyUp.setMCount(mCount);
        buyUp.setClickUp(ClickUp);
        buyUp.setBoostUpTimeCl(boostUpTime);
        buyUp.setLvlUpTimeCl(lvlUpTime);
        buyUp.seTimerBoolCl(timerBool);
        buyUp.setClickLvl(ClickLvl);
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //передача переменных
        Send();
        LoadData();


    }

    //static final private int CHOOSE_THIEF = 0;// параметр RequestCode
    //public final static String THIEF = "com.example.gojawin01.THIEF";

    public void  Return(){
        //создание экземпляра класса
        Intent intent = new Intent(MainActivityBuy.this, MainActivity.class);
        BuyUp buyUp = new BuyUp(boostUp, mCount, ClickUp,boostUpTime,lvlUpTime,timerBool,ClickLvl);
        //заполнение данными
        intent.putExtra(BuyUp.class.getSimpleName(), buyUp);
        intent.putExtra("mute", BoolMute);
        UpdateData();
        //старт окна
        startActivity(intent);
    }

    public void ReturnMain(View view)
    {
        Return();
    }
    protected void onPause() {
        super.onPause();
        MediaPlayerMusic.onPause();
    }

    @SuppressLint("SetTextI18n")
    public void UpLanguage() {
        btnbooster.setText(getResources().getString(R.string.btn_upClick)+" "+ Integer.toString(ClickLvl));
        exit.setText(getResources().getString(R.string.btn_back));
        btnboosterTime.setText(getResources().getString(R.string.btn_upAutoClick)+" "+ Integer.toString(lvlUpTime+1));
        btnTimer.setText(R.string.btn_buyAutoClick);


        TextView textView;
        textView = findViewById(R.id.textViewBuyFor1);
        textView.setText(R.string.btn_buyFor);

        textView = findViewById(R.id.textViewBuyFor2);
        textView.setText(R.string.btn_buyFor);

        textView = findViewById(R.id.textViewBuyFor3);
        textView.setText(R.string.btn_buyFor);

    }




    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startTimerBuy(View view){
        Toast toastTimer = Toast.makeText(this, "Нужно накопить 500 очков", Toast.LENGTH_LONG);
        if (mCount>= boostUpTime) {
            mCount =mCount- boostUpTime;
            timerBool = true;
            //настройка кнопок
            Button b = findViewById(R.id.btnTimer);
            b.setClickable(false);
            b.setBackgroundResource(R.drawable.button_unclick);
            Button s = findViewById(R.id.btnBoosterTime);
            s.setClickable(true);
            s.setBackgroundResource(R.drawable.button_states);
            //вывод уведомления
            showNotification("Достижение","Куплен пассивный доход\uD83D\uDC7D");
        }
        else
        {
            toastTimer.show();
        }
        @SuppressLint("CutPasteId") Button booster = findViewById(R.id.btnTimer);
        AnimUp(booster);
        UpdateData();
        LoadData();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public <string> void showNotification(string strContentTitle,string strContentText){



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

    //прокачка клика вывод обновленных результатов
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Booster (View view){

        if (mCount>= boostUp) {
            if (ClickLvl<10)
            {
                ClickUp = ClickUp+2;
                Toast toast = Toast.makeText(this, "Клик увеличен на 2", Toast.LENGTH_LONG);
                toast.show();
            }
            if (ClickLvl<20 &&ClickLvl>=10)
            {
                ClickUp = ClickUp+5;
                Toast toast = Toast.makeText(this, "Клик увеличен на 5", Toast.LENGTH_LONG);
            }
            if (ClickLvl>=20)
            {
                Toast toast = Toast.makeText(this, "Клик увеличен на 10", Toast.LENGTH_LONG);
                ClickUp = ClickUp+10;
            }

            ClickLvl++;
            mCount = mCount - boostUp;
            boostUp = boostUp *2;
            //50 × 1.07² = 54,24 2=lvl 50=start price
            price.setText(Integer.toString(boostUp));
            btnbooster.setText(getResources().getString(R.string.btn_upClick)+" "+ Integer.toString(ClickLvl));

            //получение достижений
            if (ClickLvl ==10) {
                showNotification("Достижение получено!", "Куплено х10 улучшений клика");
            }
            if (ClickLvl ==100) {
                showNotification("Достижение получено!","Куплено х100 улучшений клика");
            }

        }
        else
        {
            Toast toast = Toast.makeText(this, R.string.boost_message, Toast.LENGTH_LONG);
            toast.show();
        }
        Button booster = findViewById(R.id.btnBooster);
        AnimUp(booster);
        //обновление данных в классе
        UpdateData();
        LoadData();
    }

    public void AnimUp(Button b)
    {
        final Animation animation =  AnimationUtils.loadAnimation(this, R.anim.bounce);
        BounceInterpolator interpolator = new BounceInterpolator(0.2, 20);
        animation.setInterpolator(interpolator);
        b.setAnimation(animation);
    }




    //прокачка таймера
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void BoosterTime (View view){
        //формула прокачки
        if (mCount>= boostUpTime) {
            lvlUpTime++;
            btnboosterTime.setText(getResources().getString(R.string.btn_upAutoClick)+" "+ Integer.toString(lvlUpTime+1));
            mCount = mCount - boostUpTime;
            boostUpTime = boostUpTime * 6;

            //получение достижений
            if (lvlUpTime==10) {
                showNotification("Достижение получено!", "Куплено 10 улучшений клика");
            }
            if (lvlUpTime==20) {
                showNotification("Достижение получено!","Куплено 20 улучшений клика");
            }

        }
        else
        {
            Toast toast = Toast.makeText(this, R.string.boost_message, Toast.LENGTH_LONG);
            toast.show();
        }
        //обновление данных в классе
        Button boosterTime = findViewById(R.id.btnBoosterTime);
        AnimUp(boosterTime);
        UpdateData();
        LoadData();

    }


}