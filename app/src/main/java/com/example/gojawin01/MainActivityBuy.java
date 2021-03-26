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

    String user = "0";
    public final static String THIEF = "com.example.gojawin01.THIEF";

    public void HertMain(View view) {//кнопка перехода на новую активность



        Intent intent = new Intent(MainActivityBuy.this, MainActivity.class);
        Intent answerIntent = new Intent();
        answerIntent.putExtra(THIEF, user);
        setResult(RESULT_OK, answerIntent);
        finish();

        //startActivity(intent);//старт окна

    }

    //прокачка клика вывод обновленных результатов
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

            TextView infoTextView = (TextView)findViewById(R.id.textView4);
            infoTextView.setText(Integer.toString(mCount));



        }
        else
        {
            Toast toast = Toast.makeText(this, R.string.boost_message, Toast.LENGTH_LONG);
            toast.show();
        }
    }


    public void Hertgg(View view) {//кнопка перехода на новую активность

    }




}