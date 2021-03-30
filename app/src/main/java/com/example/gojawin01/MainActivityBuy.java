package com.example.gojawin01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityBuy extends AppCompatActivity {

    public int boostUp = 10; //цена прокачки тика
    public int mCount =0; //общее количесво очков
    public int lvlUpOne = 1; // уровень прокачки

    public TextView price; // показ цены клика
    public TextView priceTime; // показ цены тайма


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_buy);

        price = (TextView) findViewById(R.id.Price);
        priceTime = (TextView) findViewById(R.id.PriceTime);

        TextView infoTextView = (TextView)findViewById(R.id.textView4);
        Bundle arguments = getIntent().getExtras();
        BuyUp buyUp;
        buyUp = (BuyUp) arguments.getSerializable(BuyUp.class.getSimpleName());
        boostUp = buyUp.getBoostUpCl();
        mCount = buyUp.getMCount();
        lvlUpOne = buyUp.getLvlUpOneCl();

        infoTextView.setText(Integer.toString(mCount));


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        price = (TextView) findViewById(R.id.Price);
        priceTime = (TextView) findViewById(R.id.PriceTime);
        //получение данных из класса
        TextView infoTextView = (TextView)findViewById(R.id.textView4);
        Bundle arguments = getIntent().getExtras();
        BuyUp buyUp;
        buyUp = (BuyUp) arguments.getSerializable(BuyUp.class.getSimpleName());
        boostUp = buyUp.getBoostUpCl();
        mCount = buyUp.getMCount();
        lvlUpOne = buyUp.getLvlUpOneCl();

        infoTextView.setText(Integer.toString(mCount));
    }

    static final private int CHOOSE_THIEF = 0;// параметр RequestCode
    public final static String THIEF = "com.example.gojawin01.THIEF";

    public void HertMain(View view) {//кнопка перехода на новую активность

        Intent intent = new Intent(MainActivityBuy.this, MainActivity.class);

        TextView boostUpText = findViewById(R.id.Price);
        int boostUpCl = Integer.parseInt(boostUpText.getText().toString());
        TextView mCountText = findViewById(R.id.textView4);
        int mCountCl = Integer.parseInt(mCountText.getText().toString());
        int lvlUpOneCl = lvlUpOne;
        //создание экземпляра класса
        BuyUp buyUp = new BuyUp(boostUpCl, mCountCl, lvlUpOneCl);
        intent.putExtra(BuyUp.class.getSimpleName(), buyUp);
        //старт окна
        startActivity(intent);

    }



    //прокачка клика вывод обновленных результатов
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


        TextView infoTextView = (TextView)findViewById(R.id.textView4);
        infoTextView.setText(Integer.toString(mCount));
        //обновление данных в классе
        Bundle arguments = getIntent().getExtras();
        BuyUp buyUp;
        buyUp = (BuyUp) arguments.getSerializable(BuyUp.class.getSimpleName());
        buyUp.setBoostUpCl(boostUp);
        buyUp.setMCount(mCount);
        buyUp.setLvlUpOneCl(lvlUpOne);
    }



}