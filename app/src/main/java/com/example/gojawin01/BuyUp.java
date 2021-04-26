package com.example.gojawin01;
import android.widget.TextView;

import java.io.Serializable;

public class BuyUp implements Serializable {

    public int boostUpCl; //цена прокачки тика
    public int mCountCl; //общее количесво очков
    public int lvlUpOneCl; // уровень прокачки
    public int boostUpTimeCl;

    public int lvlUpTimeCl; // уровень прокачки
    boolean timerBoolCl ;


    //конструктор класса
    public BuyUp(int boostUpCl, int mCountCl, int lvlUpOneCl, int boostUpTimeCl,int lvlUpTimeCl,boolean timerBoolCl){
        this.boostUpCl = boostUpCl;
        this.mCountCl = mCountCl;
        this.lvlUpOneCl = lvlUpOneCl;
        this.boostUpTimeCl = boostUpTimeCl;
        this.lvlUpTimeCl = lvlUpTimeCl;
        this.timerBoolCl = timerBoolCl;
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

    public void seTimerBoolCl(boolean timerBoolCl) {
        this.timerBoolCl = timerBoolCl;
    }


    public boolean getTimerBoolCl() {
        return timerBoolCl;
    }

}
