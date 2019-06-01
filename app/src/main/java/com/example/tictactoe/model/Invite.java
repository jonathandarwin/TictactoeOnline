package com.example.tictactoe.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.tictactoe.BR;

public class Invite extends BaseObservable {
    public String key;
    public String sender;
    public String receiver;
    public String senderName;
    public String receiverName;
    public int accepted;
    public int responded;

    @Bindable
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
        notifyPropertyChanged(BR.key);
    }

    @Bindable
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
        notifyPropertyChanged(BR.sender);
    }

    @Bindable
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
        notifyPropertyChanged(BR.receiver);
    }

    @Bindable
    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
        notifyPropertyChanged(BR.senderName);
    }

    @Bindable
    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
        notifyPropertyChanged(BR.receiverName);
    }

    @Bindable
    public int getAccepted() {
        return accepted;
    }

    public void setAccepted(int accepted) {
        this.accepted = accepted;
        notifyPropertyChanged(BR.accepted);
    }

    @Bindable
    public int getResponded() {
        return responded;
    }

    public void setResponded(int responded) {
        this.responded = responded;
        notifyPropertyChanged(BR.responded);
    }
}
