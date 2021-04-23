package com.example.gojawin01;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivitySettings extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


    }







    public void ReturnMain(View view)
    {
        //создание экземпляра класса
        Intent intent = new Intent(MainActivitySettings.this, MainActivity.class);
        startActivity(intent);
    }

    protected void onResume() {
        super.onResume();

    }

}

