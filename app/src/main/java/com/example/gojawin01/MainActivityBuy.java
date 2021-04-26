package com.example.gojawin01;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityBuy extends AppCompatActivity {


    // Идентификатор уведомления
    private static final int NOTIFY_ID = 228;

    // Идентификатор канала
    private static String CHANNEL_ID = "Goja channel";

    public int boostUp = 10; //цена прокачки тика
    public int mCount =0; //общее количесво очков
    public int lvlUpOne = 1; // уровень прокачки
    public int boostUpTime =500;//цена прокачки тайма
    public int lvlUpTime = 1; // уровень прокачки
    boolean timerBool ;

    public TextView price; // показ цены клика
    public TextView priceTime; // показ цены тайма
    public TextView countView; // показ очков

    public TextView lvlClick; // показ уровня клика
    public TextView lvlTime; // показ цены тайма
    boolean bmute;

public  void Pered()//передача переменных
{
    Bundle arguments = getIntent().getExtras();
    BuyUp buyUp;
    buyUp = (BuyUp) arguments.getSerializable(BuyUp.class.getSimpleName());
    //передача переменных
    boostUp = buyUp.getBoostUpCl();
    mCount = buyUp.getMCount();
    lvlUpOne = buyUp.getLvlUpOneCl();
    boostUpTime = buyUp.getBoostUpTimeCl();
    lvlUpTime = buyUp.getLvlUpTimeCl();
    timerBool = buyUp.getTimerBoolCl();
    bmute = arguments.getBoolean("mute");
        if(bmute) {
            MediaPlayerMusic.onResume();
        }


}

    @SuppressLint("SetTextI18n")
    public void LoadData()
    {
        countView.setText(Integer.toString(mCount));
        priceTime.setText(Integer.toString(boostUpTime));
        price.setText(Integer.toString(boostUp));
        lvlClick.setText("boost= "+ lvlUpOne);
        lvlTime.setText("lvl= "+ lvlUpTime);
        if (timerBool){
            Button b = (Button) findViewById(R.id.btnTimer);
            b.setVisibility(View.INVISIBLE);
        }
    }


    public void UpdateData()
    {
        //обновление данных в классе
        Bundle arguments = getIntent().getExtras();
        BuyUp buyUp;
        buyUp = (BuyUp) arguments.getSerializable(BuyUp.class.getSimpleName());
        buyUp.setBoostUpCl(boostUp);
        buyUp.setMCount(mCount);
        buyUp.setLvlUpOneCl(lvlUpOne);
        buyUp.setBoostUpTimeCl(boostUpTime);
        buyUp.setLvlUpTimeCl(lvlUpTime);
        buyUp.seTimerBoolCl(timerBool);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_buy);

        price = (TextView) findViewById(R.id.Price);
        priceTime = (TextView) findViewById(R.id.PriceTime);
        countView = (TextView)findViewById(R.id.CountView);

        lvlClick = (TextView) findViewById(R.id.lvlClickText);
        lvlTime = (TextView)findViewById(R.id.lvlTimeText);

        //передача переменных
        Pered();
        LoadData();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //передача переменных
        Pered();
        LoadData();


    }

    static final private int CHOOSE_THIEF = 0;// параметр RequestCode
    public final static String THIEF = "com.example.gojawin01.THIEF";

    public void ReturnMain(View view)
    {
        //создание экземпляра класса
        Intent intent = new Intent(MainActivityBuy.this, MainActivity.class);
        BuyUp buyUp = new BuyUp(boostUp, mCount, lvlUpOne,boostUpTime,lvlUpTime,timerBool);
        intent.putExtra(BuyUp.class.getSimpleName(), buyUp);
        intent.putExtra("mute", bmute);

        UpdateData();

        //старт окна
        startActivity(intent);
    }
    protected void onPause() {
        super.onPause();
        MediaPlayerMusic.onPause();
    }




    public void startTimerBuy(View view){
        Toast toastTimer = Toast.makeText(this, "Нужно накопить 500 очков", Toast.LENGTH_LONG);
        if (mCount>= boostUpTime) {
            mCount =mCount- boostUpTime;
            timerBool = true;
            Button b = (Button) findViewById(R.id.btnTimer);
            b.setVisibility(View.INVISIBLE);
        }
        else
        {
            toastTimer.show();
        }
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
            lvlUpOne = lvlUpOne * 2;
            mCount = mCount - boostUp;
            boostUp = boostUp *4;
            price.setText(Integer.toString(boostUp));
        }
        else
        {
            Toast toast = Toast.makeText(this, R.string.boost_message, Toast.LENGTH_LONG);
            toast.show();
        }
        //обновление данных в классе
        UpdateData();
        LoadData();
    }



    //прокачка таймера
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void BoosterTime (View view){
        //формула прокачки
        if (mCount>= boostUpTime) {
            lvlUpTime++;
            mCount = mCount - boostUpTime;
            boostUpTime = boostUpTime * 6;

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
        UpdateData();
        LoadData();

    }


}