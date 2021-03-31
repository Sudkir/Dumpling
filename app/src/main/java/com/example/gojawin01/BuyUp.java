package com.example.gojawin01;
import android.widget.TextView;

import java.io.Serializable;

public class BuyUp implements Serializable {

    public int boostUpCl = 10; //цена прокачки тика
    public int mCountCl =0; //общее количесво очков
    public int lvlUpOneCl = 1; // уровень прокачки
    public int boostUpTimeCl = 500;

    public int lvlUpTimeCl = 1; // уровень прокачки
    boolean timerBoolCl ;


    //конструктор класса
    public BuyUp(int boostUpCl, int mCountCl, int lvlUpOneCl, int boostUpTimeCl,int lvlUpTimeCl,boolean timerBoolCl){
        this.boostUpCl = boostUpCl;
        this.mCountCl = mCountCl;
        this.lvlUpOneCl = lvlUpOneCl;
        this.boostUpTimeCl = boostUpTimeCl;
    }



    //гетеры и сетеры
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

    public int getBoostUpTimeCl() {
        return boostUpTimeCl;
    }

    public void setBoostUpTimeCl(int boostUpTimeCl) {
        this.boostUpTimeCl = boostUpTimeCl;
    }


    public int getLvlUpTimeCl() {
        return lvlUpTimeCl;
    }

    public void setLvlUpTimeCl(int lvlUpTimeCl) {
        this.lvlUpTimeCl = lvlUpTimeCl;
    }

}
