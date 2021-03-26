package com.example.gojawin01;
import android.widget.TextView;

import java.io.Serializable;

public class BuyUp implements Serializable {

    public int boostUpCl = 10; //цена прокачки тика
    public int mCountCl =0; //общее количесво очков
    public int lvlUpOneCl = 1; // уровень прокачки
    //public int boostUpTime = 500;
    //public int lvlUpTime = 1; // уровень прокачки
    //public TextView mShowCount; // показ очков
    //public TextView price; // показ цены клика
    //public TextView priceTime; // показ цены тайма
    //boolean bol = false;

    public BuyUp(int boostUpCl, int mCountCl, int lvlUpOneCl){
        this.boostUpCl = boostUpCl;
        this.mCountCl = mCountCl;
        this.lvlUpOneCl = lvlUpOneCl;
    }

    public int getBoostUpCl() {
        return boostUpCl;
    }

    public void setBoostUpCl(int boostUpCl) {
        this.boostUpCl = boostUpCl;
    }

    public int getMCount() {
        return mCountCl;
    }

    public void setMCount(int mCount) {
        this.mCountCl = mCount;
    }

    public int getLvlUpOneCl() {
        return lvlUpOneCl;
    }

    public void setLvlUpOneCl(int lvlUpOneCl) {
        this.lvlUpOneCl = lvlUpOneCl;
    }



}
