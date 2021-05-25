package com.example.gojawin01;

import java.io.Serializable;

public class BuyUp implements Serializable {

    public int boostUpCl; //цена прокачки тика
    public int mCountCl; //общее количесво очков
    public int clickUp; // коэф прокачки
    public int clickLvl; // уровень прокачки
    public int boostUpTimeCl;

    public int lvlUpTimeCl; // уровень прокачки
    boolean timerBoolCl ;


    //конструктор класса
    public BuyUp(int boostUpCl, int mCountCl, int clickUp, int boostUpTimeCl, int lvlUpTimeCl, boolean timerBoolCl, int clickLvl){
        this.boostUpCl = boostUpCl;
        this.mCountCl = mCountCl;
        this.clickUp = clickUp;
        this.clickLvl = clickLvl;
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

    public int getClickUp() {
        return clickUp;
    }

    public void setClickUp(int clickUp) {
        this.clickUp = clickUp;
    }

    public int getClickLvl() {
        return clickLvl;
    }

    public void setClickLvl(int clickLvl) {
        this.clickLvl = clickLvl;
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
