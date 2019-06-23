package com.example.tictactoe.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.tictactoe.BR;

public class Play extends BaseObservable {
    protected User player1;
    protected User player2;
    protected int color1;
    protected int color2;

    @Bindable
    public User getPlayer1() {
        return player1;
    }

    public void setPlayer1(User player1) {
        this.player1 = player1;
        notifyPropertyChanged(BR.player1);
    }

    @Bindable
    public User getPlayer2() {
        return player2;
    }

    public void setPlayer2(User player2) {
        this.player2 = player2;
        notifyPropertyChanged(BR.player2);
    }

    @Bindable
    public int getColor1() {
        return color1;
    }

    public void setColor1(int color1) {
        this.color1 = color1;
        notifyPropertyChanged(BR.color1);
    }

    @Bindable
    public int getColor2() {
        return color2;
    }

    public void setColor2(int color2) {
        this.color2 = color2;
        notifyPropertyChanged(BR.color2);
    }
}
