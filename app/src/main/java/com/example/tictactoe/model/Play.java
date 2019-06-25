package com.example.tictactoe.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.tictactoe.BR;

import java.util.List;

public class Play extends BaseObservable {
    protected String key;
    protected User player1;
    protected User player2;
    protected int color1;
    protected int color2;
    protected List<Integer> listBox;
    protected int turn;
    protected int playAgain;
    protected String player1Message;
    protected String player2Message;

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

    @Bindable
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Bindable
    public List<Integer> getListBox() {
        return listBox;
    }

    public Play setListBox(List<Integer> listBox) {
        this.listBox = listBox;
        notifyPropertyChanged(BR.listBox);
        return this;
    }

    @Bindable
    public int getTurn() {
        return turn;
    }

    public Play setTurn(int turn) {
        this.turn = turn;
        notifyPropertyChanged(BR.turn);
        return this;
    }

    @Bindable
    public int getPlayAgain() {
        return playAgain;
    }

    public Play setPlayAgain(int playAgain) {
        this.playAgain = playAgain;
        notifyPropertyChanged(BR.playAgain);
        return this;
    }

    @Bindable
    public String getPlayer1Message() {
        return player1Message;
    }

    public Play setPlayer1Message(String player1Message) {
        this.player1Message = player1Message;
        notifyPropertyChanged(BR.player1Message);
        return this;
    }

    @Bindable
    public String getPlayer2Message() {
        return player2Message;
    }

    public Play setPlayer2Message(String player2Message) {
        this.player2Message = player2Message;
        notifyPropertyChanged(BR.player2Message);
        return this;
    }
}
